package com.example.pmo.courier;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceFormatterTest {

    @Test
    void testCJCourier_withHyphen() {
        String formattedInvoice = InvoiceFormatter.formatInvoice("CJ대한통운", "123-456-7890");
        assertEquals("1234567890", formattedInvoice);
    }

    @Test
    void testKurlyCourier_withHyphen() {
        String formattedInvoice = InvoiceFormatter.formatInvoice("컬리새벽배송", "987-654-3210");
        assertEquals("987-654-3210", formattedInvoice);
    }

    @Test
    void testKoreaCourier_withHyphen() {
        String formattedInvoice = InvoiceFormatter.formatInvoice("우체국택배", "9876543210");
        assertEquals("9876543210", formattedInvoice);
    }


    @Test
    void testKoreaCourier_throwsExceptionIfHyphenPresent() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                InvoiceFormatter.formatInvoice("우체국택배", "123-456-7890")
        );
        assertEquals("해당 택배사는 송장번호에 하이픈을 허용하지 않습니다.", ex.getMessage());
    }

    @Test
    void testInvalidCourier_throwsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                InvoiceFormatter.formatInvoice("없는택배사", "1234567890")
        );
        assertEquals("지원하지 않는 택배사: 없는택배사", ex.getMessage());
    }

    @Test
    void testNullCourier_throwsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                InvoiceFormatter.formatInvoice(null, "1234567890")
        );
        assertEquals("택배사나 송장번호가 null입니다.", ex.getMessage());
    }

    @Test
    void testNullInvoice_throwsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                InvoiceFormatter.formatInvoice("CJ대한통운", null)
        );
        assertEquals("택배사나 송장번호가 null입니다.", ex.getMessage());
    }
}