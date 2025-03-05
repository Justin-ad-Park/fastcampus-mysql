package com.example.pmo.changeitemlog;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemSpecValue {
    /*
     * 품목별 상품정보 제공고시 상세항목 dto
     */
    @ApiModelProperty(value = "품목코드", required = true)
    private String ilItemCode;

    @ApiModelProperty(value = "상품정보제공고시항목 PK", required = true)
    private int ilSpecFieldId;

    @ApiModelProperty(value = "상품정보제공고시 상세 항목 정보", required = true)
    private String specFieldValue;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        ItemSpecValue item = (ItemSpecValue) o;

        return ilItemCode.equals(item.ilItemCode) && ilSpecFieldId == item.ilSpecFieldId && specFieldValue.equals(item.specFieldValue);
    }
}
