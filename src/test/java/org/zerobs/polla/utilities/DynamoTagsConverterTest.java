package org.zerobs.polla.utilities;

import org.junit.jupiter.api.Test;
import org.zerobs.polla.entities.db.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DynamoTagsConverterTest {
    private final DynamoTagsConverter dynamoTagsConverter = new DynamoTagsConverter();

    @Test
    void test_convert_null_noError() {
        assertDoesNotThrow(() -> {
            dynamoTagsConverter.convert(null);
        });
    }

    @Test
    void test_convert_unConvert() {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag());
        tags.get(0).setName("Science");
        tags.get(0).setId(UUID.randomUUID().toString());
        List<Map<String, String>> tagObjects = dynamoTagsConverter.convert(tags);
        assertEquals(1, tagObjects.size());
        assertEquals(2, tagObjects.get(0).keySet().size());
        assertEquals(tags.get(0).getId(), tagObjects.get(0).get("id"));
        assertEquals(tags.get(0).getName(), tagObjects.get(0).get("name"));

        assertEquals(tags, dynamoTagsConverter.unconvert(tagObjects));
    }

    @Test
    void test_unConvert_null_noError() {
        assertDoesNotThrow(() -> {
            dynamoTagsConverter.unconvert(null);
        });
    }
}