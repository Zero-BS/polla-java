package org.zerobs.polla.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.zerobs.polla.entities.db.Entity;
import org.zerobs.polla.entities.db.GSI;
import org.zerobs.polla.entities.db.SortKeyCondition;
import org.zerobs.polla.exception.CustomException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.core.GenericTypeResolver.resolveTypeArgument;

@Repository
@Slf4j
public class DefaultEntityRepository<T extends Entity> implements EntityRepository<T> {
    private static final String COLON = ":";
    private static final String EQUAL_TO = " = ";
    private static final String AND = " and ";
    @SuppressWarnings("unchecked")
    private final Class<T> genericType = (Class<T>) resolveTypeArgument(getClass(), DefaultEntityRepository.class);

    @Autowired
    private DynamoDBMapper mapper;

    @Override
    public void save(T object) {
        mapper.save(object);
    }

    @Override
    public void batchSave(Iterable<T> objects) throws CustomException {
        List<DynamoDBMapper.FailedBatch> failedBatches = mapper.batchSave(objects);
        if (!failedBatches.isEmpty()) {
            //rollback
            try {
                batchDelete(objects);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            var e = failedBatches.get(0).getException();
            throw new CustomException(e.getMessage(), e);
        }
    }

    @Override
    public T getByPk(String pk) {
        return mapper.load(genericType, pk);
    }

    @Override
    public void delete(T object) {
        mapper.delete(object);
    }

    @Override
    public List<DynamoDBMapper.FailedBatch> batchDelete(Iterable<T> objects) {
        return mapper.batchDelete(objects);
    }

    protected T getByIndex(GSI gsi, String pkValue) {
        return getByIndex(gsi, pkValue, null, null);
    }

    protected T getByIndex(GSI gsi, String pkValue, String skValue) {
        return getByIndex(gsi, pkValue, skValue, SortKeyCondition.EQUAL_TO);
    }

    protected T getByIndex(GSI gsi, String pkValue, String skValue, SortKeyCondition sortKeyCondition) {
        return getByIndex(gsi, pkValue, skValue, sortKeyCondition, 1).stream().findFirst().orElse(null);
    }

    protected List<T> getByIndex(GSI gsi, String pkValue, String skValue,
                                 SortKeyCondition sortKeyCondition, int limit) {
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(COLON + gsi.getPkAttributeName(), new AttributeValue().withS(pkValue));

        StringBuilder keyConditionExpression = new StringBuilder()
                .append(gsi.getPkAttributeName()).append(EQUAL_TO).append(COLON).append(gsi.getPkAttributeName());

        if (gsi.getSkAttributeName() != null) {
            expressionAttributeValues.put(COLON + gsi.getSkAttributeName(), new AttributeValue().withS(skValue));

            keyConditionExpression.append(AND);

            if (sortKeyCondition == SortKeyCondition.BEGINS_WITH)
                keyConditionExpression.append(sortKeyCondition.getConditionString()).append("(")
                        .append(gsi.getSkAttributeName()).append(", ")
                        .append(COLON).append(gsi.getSkAttributeName()).append(")");
            else
                keyConditionExpression.append(gsi.getSkAttributeName()).append(sortKeyCondition.getConditionString())
                        .append(COLON).append(gsi.getSkAttributeName());
        }

        DynamoDBQueryExpression<T> queryExpression = new DynamoDBQueryExpression<T>()
                .withIndexName(gsi.getIndexName())
                .withConsistentRead(false)
                .withKeyConditionExpression(keyConditionExpression.toString())
                .withExpressionAttributeValues(expressionAttributeValues)
                .withLimit(limit);
        return mapper.query(genericType, queryExpression);
    }
}
