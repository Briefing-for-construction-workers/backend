package com.constructionnote.constructionnote.component;

import org.springframework.stereotype.Component;

@Component
public class GeoUtils {
    private static final double EARTH_RADIUS = 6371.01; // Earth's radius in km

    public double[] getBoundingBox(double latitude, double longitude, double distance) {
        double radLat = Math.toRadians(latitude);
        double radLon = Math.toRadians(longitude);

        double radDist = distance / EARTH_RADIUS;

        double minLat = radLat - radDist;
        double maxLat = radLat + radDist;

        double minLon, maxLon;
        if (minLat > -Math.PI / 2 && maxLat < Math.PI / 2) {
            double deltaLon = Math.asin(Math.sin(radDist) / Math.cos(radLat));
            minLon = radLon - deltaLon;
            if (minLon < -Math.PI) {
                minLon += 2 * Math.PI;
            }
            maxLon = radLon + deltaLon;
            if (maxLon > Math.PI) {
                maxLon -= 2 * Math.PI;
            }
        } else {
            minLat = Math.max(minLat, -Math.PI / 2);
            maxLat = Math.min(maxLat, Math.PI / 2);
            minLon = -Math.PI;
            maxLon = Math.PI;
        }

        return new double[]{Math.toDegrees(minLat), Math.toDegrees(minLon), Math.toDegrees(maxLat), Math.toDegrees(maxLon)};
    }
}
