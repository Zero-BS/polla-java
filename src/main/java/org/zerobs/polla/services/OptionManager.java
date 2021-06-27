package org.zerobs.polla.services;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.zerobs.polla.entities.db.Option;
import org.zerobs.polla.exception.CustomException;

import java.util.List;

public interface OptionManager {
    List<Option> add(List<String> options, String pollId) throws CustomException;

    List<DynamoDBMapper.FailedBatch> delete(List<Option> options);
}
