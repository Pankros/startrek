package ar.com.meli.startrek.entity;

public class Planet {

    private String name;
    private int distanceFromSun;
    private int degreePerDay;
    private DirectionEnum direction;
    
    public Planet() {}
    
    public Planet(String name, int distanceFromSun, int degreePerDay, DirectionEnum direction) {
        this.name = name;
        this.distanceFromSun = distanceFromSun;
        this.degreePerDay = degreePerDay;
        this.direction = direction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDistanceFromSun() {
        return distanceFromSun;
    }

    public void setDistanceFromSun(int distanceFromSun) {
        this.distanceFromSun = distanceFromSun;
    }

    public int getDegreePerDay() {
        return degreePerDay;
    }

    public void setDegreePerDay(int degreePerDay) {
        this.degreePerDay = degreePerDay;
    }

    public DirectionEnum getDirection() {
        return direction;
    }

    public void setDirection(DirectionEnum direction) {
        this.direction = direction;
    }

}
