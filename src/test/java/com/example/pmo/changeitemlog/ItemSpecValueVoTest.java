package com.example.pmo.changeitemlog;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.pmo.changeitemlog.ItemSpecValueMapper.createItemSpecValueVo;
import static com.example.pmo.changeitemlog.ItemSpecValueRequestDtoMapper.createItemSpecValueRequestDto;

class ItemSpecValueVoTest {

    @Test
    void after_before_달라진항목찾기() {
        /** given **/
        List<ItemSpecValueVo> beforeItemDatas = new ArrayList<>();
        beforeItemDatas.add(createItemSpecValueVo("0072060", 69, 102, "", "***[테스트 명칭_Before]***", "", "", null, 1646822L, null, false));
        beforeItemDatas.add(createItemSpecValueVo("0072060", 69, 103, "", "상품 상세 정보를 참고하세요.", "", "", null, 1646822L, null, false));
        // - Deleted -
        beforeItemDatas.add(createItemSpecValueVo("0072060", 69, 177, "", "해당 없음", "", "", null, 1646822L, null, false));


        List<ItemSpecValueRequestDto> afterItemDatas = new ArrayList<>();
        // - New(Inserted) -
        afterItemDatas.add(createItemSpecValueRequestDto("0072060", 87, "수령일 기준 2 일 이내 제조(생산) 제품이 배달됩니다. / 소비기한 : 5일", null, 1646822L, null));
        // - changed -
        afterItemDatas.add(createItemSpecValueRequestDto("0072060", 102, "***[테스트 명칭_After]***", null, 1646822L, null));
        // - No Changed -
        afterItemDatas.add(createItemSpecValueRequestDto("0072060", 103, "상품 상세 정보를 참고하세요.", null, 1646822L, null));

        Map<String, ItemSpecValue> beforeItemMap = toMapItemSpecValueVo(beforeItemDatas);
        Map<String, ItemSpecValue> afterItemMap = toMapItemSpecValueVo(afterItemDatas);

        /** when **/
        List<ChangedItemSpecField> changedItems = getChangedItemSpecFields(afterItemMap, beforeItemMap);

        /** then **/
        // 결과 출력
        changedItems.forEach(item -> System.out.println(item));

        // New 결과 검증
        Assertions.assertEquals("ChangedItem{before={ itemCode='0072060', fieldId=87, value='' }, after={ itemCode='0072060', fieldId=87, value='수령일 기준 2 일 이내 제조(생산) 제품이 배달됩니다. / 소비기한 : 5일' }}", changedItems.get(0).toString());
        // Changed 결과 검증
        Assertions.assertEquals("ChangedItem{before={ itemCode='0072060', fieldId=102, value='***[테스트 명칭_Before]***' }, after={ itemCode='0072060', fieldId=102, value='***[테스트 명칭_After]***' }}", changedItems.get(1).toString());
        // Deleted 결과 검증
        Assertions.assertEquals("ChangedItem{before={ itemCode='0072060', fieldId=177, value='해당 없음' }, after={ itemCode='0072060', fieldId=177, value='' }}", changedItems.get(2).toString());
    }

    // List를 Map<Key, Value>으로 변환 (key: itemCode + "-" + specFieldId)
    private static <T extends ItemSpecValue>  @NotNull Map<String, ItemSpecValue> toMapItemSpecValueVo(List<T> beforeItemDatas) {
        Map<String, ItemSpecValue> beforeItemMap = beforeItemDatas.stream()
                .collect(Collectors.toMap(
                        item -> item.getIlItemCode() + "-" + item.getIlSpecFieldId(),
                        item -> item
                ));
        return beforeItemMap;
    }

    protected static List<ChangedItemSpecField> getChangedItemSpecFields(
            Map<String, ItemSpecValue> afterItemMap, Map<String, ItemSpecValue> beforeItemMap) {
        List<ChangedItemSpecField> changedItems = new ArrayList<>();

        changedItems.addAll(newItemSpecFields(afterItemMap, beforeItemMap)); // 추가된 항목
        changedItems.addAll(updatedItemSpecFields(afterItemMap, beforeItemMap));
        changedItems.addAll(deletedItemSpecFields(afterItemMap, beforeItemMap)); // 삭제된 항목

        return changedItems;
    }

    private static List<ChangedItemSpecField> newItemSpecFields(Map<String, ItemSpecValue>  afterItemMap, Map<String, ItemSpecValue>  beforeItemMap) {
        List<ChangedItemSpecField> result = new ArrayList<>();

        afterItemMap.forEach((key, afterItem) -> {
            if (!beforeItemMap.containsKey(key)) {
                ItemSpecValue notExistItem = new ItemSpecValueRequestDto();
                notExistItem.setIlItemCode(afterItem.getIlItemCode());
                notExistItem.setIlSpecFieldId(afterItem.getIlSpecFieldId());
                notExistItem.setSpecFieldValue("");

                result.add(new ChangedItemSpecField(notExistItem, afterItem));
            }
        });

        return result;
    }

