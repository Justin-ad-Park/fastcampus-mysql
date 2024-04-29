package com.example.pmo.richdomainmodel.domain.account;

import java.math.BigDecimal;

public class AccountMapper {

    public Account getAccount(long accountId) {
        /**
         * select AccountId, balance
         * from Account
         * where AccountId = #{AccountId}
         */
        return null;
    }

    public BigDecimal getBalance(Long AccountId) {
        /**
         * select balance
         * from Account
         * where AccountId = #{AccountId}
         */

        return BigDecimal.valueOf(0);
    }

    public void updateBalance(Long AccountId, BigDecimal subtract) {
        // Account 테이블에 차감된 amout를 업데이트 한다.
        /**
         * update Account
         * set balance = #{balance}
         * where AccountId = #{AccountId}
         */
    }
}
