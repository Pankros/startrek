package ar.com.meli.startrek.dto;

public class WeatherSeasonRainDTO extends WeatherSeasonDTO {
    
    private Long maxPerimeterDay;

    public Long getMaxPerimeterDay() {
        return maxPerimeterDay;
    }

    public void setMaxPerimeterDay(Long maxPerimeterDay) {
        this.maxPerimeterDay = maxPerimeterDay;
    }

}
