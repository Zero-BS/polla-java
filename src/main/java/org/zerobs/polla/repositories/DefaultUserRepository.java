package org.zerobs.polla.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.springframework.stereotype.Repository;
import org.zerobs.polla.entities.db.User;

import java.util.HashMap;
import java.util.Map;

@Repository
public class DefaultUserRepository extends DefaultEntityRepository<User> implements UserRepository {
    @Override
    public boolean usernameExists(String username) {
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":v1", new AttributeValue().withS(username));

        DynamoDBQueryExpression<User> queryExpression = new DynamoDBQueryExpression<User>()
                .withIndexName(User.GSI_USERNAME)
                .withConsistentRead(false)
                .withKeyConditionExpression("username = :v1")
                .withExpressionAttributeValues(expressionAttributeValues);

        return !mapper.query(User.class, queryExpression).isEmpty();
    }
}
