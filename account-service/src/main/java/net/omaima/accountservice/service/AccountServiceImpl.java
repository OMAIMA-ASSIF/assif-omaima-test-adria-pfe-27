package net.omaima.accountservice.service;

import jakarta.transaction.Transactional;
import net.omaima.accountservice.domain.Account;
import net.omaima.accountservice.domain.AccountStatus;
import net.omaima.accountservice.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service

public class AccountServiceImpl implements AccountService{
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public Account createAccount(Account account) {
        if (account.getStatus() == null) {
            account.setStatus(AccountStatus.ACTIVE);
        }
        return accountRepository.save(account);
    }

    @Override
    public Account getAccountById(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public List<Account> getAccountsByCustomer(String customerReference) {
        return accountRepository.findByCustomerReference(customerReference);
    }

    @Override
    @Transactional
    public Account updateAccountStatus(UUID id, AccountStatus newStatus) {
        Account account = getAccountById(id);
        account.setStatus(newStatus);
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public Account updateBalance(UUID id, double amount) {
        Account account = getAccountById(id);
        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public void deleteAccount(UUID id) {
        if (!accountRepository.existsById(id)) {
            throw new RuntimeException("Account not found with id: " + id);
        }
        accountRepository.deleteById(id);
    }
}
