package ar.com.meli.startrek.entity;

public class Position {

    private int degree;

    private int radius;
    
    private double x;
    
    private double y;
    
    private Planet planet;
    
    private Long day;
    
    public Position() {}

    public Position(int degree, int radius, double x, double y, Planet planet, Long day) {
        this.degree = degree;
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.planet = planet;
        this.day = day;
    }
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Planet getPlanet() {
        return planet;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
    }

    public Long getDay() {
        return day;
    }

    public void setDay(Long day) {
        this.day = day;
    }

}
