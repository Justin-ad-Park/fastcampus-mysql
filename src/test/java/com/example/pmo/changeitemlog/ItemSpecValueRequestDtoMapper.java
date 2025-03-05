package com.example.pmo.changeitemlog;

public class ItemSpecValueRequestDtoMapper {

    protected static ItemSpecValueRequestDto createItemSpecValueRequestDto(String ilItemCode, int ilSpecFieldId, String specFieldValue,
                                                                         Boolean directYn, Long createId, Long modifyId) {
        ItemSpecValueRequestDto dto = new ItemSpecValueRequestDto();
        dto.setIlItemCode(ilItemCode);
        dto.setIlSpecFieldId(ilSpecFieldId);
        dto.setSpecFieldValue(specFieldValue);
        dto.setDirectYn(directYn);
        dto.setCreateId(createId);
        dto.setModifyid(modifyId);

        return dto;
    }
}
