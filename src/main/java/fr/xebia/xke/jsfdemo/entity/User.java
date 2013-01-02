package fr.xebia.xke.jsfdemo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({
    @NamedQuery(name = "User.getUserById", query = "SELECT u FROM User u WHERE u.id = :userId")})
@Entity
public class User {

    @Id
    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private boolean administrator;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

    public String getFullName() {
        final StringBuilder sb = new StringBuilder(getFirstName());
        sb.append(' ');
        sb.append(getLastName());
        return sb.toString();
    }
}