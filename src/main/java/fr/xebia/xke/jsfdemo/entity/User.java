package fr.xebia.xke.jsfdemo.entity;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

@NamedQueries({
    @NamedQuery(name = "User.getUserById", query = "SELECT u FROM User u WHERE u.id = :userId"),
    @NamedQuery(name = "User.getUserByEmail", query = "SELECT u FROM User u WHERE u.email = :email")
})
@Entity
public class User implements Serializable {

    @Id
    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private boolean administrator;

    @Transient
    private String fullName;

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
        if (fullName == null) {
            fullName = Joiner.on(' ').useForNull("").join(firstName, lastName);
        }
        return fullName;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("Firstname", firstName).add("LastName", lastName).add("email", email).toString();
    }
}