    private static List<ChangedItemSpecField> deletedItemSpecFields(Map<String, ItemSpecValue>  afterItemMap, Map<String, ItemSpecValue>  beforeItemMap) {
        List<ChangedItemSpecField> result = new ArrayList<>();

        beforeItemMap.forEach((key, beforeItem) -> {
            if (!afterItemMap.containsKey(key)) {
                ItemSpecValueRequestDto notExistItem = new ItemSpecValueRequestDto();
                notExistItem.setIlItemCode(beforeItem.getIlItemCode());
                notExistItem.setIlSpecFieldId(beforeItem.getIlSpecFieldId());
                notExistItem.setSpecFieldValue("");

                result.add(new ChangedItemSpecField(beforeItem, notExistItem));
            }
        });

        return result;
    }


    private static List<ChangedItemSpecField> updatedItemSpecFields(Map<String, ItemSpecValue>  afterItemMap, Map<String, ItemSpecValue>  beforeItemMap) {
        List<ChangedItemSpecField> result = new ArrayList<>();

        afterItemMap.forEach((key, afterItem) -> {
            ItemSpecValue beforeItem = beforeItemMap.get(key);
            if (beforeItem == null) return;

            if (beforeItem.equals(afterItem)) return;

            result.add(new ChangedItemSpecField(beforeItem, afterItem));
        });

        return result;
    }

    private List<ItemSpecValueVo> createBeforeItemDatas() {
        List<ItemSpecValueVo> result = new ArrayList<>();

        result.add(createItemSpecValueVo("0072060", 69, 99, "SPEC_FIELD_02", "080-022-0086", "", "", null, 1646822L, null, false));
        result.add(createItemSpecValueVo("0072060", 69, 102, "", "[테스트 명칭_Before]", "", "", null, 1646822L, null, false));
        result.add(createItemSpecValueVo("0072060", 69, 103, "", "상품 상세 정보를 참고하세요.", "", "", null, 1646822L, null, false));
        result.add(createItemSpecValueVo("0072060", 69, 104, "", "상품 상세 정보를 참고하세요.", "", "", null, 1646822L, null, false));
        result.add(createItemSpecValueVo("0072060", 69, 106, "", "상품 상세 정보를 참고하세요.", "", "", null, 1646822L, null, false));
        result.add(createItemSpecValueVo("0072060", 69, 107, "", "상품 상세 정보를 참고하세요.", "", "", null, 1646822L, null, false));
        result.add(createItemSpecValueVo("0072060", 69, 111, "", "상품 상세 정보를 참고하세요.", "", "", null, 1646822L, null, false));
        result.add(createItemSpecValueVo("0072060", 69, 116, "", "상품 상세 정보를 참고하세요.", "", "", null, 1646822L, null, false));
        result.add(createItemSpecValueVo("0072060", 69, 123, "", "해당 없음", "", "", null, 1646822L, null, false));
        result.add(createItemSpecValueVo("0072060", 69, 144, "", "상품 상세 정보를 참고하세요.", "", "", null, 1646822L, null, false));
        result.add(createItemSpecValueVo("0072060", 69, 177, "", "해당 없음", "", "", null, 1646822L, null, false));

        return result;
    }

    private List<ItemSpecValueRequestDto> createAfterItemDatas() {
        List<ItemSpecValueRequestDto> result = new ArrayList<>();

        result.add(createItemSpecValueRequestDto("0072060", 103, "상품 상세 정보를 참고하세요.", null, 1646822L, null));
        result.add(createItemSpecValueRequestDto("0072060", 104, "상품 상세 정보를 참고하세요.", null, 1646822L, null));
        result.add(createItemSpecValueRequestDto("0072060", 87, "수령일 기준 2 일 이내 제조(생산) 제품이 배달됩니다. / 소비기한 : 5일", null, 1646822L, null));
        result.add(createItemSpecValueRequestDto("0072060", 106, "상품 상세 정보를 참고하세요.", null, 1646822L, null));
        result.add(createItemSpecValueRequestDto("0072060", 107, "상품 상세 정보를 참고하세요.", null, 1646822L, null));
        result.add(createItemSpecValueRequestDto("0072060", 111, "상품 상세 정보를 참고하세요.", null, 1646822L, null));
        result.add(createItemSpecValueRequestDto("0072060", 144, "상품 상세 정보를 참고하세요.", null, 1646822L, null));
        result.add(createItemSpecValueRequestDto("0072060", 177, "해당 없음", null, 1646822L, null));
        result.add(createItemSpecValueRequestDto("0072060", 102, "테스트 명칭_After", null, 1646822L, null));
        result.add(createItemSpecValueRequestDto("0072060", 116, "상품 상세 정보를 참고하세요.", null, 1646822L, null));
        result.add(createItemSpecValueRequestDto("0072060", 123, "해당 없음", null, 1646822L, null));
        result.add(createItemSpecValueRequestDto("0072060", 99, "080-022-0086", null, 1646822L, null));

        return result;
    }

    // 변경된 항목을 저장하는 클래스
    static class ChangedItemSpecField {
        private final ItemSpecValue before;
        private final ItemSpecValue after;

        public ChangedItemSpecField(ItemSpecValue before, ItemSpecValue after) {
            this.before = before;
            this.after = after;
        }

        @Override
        public String toString() {
            return "ChangedItem{" +
                    "before={ itemCode='" + before.getIlItemCode() + "', fieldId=" + before.getIlSpecFieldId() +
                    ", value='" + before.getSpecFieldValue() + "' }" +
                    ", after={ itemCode='" + after.getIlItemCode() + "', fieldId=" + after.getIlSpecFieldId() +
                    ", value='" + after.getSpecFieldValue() + "' }" +
                    '}';
        }
    }
}