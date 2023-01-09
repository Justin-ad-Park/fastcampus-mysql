package com.example.fastcampusmysql.pulmuone.v1.comm.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

public class ItemEnums {

    // 상품정보제공고시 상세 메시지 : IL_SPEC_FIELD 테이블의 SPEC_FIELD_CD ( 상품정보제공고시항목 코드 ) 에서 사용
    @Getter
    @RequiredArgsConstructor
    public enum SpecFieldCode implements CodeCommEnum {

        SPEC_FIELD_01(
                "SPEC_FIELD_01"
                , "제조연월일/소비기한 또는 품질유지기한"
                , "수령일 기준 {*소비기간별 출고기한} 일 이내 제조(생산) 제품이 배달됩니다."
                , Arrays.asList("{*소비기간별 출고기한}")
                , true
        )

        , SPEC_FIELD_02(
                "SPEC_FIELD_02"
                , "소비자상담 관련 전화번호"
                , "{*소비자상담 관련 전화번호}"
                , Arrays.asList("{*소비자상담 관련 전화번호}")
                , false
        );

        public final String code;
        private final String codeName; // 상품정보 제공고시 항목명
        private final String specFieldDetailMessage; // 상품정보 제공고시 기본 상세 메시지
        private final List<String> variableStringList; // 값으로 치환활 변수 문자열 목록
        private final boolean hasAddtionalMessage;  // 추가 메시지 존재 여부

        /*
         * 기본 상세 메시지에서 param 으로 받은 값 목록으로 치환된 수정 메시지 반환
         */
        public String getModifiedDetailMessage(List<String> valueList) {

            // 값으로 치환활 변수 문자열 목록과 param 으로 받은 변수 목록의 길이가 다른 경우 null 반환
            if (variableStringList.size() != valueList.size()) {
                return null;
            }

            String modifiedMessage = this.getSpecFieldDetailMessage(); // 수정된 메시지 : 최소 기본 상세 메시지로 세팅

            for (int i = 0; i < valueList.size(); i++) {

                // 변수 문자열 목록을 param 으로 받은 값으로 치환
                modifiedMessage = modifiedMessage.replace(this.getVariableStringList().get(i), valueList.get(i));
            }

            return modifiedMessage;
        }

        public String getAdditionalMessage(String subName) {
            if(this.hasAddtionalMessage)
                return SpecFieldAdditional.valueOf(this, subName).getAdditionalMessage();

            return "";
        }

        @Getter
        @RequiredArgsConstructor
        public enum SpecFieldAdditional implements SpecFieldAdditionInfo {

            SPEC_FIELD_01_69(SPEC_FIELD_01, "69", "가공식품",
                    "(단, '식품 등의 표시·광고에 관한 법률' 개정에 따라 유통기한을 소비기한으로 변경하는 중에 있으므로 유통기한으로 표시 된 제품이 배송될 수 있습니다.)"),
            SPEC_FIELD_01_71(SPEC_FIELD_01, "71", "식품(농수축산물)",
                    "(단, '식품 등의 표시·광고에 관한 법률' 개정에 따라 유통기한을 소비기한으로 변경하는 중에 있으므로 유통기한으로 표시 된 제품이 배송될 수 있습니다.)"),
            SPEC_FIELD_01_72(SPEC_FIELD_01, "72", "건강기능식품",
                    "보관방법 : 상품 상세페이지 참조 (단, '식품 등의 표시·광고에 관한 법률' 개정에 따라 유통기한을 소비기한으로 변경하는 중에 있으므로 유통기한으로 표시 된 제품이 배송될 수 있습니다.)");

            private final SpecFieldCode specFieldcode;
            private final String code;
            private final String codeName;
            private final String additionalMessage;

            static private SpecFieldAdditional valueOf(SpecFieldCode specFieldCode, String name) {
                String enumName = String.format("%s_%s", specFieldCode.getCode(), name);
                return SpecFieldAdditional.valueOf(enumName);
            }

        }
    }
}
