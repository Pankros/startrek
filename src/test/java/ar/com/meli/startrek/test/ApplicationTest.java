package ar.com.meli.startrek.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import ar.com.meli.app.Application;
import ar.com.meli.startrek.entity.Position;
import ar.com.meli.startrek.entity.WeatherDay;
import ar.com.meli.startrek.entity.WeatherEnum;
import ar.com.meli.startrek.service.impl.PlanetarySystemServiceImpl;
import ar.com.meli.startrek.service.impl.WeatherPredictionServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes=Application.class)
public class ApplicationTest {
    
    @Autowired
    private PlanetarySystemServiceImpl planetarySystemServiceImpl;
    
    @Autowired
    private WeatherPredictionServiceImpl weatherPredictionServiceImpl;

    @LocalServerPort
    private int port;
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void contextLoads() throws Exception {
        assertThat(planetarySystemServiceImpl).isNotNull();
        assertThat(weatherPredictionServiceImpl).isNotNull();
    }
    
    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
                String.class)).contains("Greetings from this planetaty system!");
    }

    @Test
    public void shouldRainning() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/planetarysystem/weather/?day=566",
                WeatherDay.class)).isEqualTo(new WeatherDay(566l, WeatherEnum.RAIN));
    }
    
    @Test
    public void shouldBeDroudht() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/planetarysystem/weather/?day=0",
                WeatherDay.class)).isEqualTo(new WeatherDay(0l, WeatherEnum.DROUDHT));
    }
    
    @Test
    public void shouldBeAlign() throws Exception {
        Position p1 = new Position(3, 4);
        Position p2 = new Position(-1, 0);
        Position p3 = new Position(9, 10);
        Method method = ApplicationTestUtils.getPrivateMethod(weatherPredictionServiceImpl.getClass(), "arePlanetsAlign", Position.class, Position.class, Position.class);
        assertThat((Boolean)method.invoke(weatherPredictionServiceImpl, p1,p2,p3)).isTrue();
    }

    @Test
    public void shouldBeAlignX() throws Exception {
        Position p1 = new Position(7, 4);
        Position p2 = new Position(7, 0);
        Position p3 = new Position(7, 10);
        Method method = ApplicationTestUtils.getPrivateMethod(weatherPredictionServiceImpl.getClass(), "arePlanetsAlign", Position.class, Position.class, Position.class);
        assertThat((Boolean)method.invoke(weatherPredictionServiceImpl, p1,p2,p3)).isTrue();
    }
    
    @Test
    public void shouldBeAlignY() throws Exception {
        Position p1 = new Position(5, 6);
        Position p2 = new Position(2, 6);
        Position p3 = new Position(-200, 6);
        Method method = ApplicationTestUtils.getPrivateMethod(weatherPredictionServiceImpl.getClass(), "arePlanetsAlign", Position.class, Position.class, Position.class);
        assertThat((Boolean)method.invoke(weatherPredictionServiceImpl, p1,p2,p3)).isTrue();
    }
    
    @Test
    public void shouldBeAlignLittleMargin() throws Exception {
        Position p1 = new Position(-449, -219);
        Position p2 = new Position(-432.21, 1095.98);
        Position p3 = new Position(-425, -1956);
        Method method = ApplicationTestUtils.getPrivateMethod(weatherPredictionServiceImpl.getClass(), "arePlanetsAlign", Position.class, Position.class, Position.class);
        assertThat((Boolean)method.invoke(weatherPredictionServiceImpl, p1,p2,p3)).isTrue();
    }
    
    @Test
    public void shouldNotBeAlignLittleMargin() throws Exception {
        Position p1 = new Position(-522.64, -220.64);
        Position p2 = new Position(-432.21, 1095.98);
        Position p3 = new Position(-425, -1956);
        Method method = ApplicationTestUtils.getPrivateMethod(weatherPredictionServiceImpl.getClass(), "arePlanetsAlign", Position.class, Position.class, Position.class);
        assertThat((Boolean)method.invoke(weatherPredictionServiceImpl, p1,p2,p3)).isFalse();
    }
    
    @Test
    public void shouldNotBeAlignLittleMargin2() throws Exception {
        Position p1 = new Position(-1219.63, -189.4);
        Position p2 = new Position(-432.21, 1095.98);
        Position p3 = new Position(-425, -1956);
        Method method = ApplicationTestUtils.getPrivateMethod(weatherPredictionServiceImpl.getClass(), "arePlanetsAlign", Position.class, Position.class, Position.class);
        assertThat((Boolean)method.invoke(weatherPredictionServiceImpl, p1,p2,p3)).isFalse();
    }

}
