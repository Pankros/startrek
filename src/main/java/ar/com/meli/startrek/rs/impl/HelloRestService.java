package ar.com.meli.startrek.rs.impl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRestService {

    @RequestMapping("/")
    public String index() {
        return "Greetings from this planetaty system!<br>"
                + "Si quieres saber el clima para un dia en particular usa el siguiente recurso<br>"
                + "<br>"
                + "GET /planetarysystem/weather/day/{day}<br>"
                + "<br>"
                + "Siendo {day} el dia a consultar<br>"
                + "Puedes consultar cualquier dia desde el dia 0<br>"
                + "<br>"
                + "Si quieres saber el clima por periodos de lluvia(RAIN) sequia(DROUDHT) optimos(OPTIMUM) normales(NORMAL) o todos(ALL)<br> "
                + "usa el siguiente recurso.<br>"
                + "<br>"
                + "GET /planetarysystem/weatherseason/weather/{weather}/periods/{periods}<br>"
                + "<br>"
                + "Siendo {weather} la temporada RAIN DROUDHT OPTIMUM NORMAL o ALL<br>"
                + "y {periods} la cantidad de periodos que deseas saber<br>"
                + "<br>"
                + "Si buscas destruir nuestra base de predicciones y generarla nuevamente desde el dia 0 usa el siguiente recurso<br>"
                + "<br>"
                + "POST /planetarysystem/weather/prediction/periods/{periods}<br>"
                + "<br>"
                + "Siendo {periods} los periodos que se van a generar.<br>"
                + "Este recurso retorna el sentido de la vida<br>"
                + "<br>"
                + "<b>Nosotros consideramos un PERIODO al tiempo que tardan los planetas en alinearse como estaban en el momento 0.</b><br>"
                + "Este tiempo coincide extrañamente con el año en el planeta Ferengi";
    }

}