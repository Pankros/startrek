package ar.com.meli.startrek.rs.validation;

import org.springframework.util.Assert;

import ar.com.meli.startrek.entity.WeatherEnum;

public class ValidationService {

    public static void getWeatherByDay(Long day) throws IllegalArgumentException {
        Assert.notNull(day, ErrorsEnum.NO_DAY.getValue());
        Assert.isTrue(day >= 0, ErrorsEnum.NEGATIVE_DAYS.getValue());
    }

    public static void generatePredictionWeatherForYears(Long years) throws IllegalArgumentException {
        Assert.notNull(years, ErrorsEnum.NO_DAY.getValue());
        Assert.isTrue(years >= 0, ErrorsEnum.NEGATIVE_DAYS.getValue());
    }
    
    public static void getDaysByWeather(String weather) throws IllegalArgumentException {
        Assert.notNull(weather, ErrorsEnum.NO_WEATHER.getValue());
        try {
            Enum.valueOf(WeatherEnum.class, weather);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ErrorsEnum.NO_WEATHER.getValue());
        }
    }
}
