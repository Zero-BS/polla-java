package org.zerobs.polla.services;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerobs.polla.entities.db.Option;
import org.zerobs.polla.exception.CustomException;
import org.zerobs.polla.repositories.EntityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class DefaultOptionManager implements OptionManager {
    @Autowired
    private EntityRepository<Option> optionRepository;

    @Override
    public List<Option> add(List<String> optionTexts, String pollId) throws CustomException {
        List<Option> options = new ArrayList<>();
        for (String optionText : optionTexts) {
            var option = new Option();
            option.setText(optionText);
            option.setId(UUID.randomUUID().toString());
            option.setVotes(0);
            option.setParentId(pollId);
            options.add(option);
        }
        optionRepository.batchSave(options);
        return options;
    }

    @Override
    public List<DynamoDBMapper.FailedBatch> delete(List<Option> options) {
        return optionRepository.batchDelete(options);
    }
}
