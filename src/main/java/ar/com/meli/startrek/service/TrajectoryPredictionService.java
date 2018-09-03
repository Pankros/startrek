package ar.com.meli.startrek.service;

import java.util.Map;

import ar.com.meli.startrek.entity.Planet;
import ar.com.meli.startrek.entity.Position;

public interface TrajectoryPredictionService {

    Position calculatePosition(Planet planet, Long day);

    Map<Long, Position> calculateTrajectory(Planet planet, Long days);

}
