package com.example.pmo.poormvcmodel;

import java.math.BigDecimal;

public class WalletMapper {
    public WalletVo getWallet(long walletId) {
        return null;
    }

    public BigDecimal getBalance(Long walletId) {
        return BigDecimal.valueOf(0);
    }

    public void updateBalance(Long walletId, BigDecimal subtract) {
        // wallet 테이블에 차감된 amout를 업데이트 한다.
        /**
         * update wallet
         * set balance = #{balance}
         * where walletId = #{walletId}
         */
    }
}
