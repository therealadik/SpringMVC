package com.fladx.springmvc.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.fladx.springmvc.config.Views;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.UserDetails.class)
    private Long id;

    @NotBlank
    @JsonView(Views.UserDetails.class)
    private String productName;

    @Positive
    @JsonView(Views.UserDetails.class)
    private Double amount;

    @NotBlank
    @JsonView(Views.UserDetails.class)
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
