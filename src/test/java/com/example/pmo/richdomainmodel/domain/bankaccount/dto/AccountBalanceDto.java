package com.example.pmo.richdomainmodel.domain.bankaccount.dto;

import java.math.BigDecimal;

public record AccountBalanceDto(Long accountId, BigDecimal amount) {
}
