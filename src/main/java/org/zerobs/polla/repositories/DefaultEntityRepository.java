package org.zerobs.polla.repositories;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.stereotype.Repository;

import java.lang.reflect.ParameterizedType;

@Repository
public class DefaultEntityRepository<T> implements EntityRepository<T> {
    private static final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
    protected static final DynamoDBMapper mapper = new DynamoDBMapper(client);

    @Override
    public void save(T object) {
        mapper.save(object);
    }

    @Override
    public T get(String id) {
        return mapper.load((Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0], id);
    }

    @Override
    public void delete(T object) {
        mapper.delete(object);
    }
}
