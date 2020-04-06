package atdd.station.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@NoArgsConstructor
public class Station extends Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 2, max = 20)
    private String name;

    @OneToMany(mappedBy = "subwayLine")
    @Where(clause = "deleted = false")
    @OrderBy("id ASC")
    private Edges edges;

    private boolean deleted = false;

    public Station(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Station(String name) {
        this.name = name;
    }

    @Builder
    public Station(Long id, String name, Edges edges) {
        this.id = id;
        this.name = name;
        this.edges = edges;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public List<SubwayLine> getSubwayLines() {
        return this.edges.getSubwayLines();
    }

    public void deleteStation() {
        this.deleted = true;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}


