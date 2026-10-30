package net.omaima.transactionservice.service;

import net.omaima.transactionservice.domain.entities.Transaction;
import net.omaima.transactionservice.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(UUID id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction non trouvée avec l'ID : " + id));
    }

    public List<Transaction> getTransactionsByAccountId(UUID accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    public Transaction saveTransaction(Transaction transaction) {
        if (transaction.getCreatedAt() == null) {
            transaction.setCreatedAt(Instant.now());
        }
        return transactionRepository.save(transaction);
    }
}