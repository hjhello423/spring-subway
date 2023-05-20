package subway.domain;

import java.util.List;
import java.util.Optional;

public interface LineRepository {

    Line insert(Line line);

    List<Line> findAll();

    Optional<Line> findById(Long id);

    void update(Line newLine);

    void deleteById(Long id);

}
