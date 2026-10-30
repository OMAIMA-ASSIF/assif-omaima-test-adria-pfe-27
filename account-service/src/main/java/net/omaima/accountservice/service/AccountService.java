package net.omaima.accountservice.service;

import net.omaima.accountservice.domain.Account;
import net.omaima.accountservice.domain.AccountStatus;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    Account createAccount(Account account);
    Account getAccountById(UUID id);
    List<Account> getAllAccounts();
    List<Account> getAccountsByCustomer(String customerReference);
    Account updateAccountStatus(UUID id, AccountStatus newStatus);
    Account updateBalance(UUID id, double amount);
    void deleteAccount(UUID id);
}
