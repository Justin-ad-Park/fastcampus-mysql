package com.example.pmo.richdomainmodel.domain.point.service;

import com.example.pmo.richdomainmodel.domain.common.exception.InvalidAmountException;
import com.example.pmo.richdomainmodel.domain.point.Point;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public class PointService {

    private PointMapper pointMapper;

    public Point getPoint(Long userId) {
        Point point = pointMapper.getPoint(userId);

        return point;
    }

    @Transactional
    public boolean credit(Long userId, BigDecimal amount) throws InvalidAmountException {
        Point point = pointMapper.getPoint(userId);
        point.credit(amount);

        pointMapper.updateBalance(point.getUserId(), point.balance() );

        return true;
    }
}
