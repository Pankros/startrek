package ar.com.meli.startrek.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import ar.com.meli.startrek.dao.WeatherSeasonRepository;
import ar.com.meli.startrek.entity.DirectionEnum;
import ar.com.meli.startrek.entity.Planet;
import ar.com.meli.startrek.entity.Position;
import ar.com.meli.startrek.entity.WeatherDay;
import ar.com.meli.startrek.entity.WeatherEnum;
import ar.com.meli.startrek.entity.WeatherSeason;
import ar.com.meli.startrek.service.PlanetarySystemService;
import ar.com.meli.startrek.service.TrajectoryPredictionService;
import ar.com.meli.startrek.service.WeatherPredictionService;

@Service
@PropertySource({ "classpath:application.properties" })
public class PlanetarySystemServiceImpl implements PlanetarySystemService {

    private static final Logger logger = LogManager.getLogger(PlanetarySystemServiceImpl.class);

    @Autowired
    private TrajectoryPredictionService planetarySystemUtil;

    @Autowired
    private WeatherPredictionService weatherPredictionService;

    @Autowired
    private WeatherSeasonRepository weatherSeasonRepository;
    
    @Value("${prediction.years}")
    private Long years;
    
    @Value("${prediction.at.start}")
    private boolean predictAtStart;

    private Planet ferengi;
    private Planet vulcano;
    private Planet betasoide;

    @Override
    public WeatherDay getWeatherByDay(Long day) {
        WeatherDay weatherDay;
        try {
            weatherDay = calculateWeatherByDay(day);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        return weatherDay;
    }

    private WeatherDay calculateWeatherByDay(Long day) {
        Position ferengiPosition;
        Position vulcanoPosition;
        Position batasoiddePosition;
        WeatherEnum weather;
        try {
            ferengiPosition = planetarySystemUtil.calculatePosition(ferengi, day);
            vulcanoPosition = planetarySystemUtil.calculatePosition(vulcano, day);
            batasoiddePosition = planetarySystemUtil.calculatePosition(betasoide, day);
            weather = weatherPredictionService.getWeatherByDay(ferengiPosition, 
                                                                        vulcanoPosition,
                                                                        batasoiddePosition, 
                                                                        day);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        return new WeatherDay(Long.valueOf(day), weather);
    }
    
    @Override
    public List<WeatherSeason> getAllSeasonByWeather(WeatherEnum weather) {
        List<WeatherSeason> weatherSeasons;
        try {
            weatherSeasons = weatherSeasonRepository.findAllByWeather(weather);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        return weatherSeasons;
    }

    @Override
    public int generatePredictionWeatherForYears(Long years) {
        logger.info(String.format("Preparing prediction for %d years", years));
        Long days;
        List<WeatherSeason> seasons;
        try {
            days = years * 365;
            seasons = this.generatePredictionWeatherForDays(days);
            weatherSeasonRepository.deleteAll();
            weatherSeasonRepository.saveAll(seasons);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        return seasons.size();
    }

    private List<WeatherSeason> generatePredictionWeatherForDays(Long days) {
        logger.info(String.format("Preparing prediction for %d days", days)); 
        Map<Long, Position> trajectoryFerengi;
        Map<Long, Position> trajectoryVulcano;
        Map<Long, Position> trajectoryBetasoide;
        List<WeatherSeason> seasons;
        try {
            logger.info(String.format("Begin calculate trajectory"));
            trajectoryFerengi = planetarySystemUtil.calculateTrajectory(ferengi, days);
            trajectoryVulcano = planetarySystemUtil.calculateTrajectory(vulcano, days);
            trajectoryBetasoide = planetarySystemUtil.calculateTrajectory(betasoide, days);

            seasons = weatherPredictionService.predictWeatherSeason(  trajectoryFerengi, 
                                                                trajectoryVulcano, 
                                                                trajectoryBetasoide, 
                                                                days);
            
            logger.info(String.format("End calculate trajectory"));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        return seasons;
    }

    @PostConstruct
    public void init() {
        logger.info(String.format("Creating planets"));
        ferengi = new Planet("Ferengi", 500, 1, DirectionEnum.CLOCKWISE);
        vulcano = new Planet("Vulcano", 1000, 5, DirectionEnum.COUNTER_CLOCKWISE);
        betasoide = new Planet("Betasoide", 2000, 3, DirectionEnum.CLOCKWISE);
        logger.info(String.format("Planets Done"));
        if (predictAtStart) generatePredictionWeatherForYears(years);
    }
}
