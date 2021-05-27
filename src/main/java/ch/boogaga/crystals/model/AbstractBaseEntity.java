package ch.boogaga.crystals.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;
import java.io.Serializable;

@Getter @Setter @EqualsAndHashCode @ToString
public abstract class AbstractBaseEntity implements Serializable {
    @Id
    private int id;
}
