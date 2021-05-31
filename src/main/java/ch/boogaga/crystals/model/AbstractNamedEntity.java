package ch.boogaga.crystals.model;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@TypeDefs({
        @TypeDef(
                name = "int-array",
                typeClass = IntArrayType.class
        )
})
@MappedSuperclass
@Access(AccessType.FIELD)
@Getter @Setter @EqualsAndHashCode(callSuper = true) @ToString(callSuper = true)
public abstract class AbstractNamedEntity extends AbstractBaseEntity {
    @Column(name = "name", nullable = false)
    @Size(min = 3, max = 50)
    @NotBlank
    protected String name;
}
