package ch.boogaga.crystals.to;

import ch.boogaga.crystals.HasId;

import java.io.Serializable;

public abstract class BaseTo implements HasId, Serializable {
    private static final long serialVersionUID = 1L;

    protected Integer id;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
