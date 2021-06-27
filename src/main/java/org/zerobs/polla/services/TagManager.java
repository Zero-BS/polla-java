package org.zerobs.polla.services;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.zerobs.polla.entities.db.Tag;
import org.zerobs.polla.exception.CustomException;

import java.util.List;

public interface TagManager {
    List<Tag> add(List<Tag> tags) throws CustomException;

    List<Tag> search(String nameInitials);

    List<DynamoDBMapper.FailedBatch> delete(List<Tag> tags);
}
