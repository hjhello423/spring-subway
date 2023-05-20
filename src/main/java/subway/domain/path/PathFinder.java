package subway.domain.path;

import subway.domain.Station;

import java.util.List;

public interface PathFinder {

    List<Station> getPath(Station source, Station destination);

}
