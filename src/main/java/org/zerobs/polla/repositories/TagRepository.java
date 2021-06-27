package org.zerobs.polla.repositories;

import org.zerobs.polla.entities.db.Tag;

import java.util.List;

public interface TagRepository extends EntityRepository<Tag> {
    Tag getByName(String name);

    List<Tag> search(String name, int limit);
}
