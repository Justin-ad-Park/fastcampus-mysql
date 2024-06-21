package com.example.pmo.richdomainmodel.domain.point.bizservice;

import com.example.pmo.richdomainmodel.domain.bankaccount.service.BankAccountService;
import com.example.pmo.richdomainmodel.domain.common.exception.InsufficientBalanceException;
import com.example.pmo.richdomainmodel.domain.common.exception.InvalidAmountException;
import com.example.pmo.richdomainmodel.domain.point.service.PointService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class PointBizService {
    private BankAccountService bankAccountService;
    private PointService pointService;


    @Transactional
    public boolean transferBalanceToPoint(Long fromAccountId, Long toUserId, BigDecimal amount) throws InsufficientBalanceException, InvalidAmountException {
        bankAccountService.debit(fromAccountId, amount);
        pointService.credit(toUserId, amount);

        return true;
    }
}
