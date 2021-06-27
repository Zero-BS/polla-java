package org.zerobs.polla.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GeoName {
    @JsonProperty("geonameId")
    private int geoNameId;
    private String name;
}
