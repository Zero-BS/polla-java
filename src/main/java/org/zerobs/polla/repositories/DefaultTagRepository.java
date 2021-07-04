package org.zerobs.polla.repositories;

import org.springframework.stereotype.Repository;
import org.zerobs.polla.entities.db.SortKeyCondition;
import org.zerobs.polla.entities.db.Tag;

import java.util.List;

@Repository
public class DefaultTagRepository extends DefaultEntityRepository<Tag> implements TagRepository {
    @Override
    public Tag getByName(String name) {
        return getByIndex(Tag.GSI_TAG_NAME, Tag.TAG_GSI_PK_VALUE, new Tag(name).getLoweredNameName());
    }

    @Override
    public List<Tag> search(String name, int limit) {
        return getByIndex(Tag.GSI_TAG_NAME, Tag.TAG_GSI_PK_VALUE, name, SortKeyCondition.BEGINS_WITH, limit);
    }
}
