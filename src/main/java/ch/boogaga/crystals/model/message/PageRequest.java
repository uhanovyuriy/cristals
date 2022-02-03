package ch.boogaga.crystals.model.message;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest implements Serializable {
    private int userId;
    private int startPage;
    private int endPage;
    private String localeId;
}
