package atdd.station.dto.station;

import atdd.station.domain.Station;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StationCreateRequestDto {
    private Long id;
    private String name;

    @Builder
    public StationCreateRequestDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Station toEntity() {
        return Station.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }

    public static StationCreateRequestDto toDtoEntity(Long id, String name) {
        return StationCreateRequestDto.builder()
                .id(id)
                .name(name)
                .build();
    }
}
