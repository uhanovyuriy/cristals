package ch.boogaga.crystals.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

@Entity
@Table(name = "levels")
@Getter @Setter @EqualsAndHashCode(callSuper = true, of = {"height", "weight"}) @ToString(callSuper = true)
public class Level extends AbstractNamedEntity {
    @Column(name = "height", nullable = false)
    @Min(value = 3)
    @Max(value = 50)
    @Positive
    private int height;

    @Column(name = "weight", nullable = false)
    @Min(value = 3)
    @Max(value = 50)
    @Positive
    private int weight;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Unit> cells;
}
