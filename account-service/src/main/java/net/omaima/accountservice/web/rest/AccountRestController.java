package net.omaima.accountservice.web.rest;

import net.omaima.accountservice.domain.Account;
import net.omaima.accountservice.domain.AccountStatus;
import net.omaima.accountservice.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")

public class AccountRestController {
    private AccountService accountService;

    public AccountRestController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        return new ResponseEntity<>(accountService.createAccount(account), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable UUID id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/customer/{customerReference}")
    public ResponseEntity<List<Account>> getAccountsByCustomer(@PathVariable String customerReference) {
        return ResponseEntity.ok(accountService.getAccountsByCustomer(customerReference));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Account> updateStatus(
            @PathVariable UUID id,
            @RequestParam AccountStatus status) {
        return ResponseEntity.ok(accountService.updateAccountStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/balance")
    public ResponseEntity<Account> updateBalance(@PathVariable UUID id, @RequestParam double amount) {
        return ResponseEntity.ok(accountService.updateBalance(id, amount));
    }
}
