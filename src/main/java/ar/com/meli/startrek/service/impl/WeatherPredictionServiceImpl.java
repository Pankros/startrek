package ar.com.meli.startrek.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import ar.com.meli.startrek.entity.Position;
import ar.com.meli.startrek.entity.WeatherEnum;
import ar.com.meli.startrek.entity.WeatherSeason;
import ar.com.meli.startrek.service.WeatherPredictionService;

@Service
public class WeatherPredictionServiceImpl implements WeatherPredictionService {
    
    private static final Logger logger = LogManager.getLogger(WeatherPredictionServiceImpl.class);

    @Override
    public WeatherEnum getWeatherByDay(Position position1, Position position2, Position position3, Long day) {
        logger.info(String.format("Begin calculating weather for %d day", day));
        WeatherEnum weather;
        try {
            logger.debug(String.format("Planet %s. Position [degrees %d, radius %d] [x %f, y%f]", position1.getPlanet().getName(), position1.getDegree(), position1.getRadius(), position1.getX(), position1.getY()));
            logger.debug(String.format("Planet %s. Position [degrees %d, radius %d] [x %f, y%f]", position2.getPlanet().getName(), position2.getDegree(), position2.getRadius(), position2.getX(), position2.getY()));
            logger.debug(String.format("Planet %s. Position [degrees %d, radius %d] [x %f, y%f]", position3.getPlanet().getName(), position3.getDegree(), position3.getRadius(), position3.getX(), position3.getY()));
            if (this.arePlanetsAlignWithSun(position1, position2, position3)) {
                weather = WeatherEnum.DROUDHT;
            } else if (this.arePlanetsAlign(position1, position2, position3)) {
                weather = WeatherEnum.OPTIMUM;
            } else if (this.isSunInside(position1, position2, position3)) {
                weather = WeatherEnum.RAIN;
            } else {
                weather = WeatherEnum.NORMAL;
            }
            logger.debug(String.format("Prediction %s", weather));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        return weather;
    }

    @Override
    public List<WeatherSeason> predictWeatherSeason(Map<Long, Position> trajectory1, Map<Long, Position> trajectory2, Map<Long, Position> trajectory3, Long days) {
        logger.info(String.format("Begin calculating weather for %d days", days));
        Position position1;
        Position position2;
        Position position3;
        WeatherSeason weatherSeason = null;
        Double maxPerimeter = null;
        List<WeatherSeason> seasons = new ArrayList<>();
        for (Long i = 0l; i <= days; i++) {
            position1 = trajectory1.get(i);
            position2 = trajectory2.get(i);
            position3 = trajectory3.get(i);
            WeatherEnum currentWeather = this.getWeatherByDay(position1, position2, position3, i);
            Double currentPerimeter = getPerimeter(position1, position2, position3);
            if (weatherSeason == null) {
                weatherSeason = new WeatherSeason();
                weatherSeason.setWeather(currentWeather);
                weatherSeason.setDayBegin(i);
                weatherSeason.setMaxPerimeterDay(i);
                maxPerimeter = currentPerimeter;
            } else if (weatherSeason.getWeather() != currentWeather) {
                weatherSeason.setDayEnd(i - 1);
                seasons.add(weatherSeason);
                weatherSeason = new WeatherSeason();
                weatherSeason.setWeather(currentWeather);
                weatherSeason.setDayBegin(i);
                weatherSeason.setMaxPerimeterDay(i);
                maxPerimeter = currentPerimeter;
            }
            if (maxPerimeter < currentPerimeter) {
                maxPerimeter = currentPerimeter;
                weatherSeason.setMaxPerimeterDay(i);
            }
        }
        weatherSeason.setDayEnd(days);
        return seasons;
    }
    
    private boolean arePlanetsAlignWithSun(Position p1, Position p2, Position p3) {
        boolean arePlanetsAlignWithSun = 1 == Stream.of(p1,p2,p3)
            .map(p -> p.getDegree() > 180 ? p.getDegree() - 180 : p.getDegree())
            .peek(d -> logger.debug(String.format("Absolute angle [%d]", d)))
            .distinct()
            .count();
        logger.debug(String.format("Are planets align with sun? %b", arePlanetsAlignWithSun));
        return arePlanetsAlignWithSun;
    }
    
    private double getPerimeter(Position p1, Position p2, Position p3) {
        return getDistance(p1, p2) + getDistance(p1, p3) + getDistance(p2, p3);
    }
    
    private double getDistance(Position p1, Position p2) {
        return Math.sqrt(Math.pow((p1.getX() - p2.getX()),2) + Math.pow((p1.getY() - p2.getY()),2));
    }
    
    private boolean arePlanetsAlign(Position p1, Position p2, Position p3) {
        double slope1 = (p1.getY() - p2.getY()) / (p1.getX() - p2.getX());
        double slope2 = (p1.getY() - p3.getY()) / (p1.getX() - p3.getX());

        if ((Double.isInfinite(slope1) && Double.isInfinite(slope2)))
            return true;
        if ((Double.isInfinite(slope1) || Double.isInfinite(slope2)))
            return false;
        if (slope1 == slope2)
            return true;

        double relation = Math.abs(slope1 / slope2);
        if (0.9 < relation && relation < 1.1)
            return true;
        
        return false;
    }
    
    private boolean isSunInside(Position p1, Position p2, Position p3) {
        Position starPosition = new Position(0d, 0d);
        boolean isSunInside = 1 == Stream.of(triangleOrientation(p1, p2, p3),
                triangleOrientation(p1, p2, starPosition),
                triangleOrientation(p2, p3, starPosition),
                triangleOrientation(p3, p1, starPosition))
        .peek(d -> logger.debug(String.format("Orientation [%d]", d)))
        .distinct()
        .count();
        logger.debug(String.format("Is Sun inside? %b", isSunInside));
        return isSunInside;
    }
    
    private int triangleOrientation(Position p1, Position p2, Position p3) {
        //(A1.x - A3.x)*(A2.y - A3.y)-(A1.y - A3.y)*(A2.x - A3.x)
        double orientation = (p1.getX()-p3.getX()) * (p2.getY() - p3.getY()) - (p1.getY() - p3.getY()) * (p2.getX() - p3.getX());
        return orientation >= 0 ? 1 : -1;
    }
    
}
