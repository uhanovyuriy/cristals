package ch.boogaga.crystals.to;

import ch.boogaga.crystals.util.validation.NoHtml;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserTo extends BaseTo {
    @NotBlank
    @Size(min = 3, max = 50)
    @NoHtml
    private String name;

    @NotBlank
    @Size(min = 3, max = 50)
    @NoHtml
    private String login;

    @Email
    @NotBlank
    @Size(max = 50)
    @NoHtml
    private String email;

    @NotBlank
    @Size(min = 6, max = 50)
    private String password;

    public UserTo(Integer id, String name, String login, String email, String password) {
        super.setId(id);
        this.name = name;
        this.login = login;
        this.email = email;
        this.password = password;
    }
}
