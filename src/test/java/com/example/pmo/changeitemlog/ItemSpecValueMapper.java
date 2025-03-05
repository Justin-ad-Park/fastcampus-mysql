package com.example.pmo.changeitemlog;

public class ItemSpecValueMapper {


    protected static ItemSpecValueVo createItemSpecValueVo(String ilItemCode, Integer ilSpecMasterId, Integer ilSpecFieldId, String specFieldCd,
                                                         String specFieldValue, String specFieldSubValue, String specFieldHeaderValue,
                                                         Boolean directYn, Long createId, Long modifyId, boolean changedSpecValue) {
        ItemSpecValueVo vo = new ItemSpecValueVo();
        vo.setIlItemCode(ilItemCode);
        vo.setIlSpecMasterId(ilSpecMasterId);
        vo.setIlSpecFieldId(ilSpecFieldId);
        vo.setSpecFieldCd(specFieldCd);
        vo.setSpecFieldValue(specFieldValue);
        vo.setSpecFieldSubValue(specFieldSubValue);
        vo.setSpecFieldHeaderValue(specFieldHeaderValue);
        vo.setDirectYn(directYn);
        vo.setCreateId(createId);
        vo.setModifyId(modifyId);
        vo.setChangedSpecValue(changedSpecValue);

        return vo;
    }
}
