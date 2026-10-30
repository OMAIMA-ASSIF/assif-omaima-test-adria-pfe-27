package net.omaima.transactionservice.web.graphql;

import lombok.RequiredArgsConstructor;
import net.omaima.transactionservice.domain.entities.Transaction;
import net.omaima.transactionservice.service.TransactionService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class TransactionGraphQLController {

    private final TransactionService transactionService;

    @QueryMapping
    public List<Transaction> allTransactions() {
        return transactionService.getAllTransactions();
    }

    @QueryMapping
    public Transaction transactionById(@Argument UUID id) {
        return transactionService.getTransactionById(id);
    }

    @QueryMapping
    public List<Transaction> transactionsByAccountId(@Argument UUID accountId) {
        return transactionService.getTransactionsByAccountId(accountId);
    }

    @MutationMapping
    public Transaction createTransaction(
            @Argument UUID accountId,
            @Argument BigDecimal amount,
            @Argument String currency,
            @Argument String merchantCategory,
            @Argument String country) {

        Transaction transaction = Transaction.builder()
                .accountId(accountId)
                .amount(amount)
                .currency(currency)
                .merchantCategory(merchantCategory)
                .country(country)
                .build();

        return transactionService.saveTransaction(transaction);
    }
}