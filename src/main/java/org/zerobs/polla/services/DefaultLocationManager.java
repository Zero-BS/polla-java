package org.zerobs.polla.services;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.zerobs.polla.entities.GeoName;
import org.zerobs.polla.entities.GeoNamesResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DefaultLocationManager implements LocationManager {
    private static final String GEONAMES_BASE_URL = "http://api.geonames.org";
    private static final int EARTH_GEONAME_ID = 6295630;
    private static final String CHILDREN_JSON_PATH = "childrenJSON";
    private static final String GET_JSON_PATH = "getJSON";
    private static final String GEONAME_ID_QUERY = "geonameId";
    private static final String USERNAME_QUERY = "username";
    @Autowired
    private ObjectProvider<RestConsumer> restConsumers;
    @Value("${geoname.username}")
    private String geoNameUsername;

    @Override
    public List<GeoName> getContinents() {
        return getChildren(EARTH_GEONAME_ID);
    }

    @Override
    public List<GeoName> getChildren(int geoNameId) {
        GeoNamesResponse geoNamesResponse = getGeoNamesConsumer().get(CHILDREN_JSON_PATH, getQueryParams(geoNameId));
        return geoNamesResponse == null ? new ArrayList<>() : geoNamesResponse.getGeoNames();
    }

    @Override
    public boolean validate(int geoNameId) {
        return getGeoNamesConsumer().<GeoName>getResponseEntity(GET_JSON_PATH, getQueryParams(geoNameId),
                HttpStatus.NOT_FOUND).getStatusCode().equals(HttpStatus.NOT_FOUND);
    }

    private Map<String, Object> getQueryParams(int geoNameId) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put(GEONAME_ID_QUERY, geoNameId);
        queryParams.put(USERNAME_QUERY, geoNameUsername);
        return queryParams;
    }

    private RestConsumer getGeoNamesConsumer() {
        return restConsumers.getObject(GEONAMES_BASE_URL);
    }
}
