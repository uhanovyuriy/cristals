package ch.boogaga.crystals.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Currency {
    private CurrencyType type;
    private int count;
}
