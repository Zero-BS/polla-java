package org.zerobs.polla.entities.db;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.zerobs.polla.entities.Gender;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Locale;

import static org.zerobs.polla.constants.ApplicationConstants.TABLE_NAME;

@DynamoDBTable(tableName = TABLE_NAME)
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class User extends CreatableEntity {
    private static final String GSI_NAME_USERNAME = "GSI-username";
    public static final GSI GSI_USERNAME = new GSI(GSI_NAME_USERNAME, "username");
    @DynamoDBIndexHashKey(globalSecondaryIndexName = GSI_NAME_USERNAME)
    private String username;
    @DynamoDBAttribute(attributeName = "year_of_birth")
    private Integer yearOfBirth;
    @DynamoDBTypeConvertedEnum
    private Gender gender;
    private String locale;
    private String email;
    @DynamoDBAttribute(attributeName = "email_verified")
    private Boolean emailVerified;
    @DynamoDBIgnore
    @Null
    private String createdBy;

    public User(Jwt principal) {
        setId(principal);
    }

    public void setId(@NotNull Jwt principal) {
        id = principal.getIssuer().getAuthority().toUpperCase(Locale.ROOT) + SEPARATOR + principal.getSubject();
    }
}
