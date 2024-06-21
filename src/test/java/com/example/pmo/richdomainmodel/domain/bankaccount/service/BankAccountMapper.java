package com.example.pmo.richdomainmodel.domain.bankaccount.service;

import com.example.pmo.richdomainmodel.domain.bankaccount.BankAccountBo;
import java.math.BigDecimal;

public class BankAccountMapper {

    protected BankAccountBo getAccount(long accountId) {
        /**
         * select AccountId, balance
         * from Account
         * where AccountId = #{AccountId}
         */
        return null;
    }


    protected BigDecimal getBalance(Long AccountId) {
        /**
         * select balance
         * from Account
         * where AccountId = #{AccountId}
         */

        return BigDecimal.valueOf(0);
    }

    protected void updateBalance(Long AccountId, BigDecimal subtract) {
        // Account 테이블에 차감된 amout를 업데이트 한다.
        /**
         * update Account
         * set balance = #{balance}
         * where AccountId = #{AccountId}
         */
    }
}
