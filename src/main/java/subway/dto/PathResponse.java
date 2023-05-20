package subway.dto;

import subway.domain.Distance;
import subway.domain.path.Path;

import java.util.List;
import java.util.stream.Collectors;

public class PathResponse {

    private List<StationResponse> stations;
    private int distance;

    private PathResponse() {
    }

    private PathResponse(List<StationResponse> stations, int distance) {
        this.stations = stations;
        this.distance = distance;
    }

    public static PathResponse of(Path path) {
        Distance distance = path.getDistance();
        List<StationResponse> stationResponses = path.getStations()
                .stream()
                .map(StationResponse::of)
                .collect(Collectors.toList());

        return new PathResponse(stationResponses, distance.getValue());
    }

    public List<StationResponse> getStations() {
        return stations;
    }

    public int getDistance() {
        return distance;
    }
}
