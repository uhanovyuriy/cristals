package ch.boogaga.crystals.model;

import ch.boogaga.crystals.HasId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@Access(AccessType.FIELD)
@Getter
@Setter
@EqualsAndHashCode
@ToString
public abstract class AbstractBaseEntity implements HasId, Serializable {
    private static final long serialVersionUID = 1L;
    public static final int START_SEQ = 100000;

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    protected Integer id;

    public AbstractBaseEntity() {
    }
}
