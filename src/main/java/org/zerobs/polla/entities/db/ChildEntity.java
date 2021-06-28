package org.zerobs.polla.entities.db;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Locale;

import static org.zerobs.polla.constants.ApplicationConstants.TABLE_NAME;

@EqualsAndHashCode(callSuper = true)
@Data
@DynamoDBTable(tableName = TABLE_NAME)
public abstract class ChildEntity extends Entity {
    private final String parentEntityClassIdentifier;
    private String parentId;

    protected ChildEntity(Class<? extends Entity> parentClass) {
        this.parentEntityClassIdentifier = parentClass.getSimpleName().toUpperCase(Locale.ROOT);
    }

    @Override
    protected String getPkInitials() {
        return parentEntityClassIdentifier + SEPARATOR;
    }

    @Override
    public String getPk() {
        return getPkInitials() + parentId;
    }

    @Override
    public void setPk(String pk) {
        this.pk = pk;
        parentId = pk.substring(getPkInitials().length());
    }

    private String getSkInitials() {
        return getEntityClassIdentifier() + SEPARATOR;
    }

    @Override
    public String getSk() {
        return getSkInitials() + getId();
    }

    @Override
    public void setSk(String sk) {
        this.sk = sk;
        id = sk.substring(getSkInitials().length());
    }
}
