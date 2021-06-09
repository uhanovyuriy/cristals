package ch.boogaga.crystals.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "login", name = "users_unique_login_idx"),
        @UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
@Getter @Setter @EqualsAndHashCode(callSuper = true, of = {"login", "password", "email"})
@ToString(callSuper = true, exclude = {"password"})
public class User extends AbstractNamedEntity {
    @Column(name = "login", nullable = false)
    @Size(min = 3, max = 50)
    @NotBlank
    private String login;

    @Column(name = "password", nullable = false)
    @Size(min = 3, max = 50)
    @NotBlank
    private String password;

    @Column(name = "email", nullable = false)
    @Size(min = 3, max = 50)
    @NotBlank
    @Email
    private String email;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "last_login_time", nullable = false)
    private LocalDateTime lastLoginTime;

    @Column(name = "enabled", nullable = false)
    @NotNull
    private boolean enabled;

    @Column(name = "melee", nullable = false)
    @NotNull
    private int melee;

    @Column(name = "melanges", nullable = false)
    @NotNull
    private int melanges;

    @Column(name = "solitaires", nullable = false)
    @NotNull
    private int solitaires;

    @Column(name = "score", nullable = false)
    @NotNull
    private int score;

    @Column(name = "record", nullable = false)
    @NotNull
    private int record;

    @Column(name = "free_swaps", nullable = false)
    @NotNull
    private int freeSwaps;

    @Column(name = "undo", nullable = false)
    @NotNull
    private int undo;

    public User() {
    }

    public User(int id, String name, String login, String password, String email, LocalDateTime created,
                LocalDateTime lastLoginTime, boolean enabled, int melee, int melanges, int solitaires, int score,
                int record, int freeSwaps, int undo) {
        this.setId(id);
        this.setName(name);
        this.login = login;
        this.password = password;
        this.email = email;
        this.created = created;
        this.lastLoginTime = lastLoginTime;
        this.enabled = enabled;
        this.melee = melee;
        this.melanges = melanges;
        this.solitaires = solitaires;
        this.score = score;
        this.record = record;
        this.freeSwaps = freeSwaps;
        this.undo = undo;
    }
}
