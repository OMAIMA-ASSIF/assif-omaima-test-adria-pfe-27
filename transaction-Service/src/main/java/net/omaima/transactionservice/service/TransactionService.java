package net.omaima.transactionservice.service;

import net.omaima.transactionservice.domain.entities.Transaction;
import net.omaima.transactionservice.domain.model.Account;
import net.omaima.transactionservice.feign.AccountRestClient;
import net.omaima.transactionservice.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRestClient accountRestClient;

    public TransactionService(TransactionRepository transactionRepository, AccountRestClient accountRestClient) {
        this.transactionRepository = transactionRepository;
        this.accountRestClient = accountRestClient;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(UUID id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction non trouvée"));
    }

    public List<Transaction> getTransactionsByAccountId(UUID accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    @Transactional
    public Transaction saveTransaction(Transaction transaction) {
        // Règle 1 : Montant strictement positif
        if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant doit être strictement positif.");
        }

        // Règle 2 : Le compte doit exister et être actif (Appel Feign)
        Account account = accountRestClient.getAccountById(transaction.getAccountId());
        if (account == null || !"ACTIVE".equals(account.getStatus())) {
            throw new IllegalStateException("Le compte n'existe pas ou n'est pas actif (Statut: " +
                    (account != null ? account.getStatus() : "INCONNU") + ").");
        }

        // Règle 3 : La devise de la transaction doit correspondre à celle du compte
        if (!account.getCurrency().equals(transaction.getCurrency())) {
            throw new IllegalArgumentException("La devise de la transaction (" + transaction.getCurrency() +
                    ") ne correspond pas à celle du compte (" + account.getCurrency() + ").");
        }

        // Règle 4 : Date de création
        if (transaction.getCreatedAt() == null) {
            transaction.setCreatedAt(Instant.now());
        }

        // Sauvegarde de la transaction
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Règle 5 : Mise à jour du solde après enregistrement (Appel Feign - Débit)
        accountRestClient.updateBalance(transaction.getAccountId(), transaction.getAmount().doubleValue() * -1);

        // Enrichissement avec les détails du compte (optionnel pour la réponse)
        savedTransaction.setAccountDetails(account);

        return savedTransaction;
    }
}