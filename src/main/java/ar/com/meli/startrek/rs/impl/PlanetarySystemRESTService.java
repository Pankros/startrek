package ar.com.meli.startrek.rs.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.meli.startrek.dto.WeatherDayDTO;
import ar.com.meli.startrek.dto.WeatherSeasonDTO;
import ar.com.meli.startrek.dto.WeatherSeasonRainDTO;
import ar.com.meli.startrek.dto.WeatherSeasonResponseDTO;
import ar.com.meli.startrek.entity.WeatherDay;
import ar.com.meli.startrek.entity.WeatherEnum;
import ar.com.meli.startrek.entity.WeatherSeason;
import ar.com.meli.startrek.rs.validation.ValidationService;
import ar.com.meli.startrek.service.impl.PlanetarySystemServiceImpl;

@RestController
@RequestMapping("/planetarysystem")
public class PlanetarySystemRESTService {

    private static final Logger logger = LogManager.getLogger(PlanetarySystemRESTService.class);
    
    @Autowired
    private PlanetarySystemServiceImpl planetarySystemServiceImpl;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @RequestMapping(value = "/weather", method = RequestMethod.GET)
    public ResponseEntity<?> getWeatherByDay(@RequestParam(name="day") Long day) {
        logger.debug(String.format("getting weather for day %d", day));
        WeatherDayDTO response;
        WeatherDay weatherDay;
        try {
            ValidationService.getWeatherByDay(day);
            weatherDay = planetarySystemServiceImpl.getWeatherByDay(day);
            if (weatherDay == null) {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
            response = modelMapper.map(weatherDay, WeatherDayDTO.class);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<WeatherDayDTO>(response, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/weather/type/{weather}", method = RequestMethod.GET)
    public ResponseEntity<?> getDaysByWeather(@PathVariable(name="weather") String weather) {
        logger.debug(String.format("getting periodos of %s ", weather));
        WeatherEnum weatherEnum;
        List<WeatherSeason> weatherSeasons;
        WeatherSeasonResponseDTO response = new WeatherSeasonResponseDTO();
        try {
            ValidationService.getDaysByWeather(weather);
            weatherEnum = Enum.valueOf(WeatherEnum.class, weather);
            weatherSeasons = planetarySystemServiceImpl.getAllSeasonByWeather(weatherEnum);
            if (weatherSeasons == null || weatherSeasons.isEmpty()) {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
            if (weatherEnum.equals(WeatherEnum.RAIN)) {
                response.setSeasons(weatherSeasons.stream()
                                        .map(w -> modelMapper.map(w, WeatherSeasonRainDTO.class))
                                        .collect(Collectors.toList()));
            } else {
                response.setSeasons(weatherSeasons.stream()
                        .map(w -> modelMapper.map(w, WeatherSeasonDTO.class))
                        .collect(Collectors.toList()));
            }
            response.setTotal(weatherSeasons.size());
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<WeatherSeasonResponseDTO>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/weather/prediction/years/{years}", method = RequestMethod.POST)
    public ResponseEntity<?> generatePredictionWeatherForYears(@PathVariable(name="years") Long years) {
        logger.debug(String.format("generation weather prediction for %d years", years));
        int seasons = 0;
        try {
            ValidationService.generatePredictionWeatherForYears(years);
            seasons = planetarySystemServiceImpl.generatePredictionWeatherForYears(years);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<WeatherDay>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(seasons, HttpStatus.OK);
    }
}
