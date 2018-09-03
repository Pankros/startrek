package ar.com.meli.startrek.dto;

import java.util.List;

public class WeatherSeasonResponseDTO {
    
    private int total;
    
    private List<? extends WeatherSeasonDTO> seasons;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<? extends WeatherSeasonDTO> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<? extends WeatherSeasonDTO> seasons) {
        this.seasons = seasons;
    }
    
}
