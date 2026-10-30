package net.omaima.transactionservice;

import net.omaima.transactionservice.domain.entities.Transaction;
import net.omaima.transactionservice.service.TransactionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class TransactionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner run(TransactionService transactionService) {
        return args -> {
            UUID omaimaAccountId = UUID.fromString("f25800b4-0df7-47b2-b956-823401c05c6a");

            List<Transaction> testTransactions = List.of(
                    Transaction.builder()
                            .accountId(omaimaAccountId)
                            .amount(new BigDecimal("150.00"))
                            .currency("MAD")
                            .merchantCategory("RETAIL")
                            .country("MA")
                            .createdAt(Instant.now())
                            .build(),
                    Transaction.builder()
                            .accountId(omaimaAccountId)
                            .amount(new BigDecimal("45.50"))
                            .currency("MAD")
                            .merchantCategory("FOOD")
                            .country("MA")
                            .createdAt(Instant.now())
                            .build()
            );

            testTransactions.forEach(transaction -> {
                Transaction saved = transactionService.saveTransaction(transaction);
                System.out.println("Transaction sauvegardée : ID=" + saved.getId() +
                        ", Montant=" + saved.getAmount() + " " + saved.getCurrency());
            });

        };
    }
}
