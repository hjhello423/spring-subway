package subway.application;

import org.springframework.stereotype.Service;
import subway.domain.Line;
import subway.domain.LineRepository;
import subway.domain.SectionRepository;
import subway.domain.Sections;
import subway.domain.Station;
import subway.domain.StationRepository;
import subway.domain.path.Path;
import subway.domain.path.ShortestPathFinder;
import subway.dto.PathResponse;
import subway.exception.ErrorType;
import subway.exception.ServiceException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PathService {

    private final StationRepository stationRepository;
    private final LineRepository lineRepository;
    private final SectionRepository sectionRepository;

    public PathService(StationRepository stationRepository, LineRepository lineRepository,
                       SectionRepository sectionRepository) {
        this.stationRepository = stationRepository;
        this.lineRepository = lineRepository;
        this.sectionRepository = sectionRepository;
    }

    public PathResponse findPath(Long sourceId, Long destinationId) {
        Station sourceStation = findStationById(sourceId);
        Station destinationStation = findStationById(destinationId);
        ShortestPathFinder pathFinder = ShortestPathFinder.of(findAllSections());
        Path path = pathFinder.getPath(sourceStation, destinationStation);

        return PathResponse.of(path);
    }

    private Sections findAllSection() {
        return sectionRepository.findAll();
    }

    private Station findStationById(long stationId) {
        return stationRepository.findById(stationId)
                .orElseThrow(() -> new ServiceException(ErrorType.NOT_FOUND_STATION));
    }

    private List<Sections> findAllSections() {
        List<Line> lines = lineRepository.findAll();
        if (lines.isEmpty()) {
            throw new ServiceException(ErrorType.NOT_REGISTERED_LINE);
        }

        List<Sections> collect = lines.stream()
                .map(it -> findAllSection(it))
                .collect(Collectors.toList());

        return collect;
    }

    private Sections findAllSection(Line line) {
        Long id = line.getId();
        String name = line.getName();
        return sectionRepository.findAllByLineId(line.getId());
    }

}
