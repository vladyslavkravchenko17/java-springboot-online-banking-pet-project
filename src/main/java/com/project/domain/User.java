package com.project.domain;

import com.project.validator.PasswordMatches;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@PasswordMatches
public class User implements Serializable{
    private Long id;
    @NotNull
    @NotEmpty(message = "First name must not be empty.")
    private String firstName;


    @NotNull
    @NotEmpty(message = "Last name must not be empty.")
    private String lastName;

    @NotNull
    @NotEmpty(message = "Email must not be empty.")
    @Email(message = "Invalid email.")
    private String email;

    @NotNull
    @Size(min = 3, max = 10, message = "Password size is 3-10")
    private String password;

    @NotNull
    @NotEmpty(message = "Matching password must not be empty.")
    private String matchingPassword;
    private String activationCode;
    private boolean active;
    private Role role;
    private List<Card> cards = new ArrayList<>();

    public void addCard(Card card){
        cards.add(card);
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public List<Card> getCards() {
        Collections.sort(cards);
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
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

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = Role.valueOf(role);
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }
}
