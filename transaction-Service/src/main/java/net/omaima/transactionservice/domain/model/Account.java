package net.omaima.transactionservice.domain.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    private UUID id;
    private String customerReference;
    private String status; // "ACTIVE" ou "BLOCKED"
    private String currency;
    private double balance;
}
