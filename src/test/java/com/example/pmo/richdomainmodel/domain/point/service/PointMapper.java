package com.example.pmo.richdomainmodel.domain.point.service;

import com.example.pmo.richdomainmodel.domain.point.Point;

import java.math.BigDecimal;

public class PointMapper {

    protected Point getPoint(long userId) {
        /**
         * select UserId, balance
         * from Point
         * where UserId = #{AccountId}
         */
        return null;
    }

    protected BigDecimal getBalance(Long userId) {
        /**
         * select point
         * from Account
         * where AccountId = #{userId}
         */

        return BigDecimal.valueOf(0);
    }

    protected void updateBalance(Long userId, BigDecimal subtract) {
        // Account 테이블에 차감된 amout를 업데이트 한다.
        /**
         * update Point
         * set balance = #{balance}
         * where UserId = #{userId}
         */
    }
}
