package ar.com.meli.startrek.service.impl;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import ar.com.meli.startrek.entity.DirectionEnum;
import ar.com.meli.startrek.entity.Planet;
import ar.com.meli.startrek.entity.Position;
import ar.com.meli.startrek.service.TrajectoryPredictionService;

@Service
public class TrajectoryPredictionServiceImpl implements TrajectoryPredictionService {
    
    private static final Logger logger = LogManager.getLogger(TrajectoryPredictionServiceImpl.class);
    
    @Override
    public Position calculatePosition(Planet planet, Long day) {
        Position position;
        try {
            logger.info(String.format("Calculating position for planet %s", planet.getName()));
            logger.info(String.format("Distance from SUN %d km", planet.getDistanceFromSun()));
            logger.info(String.format("Velocity %d degree per day %s", planet.getDegreePerDay(), planet.getDirection()));
            logger.info(String.format("Direction %s", planet.getDirection()));
            position = planetPosition(planet, day);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        return position;
    }
    
    @Override
    public Map<Long, Position> calculateTrajectory(Planet planet, Long days) {
        Map<Long, Position> positions;
        try {
            logger.info(String.format("Calculating trajectory for planet %s", planet.getName()));
            logger.info(String.format("Distance from SUN %d km", planet.getDistanceFromSun()));
            logger.info(String.format("Velocity %d degree per day %s", planet.getDegreePerDay(), planet.getDirection()));
            logger.info(String.format("Direction %s", planet.getDirection()));
            
            positions = Stream.iterate(0l, i -> i + 1)
                        .limit(days)
                        .map(i -> planetPosition(planet, i))
                        .collect(Collectors.toMap(p -> p.getDay(), p -> p));
            logger.info(String.format("Done trajectory for %s", planet.getName()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        return positions;
    }
    
    private Position planetPosition(Planet planet, Long day) {
        Long degree = this.getAbsoluteDegree(planet.getDirection(), planet.getDegreePerDay(), day);
        double x = this.getPositionX(degree.intValue(), planet.getDistanceFromSun());
        double y = this.getPositionY(degree.intValue(), planet.getDistanceFromSun());
        logger.debug(String.format("[Day %d] [degrees %d] [x %f, y%f]", day, degree, x, y));
        return new Position(degree.intValue(), planet.getDistanceFromSun(), x, y, planet, day);
    }
    
    private Long getAbsoluteDegree(DirectionEnum direction, int speed, Long day) {
        Long degree = (direction.getValue() * (speed * day)) % 360;
        return  degree < 0 ? 360 + degree : degree;
    }

    private double getPositionX(int degree, int radius) {
        return Math.cos(Math.toRadians(degree)) * radius;
    }
    
    private double getPositionY(int degree, int radius) {
        return Math.sin(Math.toRadians(degree)) * radius;
    }
    
}
