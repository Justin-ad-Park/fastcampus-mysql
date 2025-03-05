package com.example.pmo.changeitemlog;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemSpecValueVo  extends ItemSpecValue {
    /*
     * 품목별 상품정보 제공고시 상세항목 Vo
     */
    private int ilSpecMasterId; // 상품정보제공고시항목 마스터 PK

    private String specFieldCd; // 상품정보제공고시항목 코드

    private String specFieldSubValue; // 상품정보제공고시 상세 항목 추가 정보

    private String specFieldHeaderValue; // 상품정보제공고시 헤더 항목 추가 정보

    // IL_ITEM_SPEC_VALUE 테이블의 DIRECT_YN 컬럼에는 'Y', null 값만 있음
    // BooleanTypeHandler 에 의해 IL_ITEM_SPEC_VALUE 테이블의 DIRECT_YN 컬럼 'Y' 인 경우에만 true, 그 외에는 null 값 지정됨
    private Boolean directYn;

    private Long createId; // 등록자 ID

    private Long modifyId; // 수정자 ID

    private boolean changedSpecValue; // 상품정보제공고시 상세항목 변경여부

}
