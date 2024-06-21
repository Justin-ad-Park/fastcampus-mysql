package com.example.pmo.richdomainmodel.mall.bankaccount;

import com.example.pmo.richdomainmodel.domain.bankaccount.service.BankAccountService;
import com.example.pmo.richdomainmodel.domain.bankaccount.dto.AccountBalanceDto;
import com.example.pmo.richdomainmodel.domain.common.exception.InsufficientBalanceException;
import com.example.pmo.richdomainmodel.domain.common.exception.InvalidAmountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @PostMapping(value = "/account/debit")
    public boolean debit(AccountBalanceDto accountBalanceResDto) throws InsufficientBalanceException {
        return bankAccountService.debit(accountBalanceResDto.accountId(), accountBalanceResDto.amount());
    }

    @PostMapping(value = "/account/credit")
    public boolean credit(AccountBalanceDto accountBalanceResDto) throws InvalidAmountException {
        return bankAccountService.credit(accountBalanceResDto.accountId(), accountBalanceResDto.amount());
    }

    @PostMapping(value = "/account/transfer")
    public boolean transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) throws InsufficientBalanceException, InvalidAmountException {
        return bankAccountService.transfer(fromAccountId, toAccountId, amount);
    }



}
