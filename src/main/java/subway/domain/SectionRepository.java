package subway.domain;

public interface SectionRepository {

    Section save(Section section);

    Sections findAllByLineId(long lineId);

    Sections findAll();

    void deleteByLineIdAndDownStationId(long lineId, long stationId);
}
