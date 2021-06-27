package org.zerobs.polla.entities.db;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import static org.zerobs.polla.constants.ApplicationConstants.TABLE_NAME;

@DynamoDBTable(tableName = TABLE_NAME)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Option extends ChildEntity {
    private String text;
    private Integer votes;

    public Option() {
        super(Poll.class);
    }
}
