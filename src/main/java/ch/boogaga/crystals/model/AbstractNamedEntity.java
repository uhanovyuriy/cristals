package ch.boogaga.crystals.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter @EqualsAndHashCode(callSuper = true) @ToString(callSuper = true)
public abstract class AbstractNamedEntity extends AbstractBaseEntity {
    @Column(name = "name", nullable = false)
    @Size(min = 3, max = 50)
    @NotBlank
    private String name;
}
