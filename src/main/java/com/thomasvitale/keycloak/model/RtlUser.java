package com.thomasvitale.keycloak.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RtlUser {

    private String username;
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private boolean emailVerified;
    private String role;

    public RtlUser(String name, String sid, String mail, boolean en) {
        this.enabled = en;
        this.email = mail;
        this.id = sid;
        this.username = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username=" + username +
                ", email=" + email +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", enabled=" + enabled +
                ", emailVerified=" + emailVerified +
                "}";
    }
}
