package org.zerobs.polla.utilities;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.zerobs.polla.entities.db.Tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DynamoTagsConverter implements DynamoDBTypeConverter<List<Map<String, String>>, List<Tag>> {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<Map<String, String>> convert(List<Tag> tags) {
        return Utils.cleanList(tags).stream().map(tag -> {
            var result = new HashMap<String, String>();
            result.put("id", tag.getId());
            result.put("name", tag.getName());
            return result;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Tag> unconvert(List<Map<String, String>> tagObjects) {
        return mapper.convertValue(tagObjects, new TypeReference<>() {
        });
    }
}