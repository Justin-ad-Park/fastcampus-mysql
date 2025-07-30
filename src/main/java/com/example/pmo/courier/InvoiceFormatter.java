package com.example.pmo.courier;

public class InvoiceFormatter {

    @FunctionalInterface
    private interface IFormat {
        String format(String invoiceNumber);
    }

    private enum CourierType {
        CJCourier("CJ대한통운", FormatterUtils.convertHypenToEmpty),
        KurlyCourier("컬리새벽배송", FormatterUtils.convertNormal),
        KoreaCourier("우체국택배", FormatterUtils.convertNotAllowHypen);

        private final String courierName;
        private final IFormat formatter;

        CourierType(String courierName, IFormat formatter) {
            this.courierName = courierName;
            this.formatter = formatter;
        }

        private String format(String invoiceNumber) {
            return formatter.format(invoiceNumber);
        }

        public static CourierType fromName(String name) {
            for (CourierType type : values()) {
                if (type.courierName.equalsIgnoreCase(name.trim())) {
                    return type;
                }
            }
            throw new IllegalArgumentException("지원하지 않는 택배사: " + name);
        }
    }

    private static class FormatterUtils {
        private static IFormat convertHypenToEmpty  = (String invoiceNo) -> {
            return invoiceNo.replaceAll("-", "");
        };

        private static IFormat convertNormal = (String invoiceNo) ->  {
            return invoiceNo;
        };

        private static IFormat convertNotAllowHypen = (String invoiceNo) -> {
            if (invoiceNo.contains("-")) {
                throw new IllegalArgumentException("해당 택배사는 송장번호에 하이픈을 허용하지 않습니다.");
            }

            return invoiceNo;
        };
    }

    public static String formatInvoice(String courierName, String invoiceNumber) {
        if (courierName == null || invoiceNumber == null) {
            throw new IllegalArgumentException("택배사나 송장번호가 null입니다.");
        }
        try {
            CourierType courier = CourierType.fromName(courierName);
            return courier.format(invoiceNumber);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }
}