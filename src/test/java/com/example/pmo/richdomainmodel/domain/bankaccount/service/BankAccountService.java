package com.example.pmo.richdomainmodel.domain.bankaccount.service;

import com.example.pmo.richdomainmodel.domain.bankaccount.BankAccountBo;
import com.example.pmo.richdomainmodel.domain.bankaccount.BankAccountManager;
import com.example.pmo.richdomainmodel.domain.common.exception.InsufficientBalanceException;
import com.example.pmo.richdomainmodel.domain.common.exception.InvalidAmountException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class BankAccountService {
    //생성자 또는 프레임워크
    private BankAccountManager bankAccountManager;
    private BankAccountMapper bankAccountMapper;

    public BankAccountService() {
        bankAccountManager = new BankAccountManager();
        bankAccountMapper = new BankAccountMapper();
    }

    public BankAccountBo getAccount(Long AccountId) {
        BankAccountBo bankAccountBo = bankAccountManager.getAccount(AccountId);

        return bankAccountBo;
    }

    /**
     * 차감
     *  본 기능은 계좌 도메인 본연의 기능으로 타 서비스가 직접 사용하면 안되며, 반드시 Controller의 API를 호출해야 함
     * @param AccountId
     * @param amount
     * @return
     * @throws InsufficientBalanceException
     */
    @Transactional
    public boolean debit(Long accountId, BigDecimal amount) throws InsufficientBalanceException {
        BankAccountBo bankAccountBo = bankAccountManager.getAccount(accountId);
        bankAccountBo.debit(amount);

        updateBalance(bankAccountBo);

        return true;
    }

    /**
     * 입금
     *  본 기능은 계좌 도메인 본연의 기능으로 타 서비스가 직접 사용하면 안되며, 반드시 Controller의 API를 호출해야 함
      */

    @Transactional
    public boolean credit(Long accountId, BigDecimal amount) throws InvalidAmountException {
        BankAccountBo bankAccountBo = bankAccountManager.getAccount(accountId);
        bankAccountBo.credit(amount);

        updateBalance(bankAccountBo);

        return true;
    }

    @Transactional
    public boolean transfer(Long fromAccountId, long toAccountId, BigDecimal amount) throws InsufficientBalanceException, InvalidAmountException {
        debit(fromAccountId, amount);
        credit(toAccountId, amount);

        return true;
    }

    /**
     * 잔액 업데이트는 도메인 내부 메서드로
     * 외부에서는 debit, credit, transfer 로만 잔액을 수정할 수 있다.
     * @param bankAccountManager
     */
    private void updateBalance(BankAccountBo bankAccountBo) {
        bankAccountMapper.updateBalance(bankAccountBo.getAccountId(), bankAccountBo.getBalance());
    }


}
