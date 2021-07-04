package org.zerobs.polla.entities.db;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zerobs.polla.utilities.DynamoTagsConverter;

import java.util.List;

import static org.zerobs.polla.constants.ApplicationConstants.TABLE_NAME;

@DynamoDBTable(tableName = TABLE_NAME)
@EqualsAndHashCode(callSuper = true)
@Data
public class Poll extends CreatableEntity {
    private String title;
    private String description;
    @DynamoDBAttribute(attributeName = "expires_on")
    private Long expiresOn;
    @DynamoDBAttribute(attributeName = "publish_on")
    private Long publishOn;
    @DynamoDBAttribute(attributeName = "open_location")
    private Integer openLocation;
    @DynamoDBAttribute(attributeName = "vote_change_allowed")
    private boolean voteChangeAllowed;
    @DynamoDBIgnore
    private List<String> options;
    @DynamoDBTypeConverted(converter = DynamoTagsConverter.class)
    private List<Tag> tags;
    private Integer votes;
}
