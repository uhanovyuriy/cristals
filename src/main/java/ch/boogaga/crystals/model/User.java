package ch.boogaga.crystals.model;

import ch.boogaga.crystals.util.validation.NoHtml;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "login", name = "users_unique_login_idx"),
        @UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"login", "password", "email"})
@ToString(callSuper = true, exclude = {"password"})
public class User extends AbstractNamedEntity {
    @Column(name = "login", nullable = false)
    @Size(min = 3, max = 50)
    @NotBlank
    @NoHtml
    private String login;

    @Column(name = "password", nullable = false)
    @Size(min = 6, max = 200)
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "email", nullable = false)
    @Size(min = 3, max = 50)
    @NotBlank
    @Email
    @NoHtml
    private String email;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "created", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    private LocalDateTime created = LocalDateTime.now();

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "last_login_time")
    private LocalDateTime lastLoginTime;

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    private boolean enabled = true;

    @Column(name = "score", nullable = false, columnDefinition = "int default 0")
    @Range(max = Integer.MAX_VALUE)
    private int score;

    public User() {
    }

    public User(Integer id, String name, String login, String password, String email) {
        super.setId(id);
        super.setName(name);
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public User(int id, String name, String login, String password, String email, LocalDateTime created,
                LocalDateTime lastLoginTime, boolean enabled, int score) {
        this.setId(id);
        this.setName(name);
        this.login = login;
        this.password = password;
        this.email = email;
        this.created = created;
        this.lastLoginTime = lastLoginTime;
        this.enabled = enabled;
        this.score = score;
    }

    public User(User user) {
        this.setId(user.id);
        this.setName(user.name);
        this.login = user.login;
        this.password = user.password;
        this.email = user.email;
        this.created = user.created;
        this.lastLoginTime = user.lastLoginTime;
        this.enabled = user.enabled;
        this.score = user.score;
    }
}
