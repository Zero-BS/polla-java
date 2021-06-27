package org.zerobs.polla.entities.db;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.util.Locale;

import static org.zerobs.polla.constants.ApplicationConstants.TABLE_NAME;

@EqualsAndHashCode(callSuper = true)
@DynamoDBTable(tableName = TABLE_NAME)
@Data
@NoArgsConstructor
public class Tag extends CreatableEntity {
    @JsonIgnore
    @DynamoDBAttribute(attributeName = PK_TAG_NAME_GSI)
    @DynamoDBIndexHashKey(globalSecondaryIndexName = GSI_NAME_TAG_NAME)
    public static final String TAG_GSI_PK = "TAG";
    private static final String GSI_NAME_TAG_NAME = "GSI-tag-name";
    private static final String PK_TAG_NAME_GSI = "tag_name_gsi_pk";
    private static final String SK_TAG_NAME_GSI = "lowered_name_name";
    public static final GSI GSI_TAG_NAME = new GSI(GSI_NAME_TAG_NAME, PK_TAG_NAME_GSI, SK_TAG_NAME_GSI);
    @DynamoDBIgnore
    @JsonIgnore
    @Null
    protected Long updatedOn;
    private String name;
    private Integer followers;
    @JsonIgnore
    @DynamoDBAttribute(attributeName = SK_TAG_NAME_GSI)
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = GSI_NAME_TAG_NAME)
    private String loweredNameName;
    @JsonIgnore
    @DynamoDBIgnore
    private boolean newTag;

    public Tag(String name) {
        this.name = name;
    }

    public String getLoweredNameName() {
        if (name == null)
            return null;
        return name.toLowerCase(Locale.ROOT) + SEPARATOR + name;
    }

}
