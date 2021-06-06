package org.zerobs.polla.entities.db;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.zerobs.polla.entities.Gender;

@DynamoDBTable(tableName = "polla")
@Data
public class User {
    public static final String GSI_USERNAME = "GSI-username";
    private static final String DB_SEPARATOR = "#";
    private static final String ENTITY_IDENTIFIER = "ENTITY";
    private static final String ENTITY_TYPE_IDENTIFIER = "USER";
    @DynamoDBIgnore
    private String id;
    @JsonIgnore
    private String pk;
    @JsonIgnore
    private String sk;
    @DynamoDBAttribute
    @DynamoDBIndexHashKey(globalSecondaryIndexName = GSI_USERNAME)
    private String username;
    @DynamoDBAttribute(attributeName = "year_of_birth")
    @JsonProperty("year_of_birth")
    private Integer yearOfBirth;
    @DynamoDBIgnore
    private Gender gender;
    @DynamoDBAttribute
    private String locale;
    @DynamoDBAttribute
    private String email;
    @DynamoDBAttribute(attributeName = "email_verified")
    private Boolean emailVerified;
    @DynamoDBAttribute(attributeName = "created_on")
    @JsonIgnore
    private Long createdOn;
    @DynamoDBAttribute(attributeName = "updated_on")
    @JsonIgnore
    private Long updatedOn;

    @DynamoDBIgnore
    public static String getPkInitials() {
        return ENTITY_IDENTIFIER + DB_SEPARATOR + ENTITY_TYPE_IDENTIFIER + DB_SEPARATOR;
    }

    @DynamoDBIgnore
    public static String getPk(String id) {
        return getPkInitials() + id;
    }

    @DynamoDBHashKey
    @DynamoDBAttribute
    public String getPk() {
        return getPk(id);
    }

    public void setPk(String pk) {
        this.pk = pk;
        id = pk.substring(getPkInitials().length());
    }

    @DynamoDBRangeKey
    @DynamoDBAttribute
    public String getSk() {
        return getPk();
    }

    @DynamoDBAttribute(attributeName = "gender")
    @JsonIgnore
    public String getGenderDBString() {
        return gender.getDbString();
    }

    public void setGenderDBString(String genderDBString) {
        gender = Gender.fromDBString(genderDBString);
    }
}
