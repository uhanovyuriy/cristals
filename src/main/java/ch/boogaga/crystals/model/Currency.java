package ch.boogaga.crystals.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "currency")
@Getter @Setter @EqualsAndHashCode(callSuper = true) @ToString(callSuper = true)
public class Currency extends AbstractNamedEntity {
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private CurrencyType type;
}
