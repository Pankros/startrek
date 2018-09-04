package ar.com.meli.startrek.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.com.meli.startrek.entity.WeatherEnum;
import ar.com.meli.startrek.entity.WeatherSeason;

@Repository
public interface WeatherSeasonRepository extends CrudRepository<WeatherSeason, Long> {

    List<WeatherSeason> findAllByWeather(WeatherEnum weather);
    
    WeatherSeason findByDayBeginLessThanEqualAndDayEndGreaterThanEqual(Long dayEnd, Long dayBegin);
    
    List<WeatherSeason> findAll();

}
