package net.omaima.accountservice.web.graphql;

import net.omaima.accountservice.domain.Account;
import net.omaima.accountservice.domain.AccountStatus;
import net.omaima.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class AccountGraphController {
    @Autowired
    AccountService accountService;

    @QueryMapping
    public Account accountById(@Argument UUID id) {
        return accountService.getAccountById(id);
    }

    @QueryMapping
    public List<Account> allAccounts() {
        return accountService.getAllAccounts();
    }

    @QueryMapping
    public List<Account> accountsByCustomer(@Argument String customerReference) {
        return accountService.getAccountsByCustomer(customerReference);
    }

    @MutationMapping
    public Account createAccount(
            @Argument String customerReference,
            @Argument String currency,
            @Argument Double balance) {

        Account account = Account.builder()
                .customerReference(customerReference)
                .currency(currency)
                .balance(balance != null ? balance : 0.0)
                .status(AccountStatus.ACTIVE)
                .build();

        return accountService.createAccount(account);
    }

    @MutationMapping
    public Account updateAccountStatus(@Argument UUID id, @Argument AccountStatus status) {
        return accountService.updateAccountStatus(id, status);
    }

    @MutationMapping
    public boolean deleteAccount(@Argument UUID id) {
        accountService.deleteAccount(id);
        return true;
    }

}
