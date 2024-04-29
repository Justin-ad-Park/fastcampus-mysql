package com.example.pmo.poormvcmodel;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public class WalletService {
    private WalletMapper walletMapper;

    public WalletVo getWallet(long walletId) {
        WalletVo walletVo = walletMapper.getWallet(walletId);

        return walletVo;
    }

    public BigDecimal getBalance(Long walletId) {
        return walletMapper.getBalance(walletId);
    }

    @Transactional
    // 밸런스 차감
    public void debit(Long walletId, BigDecimal amount) throws NoSufficientBalanceException {
        WalletVo walletVo = walletMapper.getWallet(walletId);

        BigDecimal balance = walletVo.getBalance();

        if(balance.compareTo(amount) < 0) {
            throw new NoSufficientBalanceException("잔고 부족");
        }

        walletMapper.updateBalance(walletId, balance.subtract(amount));
    }

    @Transactional
    public void credit(Long walletId, BigDecimal amount) {
        WalletVo walletVo = walletMapper.getWallet(walletId);

        BigDecimal balance = walletVo.getBalance();

        walletMapper.updateBalance(walletId, balance.add(amount));
    }

    @Transactional
    public void transfer(Long fromWalletId, Long toWalletId, BigDecimal amount) throws NoSufficientBalanceException {
        debit(fromWalletId, amount);
        credit(toWalletId, amount);
    }
}
