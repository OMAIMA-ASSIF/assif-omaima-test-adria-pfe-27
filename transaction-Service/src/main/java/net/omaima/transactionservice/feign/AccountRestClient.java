package net.omaima.transactionservice.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import net.omaima.transactionservice.domain.model.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "account-service")
public interface AccountRestClient {

    @GetMapping("/{id}")
    @CircuitBreaker(name = "accountService", fallbackMethod = "getDefaultAccount")
    Account getAccountById(@PathVariable UUID id);

    @PatchMapping("/{id}/balance")
    @CircuitBreaker(name = "accountService", fallbackMethod = "getDefaultAccount")
    Account updateBalance(@PathVariable UUID id, @RequestParam double amount);

    // Fallback pour Resilience4j
    default Account getDefaultAccount(UUID id, Throwable e) {
        return Account.builder()
                .id(id)
                .customerReference("Not available")
                .status("BLOCKED")
                .currency("N/A")
                .balance(0.0)
                .build();
    }

    default Account getDefaultAccount(UUID id, double amount, Throwable e) {
        return Account.builder()
                .id(id)
                .customerReference("Not available")
                .status("BLOCKED")
                .currency("N/A")
                .balance(0.0)
                .build();
    }
}