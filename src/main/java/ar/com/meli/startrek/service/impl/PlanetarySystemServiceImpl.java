package ar.com.meli.startrek.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    
    @Value("${prediction.period}")
    private Long period;
    
    @Value("${meaning.of.life}")
    private String meaningOfLife;
    

    @Value("${prediction.at.start}")
    private boolean predictAtStart;
    
    private Planet ferengi;
    private Planet vulcano;
    private Planet betasoide;

    @Override
    public WeatherDay getWeatherByDay(Long day) {
        WeatherDay weatherDay;
        WeatherSeason weatherSeason;
        try {
            //buscamos el dia dentro del primer periodo
            //calculandole el modulo
            weatherSeason = weatherSeasonRepository.findByDayBeginLessThanEqualAndDayEndGreaterThanEqual(day % 360, day % 360);
            weatherDay = new WeatherDay(day, weatherSeason.getWeather());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        return weatherDay;
    }

    @Override
    public List<WeatherSeason> getAllSeasonByWeather(WeatherEnum weather, Long periods) {
        List<WeatherSeason> weatherSeasons;
        List<WeatherSeason> weatherSeasonsResponse = new ArrayList<>();
        try {
            //Nos traemos la informacion del clima para el periodo 1 
            if (weather.equals(WeatherEnum.ALL)) {
                weatherSeasons = weatherSeasonRepository.findAll();
            } else {
                weatherSeasons = weatherSeasonRepository.findAllByWeather(weather);
            }
            weatherSeasonsResponse.addAll(weatherSeasons);
            //Generamos los periodos que sean necesarios
            for (long i = 1; i < (periods != null ? periods : 0); i++) {
                final long lambda = i;
                weatherSeasonsResponse.addAll(
                    weatherSeasons.stream()
                        .map(ws -> new WeatherSeason(ws.getId(), 
                                ws.getWeather() , 
                                (ws.getDayBegin() + (lambda*360)), 
                                (ws.getDayEnd() + (lambda*360)), 
                                (ws.getMaxPerimeterDay() + (lambda*360))))
                        .collect(Collectors.toList()));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        return weatherSeasonsResponse;
    }

    @Override
    public String generatePredictionWeatherForPeriods(Long periods) {
        logger.info(String.format("Preparing prediction for %d periods", periods));
        List<WeatherSeason> seasons;
        try {
            //Dado que cada 360 dias el ciclo se repite 
            //Solo es necesario calcular un ciclo completo
            //Lo demas se lo dejamos a getAllSeasonByWeather y getWeatherByDay
            seasons = this.generatePredictionWeatherForDays(360l);
            weatherSeasonRepository.deleteAll();
            weatherSeasonRepository.saveAll(seasons);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        return meaningOfLife;
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

            seasons = weatherPredictionService.predictWeatherSeason(    trajectoryFerengi, 
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
        if (predictAtStart) generatePredictionWeatherForPeriods(period);
    }
}
