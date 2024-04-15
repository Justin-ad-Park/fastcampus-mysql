package com.example.pmo.memberalarm;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class MemberAlarmEnumEnumTest {

    @Test
     void 회원알림_enum테스트() {
        MemberAlarmEnum memberAlarmEnum = MemberAlarmEnum.USER_WELCOME;

        Assertions.assertNotNull(memberAlarmEnum);
    }



    @Test
    void 알림_파라미터단수_푸시메시지확인() {
        MemberAlarmEnum memberAlarmEnum = MemberAlarmEnum.USER_WELCOME;

        String template = memberAlarmEnum.getAlarmMessage(1L);

        System.out.println(template);

        Assertions.assertEquals("회원 홍길동님 환영합니다.", template);
    }

    @Test
    void 레벨알람_파라미터복수_푸시메시지확인() {
        MemberAlarmEnum memberAlarmEnum = MemberAlarmEnum.USER_CHANGE_LEVEL;

        String template = memberAlarmEnum.getAlarmMessage(1L);

        System.out.println(template);

        Assertions.assertEquals("회원 홍길동님은 레벨1로 변경되었습니다.", template);
    }


}
