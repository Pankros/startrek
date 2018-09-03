package ar.com.meli.startrek.service;

import java.util.List;

import ar.com.meli.startrek.entity.WeatherDay;
import ar.com.meli.startrek.entity.WeatherEnum;
import ar.com.meli.startrek.entity.WeatherSeason;

public interface PlanetarySystemService {

    WeatherDay getWeatherByDay(Long day);

    List<WeatherSeason> getAllSeasonByWeather(WeatherEnum weather);

    int generatePredictionWeatherForYears(Long years);

}
