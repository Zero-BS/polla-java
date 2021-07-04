package org.zerobs.polla.services;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerobs.polla.entities.db.Tag;
import org.zerobs.polla.exception.CustomException;
import org.zerobs.polla.exception.CustomRuntimeException;
import org.zerobs.polla.repositories.TagRepository;
import org.zerobs.polla.utilities.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static org.zerobs.polla.exception.RuntimeExceptionType.*;

@Service
public class DefaultTagManager implements TagManager {
    @Autowired
    private TagRepository tagRepository;

    private Tag get(String id) {
        var tag = new Tag();
        tag.setId(id);
        return tagRepository.getByPk(tag.getPk());
    }

    @Override
    public List<Tag> add(List<Tag> tags) throws CustomException {
        List<Tag> newTags = new ArrayList<>();
        for (var i = 0; i < tags.size(); i++) {
            String tagId = tags.get(i).getId();
            if (tagId != null) {
                tags.set(i, get(tagId));
                if (tags.get(i) == null)
                    throw new CustomRuntimeException(INVALID_TAG_ID, new String[]{tagId});
            } else {
                var tag = tagRepository.getByName(tags.get(i).getName());
                if (tag != null) {
                    tags.set(i, tag);
                    continue;
                }
                if (StringUtils.isBlank(tags.get(i).getName()))
                    throw new CustomRuntimeException(EMPTY_TAG_NAME);
                tag = new Tag(tags.get(i).getName());
                tag.setId(UUID.randomUUID().toString());
                tag.setFollowers(0);
                tag.setCreatedOn(System.currentTimeMillis());
                tag.setCreatedBy(Utils.getUserId());
                tag.setNewTag(true);
                tags.set(i, tag);
                newTags.add(tag);
            }
        }
        tagRepository.batchSave(newTags);
        return tags;
    }

    @Override
    public List<Tag> search(String nameInitials) {
        if (nameInitials == null)
            throw new CustomRuntimeException(EMPTY_TAG_NAME_INITIALS);
        nameInitials = nameInitials.trim().toLowerCase(Locale.ROOT);
        if (nameInitials.length() < 3)
            throw new CustomRuntimeException(SMALL_TAG_NAME_INITIALS);
        return tagRepository.search(nameInitials, 5);
    }

    @Override
    public List<DynamoDBMapper.FailedBatch> delete(List<Tag> tags) {
        return tagRepository.batchDelete(tags);
    }
}
