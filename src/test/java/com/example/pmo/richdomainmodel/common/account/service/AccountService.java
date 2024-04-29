package com.example.pmo.richdomainmodel.common.account.service;

import com.example.pmo.richdomainmodel.common.account.exception.InsufficientBalanceException;
import com.example.pmo.richdomainmodel.common.account.exception.InvalidAmountException;
import com.example.pmo.richdomainmodel.domain.account.Account;
import com.example.pmo.richdomainmodel.domain.account.AccountMapper;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public class AccountService {
    //생성자 또는 프레임워크로 주입
    private AccountMapper accountMapper;

    public Account getAccount(Long AccountId) {
        Account account = accountMapper.getAccount(AccountId);

        return account;
    }

    public BigDecimal getBalance(Long AccountId) {
        return accountMapper.getBalance(AccountId);
    }

    @Transactional
    public boolean debit(Long AccountId, BigDecimal amount) throws InsufficientBalanceException {
        Account account = accountMapper.getAccount(AccountId);
        account.debit(amount);

        updateBalance(account);

        return true;
    }

    @Transactional
    public boolean credit(Long AccountId, BigDecimal amount) throws InvalidAmountException {
        Account account = accountMapper.getAccount(AccountId);
        account.credit(amount);

        updateBalance(account);

        return true;
    }

    private void updateBalance(Account account) {
        accountMapper.updateBalance(account.getAccountId(), account.balance());
    }

    @Transactional
    public void transfer(Long fromAccountId, long toAccountId, BigDecimal amount) throws InsufficientBalanceException, InvalidAmountException {
        debit(fromAccountId, amount);
        credit(toAccountId, amount);
    }

}
