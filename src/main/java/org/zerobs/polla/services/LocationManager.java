package org.zerobs.polla.services;

import org.zerobs.polla.entities.GeoName;

import java.util.List;

public interface LocationManager {
    List<GeoName> getContinents();

    List<GeoName> getChildren(int locationId);

    boolean validate(int locationId);
}
