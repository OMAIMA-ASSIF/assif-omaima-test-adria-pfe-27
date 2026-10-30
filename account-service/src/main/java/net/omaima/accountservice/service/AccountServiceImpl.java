package net.omaima.accountservice.service.impl;

import lombok.RequiredArgsConstructor;
import net.omaima.accountservice.domain.Account;
import net.omaima.accountservice.domain.AccountStatus;
import net.omaima.accountservice.repository.AccountRepository;
import net.omaima.accountservice.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public Account createAccount(Account account) {
        if (account.getStatus() == null) {
            account.setStatus(AccountStatus.ACTIVE);
        }
        return accountRepository.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public Account getAccountById(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compte non trouvé avec l'ID : " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> getAccountsByCustomer(String customerReference) {
        return accountRepository.findByCustomerReference(customerReference);
    }

    @Override
    @Transactional
    public Account updateAccountStatus(UUID id, AccountStatus newStatus) {
        Account account = getAccountById(id); // Réutilise la méthode qui vérifie l'existence
        account.setStatus(newStatus);
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public Account updateBalance(UUID id, double amount) {
        Account account = getAccountById(id);
        // Ajoute le montant au solde existant (peut être négatif pour un débit)
        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public void deleteAccount(UUID id) {
        if (!accountRepository.existsById(id)) {
            throw new RuntimeException("Impossible de supprimer : Compte non trouvé avec l'ID : " + id);
        }
        accountRepository.deleteById(id);
    }
}