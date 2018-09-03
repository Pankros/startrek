package ar.com.meli.startrek.service;

import java.util.List;
import java.util.Map;

import ar.com.meli.startrek.entity.Position;
import ar.com.meli.startrek.entity.WeatherEnum;
import ar.com.meli.startrek.entity.WeatherSeason;

public interface WeatherPredictionService {
    
    WeatherEnum getWeatherByDay(Position position1, Position position2, Position position3, Long day);
    
    List<WeatherSeason> predictWeatherSeason(Map<Long, Position> trajectory1, Map<Long, Position> trajectory2, Map<Long, Position> trajectory3, Long days);

}
