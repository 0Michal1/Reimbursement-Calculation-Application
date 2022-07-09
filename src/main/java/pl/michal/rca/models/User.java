package pl.michal.rca.models;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "password")
public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String role;

    public User( String username, String password) {
        this.username = username;
        this.password = password;
    }
    public User( String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

}
