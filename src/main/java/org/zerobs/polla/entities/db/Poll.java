package org.zerobs.polla.entities.db;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zerobs.polla.utilities.DynamoLocationsConverter;
import org.zerobs.polla.utilities.DynamoTagsConverter;

import java.util.List;

import static org.zerobs.polla.constants.ApplicationConstants.TABLE_NAME;

@DynamoDBTable(tableName = TABLE_NAME)
@EqualsAndHashCode(callSuper = true)
@Data
public class Poll extends CreatableEntity {
    private String title;
    private String description;
    @DynamoDBAttribute(attributeName = "user_id")
    private String userId;
    @DynamoDBAttribute(attributeName = "expires_on")
    private Long expiresOn;
    private Long publishOn;
    @DynamoDBTypeConverted(converter = DynamoLocationsConverter.class)
    @DynamoDBAttribute(attributeName = "locations_open")
    private List<Integer> locationsOpen;
    @DynamoDBAttribute(attributeName = "vote_change_allowed")
    private boolean voteChangeAllowed;
    private List<String> options;
    @DynamoDBTypeConverted(converter = DynamoTagsConverter.class)
    private List<Tag> tags;
    private Integer votes;
}
