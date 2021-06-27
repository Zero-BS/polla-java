package org.zerobs.polla.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GeoNamesResponse {
    @JsonProperty("geonames")
    private List<GeoName> geoNames;
}
