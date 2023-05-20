package subway.domain;

import java.util.List;
import java.util.Optional;

public interface StationRepository {

    Station insert(Station station);

    List<Station> findAll();

    Optional<Station> findById(Long id);

    void update(Station newStation);

    void deleteById(Long id);
}
