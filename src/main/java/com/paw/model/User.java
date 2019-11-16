package com.paw.model;

import act.Act;
import act.db.jpa.JPADao;
import org.apache.bval.constraints.NotEmpty;
import org.osgl.util.S;

import javax.persistence.*;

@Entity
@Table(name = "user_model")
public class User implements UserLinked {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int userId;

    @NotEmpty
    public String email;

    @NotEmpty
    public String firstName;

    @NotEmpty
    public String lastName;

    public boolean activated;

    private String password;

    public String fullName() {
        return S.concat(lastName, " ", firstName);
    }

    public void setPassword(String password) {
        this.password = Act.crypto().passwordHash(password);
    }

    public boolean verifyPassword(char[] password) {
        return Act.crypto().verifyPassword(password, this.password);
    }

    @Override
    public int userId() {
        return userId;
    }

    @Override
    public String email() {
        return email;
    }

    public static class Dao extends JPADao<Integer, User> {

        public User findByUsername(String email) {
            return (User) findOneBy("email", email);
        }

        public User authenticate(String email, String password) {
            User user = findByUsername(email);
            return null == user ? null : user.activated ? user.verifyPassword(password.toCharArray()) ? user : null : null;
        }

        public boolean exists(String email) {
            return countBy("email", email) > 0;
        }

    }
}
