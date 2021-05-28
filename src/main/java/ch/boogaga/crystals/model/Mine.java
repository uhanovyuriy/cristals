package ch.boogaga.crystals.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "mine")
@Getter @Setter @EqualsAndHashCode(callSuper = true) @ToString(callSuper = true)
public class Mine extends AbstractNamedEntity {
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private MineType type;
}
