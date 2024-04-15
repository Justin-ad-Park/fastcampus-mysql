package com.example.pmo.memberalarm4;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class MemberAlarmEnum4Test {

    @Test
    void 회원알림_enum테스트() {
        MemberAlarmEnum4 memberAlarmEnum4 = MemberAlarmEnum4.USER_WELCOME;

        Assertions.assertNotNull(memberAlarmEnum4);
    }


    @Test
    void 알림_파라미터단수_푸시메시지확인() {
        MemberAlarmEnum4 memberAlarmEnum4 = MemberAlarmEnum4.USER_WELCOME;

        String template = memberAlarmEnum4.getAlarmMessage(2L);
        System.out.println(template);

        template = memberAlarmEnum4.getAlarmMessage(1L);
        System.out.println(template);

        Assertions.assertEquals("[가입인사]회원 홍길동님 가입을 환영합니다.", template);
    }

    @Test
    void 레벨알람_파라미터복수_푸시메시지확인() {
        MemberAlarmEnum4 memberAlarmEnum4 = MemberAlarmEnum4.USER_CHANGE_LEVEL;

        String template = memberAlarmEnum4.getAlarmMessage(1L);

        System.out.println(template);

        Assertions.assertEquals("[등급변경]홍길동님은 레벨1로 회원 등급이 변경되었습니다.", template);
    }
}
