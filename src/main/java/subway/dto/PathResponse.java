package subway.dto;

import subway.domain.Distance;
import subway.domain.fare.Fare;
import subway.domain.path.Path;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class PathResponse {

    private List<StationResponse> stations;
    private int distance;
    private BigDecimal fare;

    private PathResponse() {
    }

    private PathResponse(List<StationResponse> stations, int distance, BigDecimal fare) {
        this.stations = stations;
        this.distance = distance;
        this.fare = fare;
    }

    public static PathResponse of(Path path, Fare fare) {
        Distance distance = path.getDistance();
        List<StationResponse> stationResponses = path.getStations()
                .stream()
                .map(StationResponse::of)
                .collect(Collectors.toList());

        return new PathResponse(stationResponses, distance.getValue(), fare.getValue());
    }

    public List<StationResponse> getStations() {
        return stations;
    }

    public int getDistance() {
        return distance;
    }

    public BigDecimal getFare() {
        return fare;
    }
}
