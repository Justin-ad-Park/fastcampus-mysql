package com.example.javaLang.generic.streamtest.chap15.reactiveprogramming;

import org.junit.jupiter.api.Test;

public class ArithmeticCellTest {
    @Test
    void test() {
        SimpleCell voltageSensor1 = new SimpleCell("Voltage Sensor1");
        SimpleCell voltageSensor2 = new SimpleCell("Voltage Sensor2");
        ArithmeticCell voltageTotal = new ArithmeticCell("Total Voltage");

        // 아래 1번, 2번은 동일한 로직임
        // 1번
        voltageSensor1.subscribe(voltageTotal::setLeft);

        // 2번
        voltageSensor2.subscribe(
                value -> voltageTotal.setRight(value)
        );

        voltageTotal.subscribe(displayPannel);
        voltageTotal.subscribe(sendMessage);

        voltageSensor1.onChange(10);
        voltageSensor2.onChange(20);
        voltageSensor1.onChange(15);
    }

    private Subscriber<Integer> displayPannel = (value) -> System.out.println("==Display Pannel == : " + value);
    private Subscriber<Integer> sendMessage = (value) -> System.out.println("==Send Message == : " + value);
}
