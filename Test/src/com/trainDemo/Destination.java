package com.trainDemo;

public class Destination {

    public String toLocation;
    public int distance;
    public boolean visited;

    public Destination() {
    }

    public Destination(String toLocation, int distance, boolean visited) {
        this.toLocation = toLocation;
        this.distance = distance;
        this.visited = visited;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public boolean getVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
