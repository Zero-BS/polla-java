package org.zerobs.polla.utilities;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class DynamoLocationsConverter implements DynamoDBTypeConverter<List<Integer>, String> {
    private static final String SEPARATOR = "#";

    @Override
    public List<Integer> convert(String dbString) {
        if (dbString == null)
            return new ArrayList<>();
        return Arrays.stream(dbString.split(SEPARATOR)).map(Integer::parseInt).collect(toList());
    }

    @Override
    public String unconvert(List<Integer> locationIds) {
        if (locationIds == null)
            return null;
        return locationIds.stream().map(String::valueOf).collect(joining(SEPARATOR));
    }
}
