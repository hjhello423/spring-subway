package subway.domain.path;

import subway.domain.Distance;
import subway.domain.Station;

import java.util.List;

public class Path {

    public static final Path NONE = new Path(List.of(), Distance.ZERO);

    private List<Station> stations;
    private Distance distance;

    private Path(List<Station> stations, Distance distance) {
        this.stations = stations;
        this.distance = distance;
    }

    public static Path of(List<Station> stations, Distance distance) {
        return new Path(stations, distance);
    }

    public List<Station> getStations() {
        return stations;
    }

    public Distance getDistance() {
        return distance;
    }
}
