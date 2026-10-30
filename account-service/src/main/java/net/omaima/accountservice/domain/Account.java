package net.omaima.accountservice.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity @Getter
@Setter
@AllArgsConstructor @NoArgsConstructor @Builder
@Table(name = "accounts")
public class Account {
     @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    private String currency;

    private double balance;

}
