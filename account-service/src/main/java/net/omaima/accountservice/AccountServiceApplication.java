package net.omaima.accountservice;

import net.omaima.accountservice.domain.Account;
import net.omaima.accountservice.domain.AccountStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner run() {
        return args -> {
            List<Account> testAccounts = List.of(
                    Account.builder()
                            .customerReference("CUST-OMAIMA-001")
                            .status(AccountStatus.ACTIVE)
                            .currency("MAD")
                            .balance(15000.50)
                            .build(),
                    Account.builder()
                            .customerReference("CUST-SOUMIA-002")
                            .status(AccountStatus.ACTIVE)
                            .currency("EUR")
                            .balance(2500.00)
                            .build(),
                    Account.builder()
                            .customerReference("CUST-FATIMA-003")
                            .status(AccountStatus.BLOCKED)
                            .currency("MAD")
                            .balance(0.0)
                            .build()
            );
        };
    }
}
