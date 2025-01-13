package com.example.javaLang.generic.streamtest.groupBySum;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // InputData 객체 생성
        BrandGroupsReqDto brandGroupsReqDto = new BrandGroupsReqDto();
        brandGroupsReqDto.setMallId("B2E");
        brandGroupsReqDto.setOrderId("K1000004887");
        brandGroupsReqDto.setOrderDt("20250109222955");

        // BrandGroups 데이터 생성
        List<BrandGroupsReqDto.BrandGroup> brandGroups = new ArrayList<>();
        BrandGroupsReqDto.BrandGroup bg1 = new BrandGroupsReqDto.BrandGroup();
        bg1.setBrandGroupId("3");
        bg1.setRedeemPoints(10000);
        brandGroups.add(bg1);

        BrandGroupsReqDto.BrandGroup bg2 = new BrandGroupsReqDto.BrandGroup();
        bg2.setBrandGroupId("4");
        bg2.setRedeemPoints(20000);
        brandGroups.add(bg2);

        BrandGroupsReqDto.BrandGroup bg3 = new BrandGroupsReqDto.BrandGroup();
        bg3.setBrandGroupId("5");
        bg3.setRedeemPoints(10000);
        brandGroups.add(bg3);

        brandGroupsReqDto.setBrandGroups(brandGroups);

        // UpperBrandGroup 매핑
        Map<String, String> brandGroupToUpperGroup = new HashMap<>();
        brandGroupToUpperGroup.put("3", "1");
        brandGroupToUpperGroup.put("4", "2");
        brandGroupToUpperGroup.put("5", "2");

        // UpperBrandGroup별 RedeemPoints 합산 (스트림 사용)
        Map<String, Integer> upperBrandGroupPoints = brandGroupsReqDto.getBrandGroups().stream()
                .collect(Collectors.groupingBy(
                        bg -> brandGroupToUpperGroup.get(bg.getBrandGroupId()),
                        Collectors.summingInt(BrandGroupsReqDto.BrandGroup::getRedeemPoints)
                ));

        // UpperBrandGroup 리스트 생성
        List<UpperBrandGroup> upperBrandGroups = upperBrandGroupPoints.entrySet().stream()
                .map(entry -> new UpperBrandGroup(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        // 결과 출력
        System.out.println("Mall ID: " + brandGroupsReqDto.getMallId());
        System.out.println("Order ID: " + brandGroupsReqDto.getOrderId());
        System.out.println("Order Date: " + brandGroupsReqDto.getOrderDt());
        System.out.println("Upper Brand Groups:");
        upperBrandGroups.forEach(System.out::println);
    }
}