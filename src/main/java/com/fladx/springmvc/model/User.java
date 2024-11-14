package com.fladx.springmvc.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.fladx.springmvc.config.Views;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @JsonView(Views.UserSummary.class)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @JsonView(Views.UserSummary.class)
    private String name;

    @NotBlank
    @Email(message = "Email неккоректен")
    @JsonView(Views.UserSummary.class)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonView(Views.UserDetails.class)
    private List<Order> orders;

    public User(Long l, String johnDoe, String mail) {
        this.id = l;
        this.name = johnDoe;
        this.email = mail;
    }

    public User() {

    }
}
