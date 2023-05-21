package subway.domain.path;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.domain.Distance;
import subway.domain.Sections;
import subway.domain.Station;
import subway.exception.ErrorType;
import subway.exception.ServiceException;

import java.util.List;
import java.util.Objects;

public class ShortestPathFinder {

    private WeightedMultigraph<Station, DefaultWeightedEdge> graph;
    private DijkstraShortestPath<Station, DefaultWeightedEdge> dijkstraShortestPath;

    private ShortestPathFinder() {
        this.graph = new WeightedMultigraph(DefaultWeightedEdge.class);
        this.dijkstraShortestPath = new DijkstraShortestPath(graph);
    }

    public static ShortestPathFinder of(List<Sections> sections) {
        ShortestPathFinder pathChecker = new ShortestPathFinder();
        pathChecker.initGraph(sections);

        return pathChecker;
    }

    public Path getPath(Station source, Station target) {
        validatePath(source, target);
        GraphPath<Station, DefaultWeightedEdge> path = dijkstraShortestPath.getPath(source, target);

        if (Objects.isNull(path)) {
            throw new ServiceException(ErrorType.NOT_FOUND_PATH);
        }

        return Path.of(path.getVertexList(), Distance.of((int) path.getWeight()));
    }

    private void initGraph(List<Sections> sections) {
        sections.forEach(this::initVertex);
        sections.forEach(this::initEdgeWeight);
    }

    private void initVertex(Sections sections) {
        List<Station> stations = sections.getAllStation();
        stations.forEach(graph::addVertex);
    }

    private void initEdgeWeight(Sections sections) {
        sections.getValue()
                .stream()
                .forEach(it -> graph.setEdgeWeight(
                        graph.addEdge(it.getUpStation(), it.getDownStation()), it.getDistance()));
    }

    private void validatePath(Station source, Station target) {
        if (Objects.equals(source, target)) {
            throw new ServiceException(ErrorType.SAME_SOURCE_DESTINATION_STATION);
        }
    }

}
