package com.example.pmo.richdomainmodel.mall.point;

import com.example.pmo.richdomainmodel.domain.common.exception.InsufficientBalanceException;
import com.example.pmo.richdomainmodel.domain.common.exception.InvalidAmountException;
import com.example.pmo.richdomainmodel.domain.point.bizservice.PointBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class PointController {

    @Autowired
    private PointBizService pointBizService;

    /**
     * 포인트 선물하기
     */
    @PostMapping(value = "/point/giftPoint")
    public boolean giftPoint(Long fromAccountId, Long toUserId, BigDecimal amount) throws InsufficientBalanceException, InvalidAmountException {
        return pointBizService.transferBalanceToPoint(fromAccountId, toUserId, amount);
    }

}
