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

        String template = memberAlarmEnum4.getAlarmMessage(1L);
        System.out.println(template);

        Assertions.assertEquals("""
            {
            "CompID": "USER_REG",
            "Ver": "1",
            "Params": [
                {"UserNM": "홍길동"}
            ]
            }
            """, template);
    }

    @Test
    void 레벨알람_파라미터복수_푸시메시지확인() {
        MemberAlarmEnum4 memberAlarmEnum4 = MemberAlarmEnum4.USER_CHANGE_LEVEL;

        String template = memberAlarmEnum4.getAlarmMessage(1L);

        System.out.println(template);

        Assertions.assertEquals("""
            {
            "CompID": "USER_LEVEL",
            "Ver": "2",
            "Params": [
                {"UserNM": "홍길동"},
                {"UserLevel": "1"}
            ]
            }
            """, template);
    }
}
