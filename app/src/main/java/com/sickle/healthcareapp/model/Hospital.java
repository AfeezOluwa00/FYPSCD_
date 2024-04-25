package com.sickle.healthcareapp.model;

import com.google.gson.annotations.SerializedName;

public class Hospital {

    @SerializedName("name")
    private String name;

    @SerializedName("geometry")
    private Geometry geometry;

    // Add other fields as needed

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return geometry.getLocation().getLat();
    }

    public double getLongitude() {
        return geometry.getLocation().getLng();
    }

    // Add getters for other fields

    private static class Geometry {
        @SerializedName("location")
        private Location location;

        public Location getLocation() {
            return location;
        }
    }

    private static class Location {
        @SerializedName("lat")
        private double lat;

        @SerializedName("lng")
        private double lng;

        public double getLat() {
            return lat;
        }

        public double getLng() {
            return lng;
        }
    }
}
