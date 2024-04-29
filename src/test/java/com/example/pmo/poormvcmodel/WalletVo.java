package com.example.pmo.poormvcmodel;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WalletVo {
    private long id;
    private long createTime;
    private BigDecimal balance;

}
