package ch.boogaga.crystals.model.persist;

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
public abstract class AbstractBaseEntity implements HasId<Integer>, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    public AbstractBaseEntity() {
    }
}
