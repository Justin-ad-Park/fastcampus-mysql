//package com.example.javaLang.generic.streamtest.chap16reactive.asynchronouspipeline;
//
//import com.example.javaLang.generic.streamtest.chap16reactive.FutureSample;
//import com.example.javaLang.generic.streamtest.chap16reactive.Shop;
//import org.junit.jupiter.api.Test;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class AsyncPipelineTest {
//    List<Shop> shops = Arrays.asList(new Shop("B-Mart"),
//            new Shop("C-mart"),
//            new Shop("S-mart"),
//            new Shop("4-Mart"),
//            new Shop("5-Mart"),
//            new Shop("6-Mart"),
//            new Shop("7-Mart"),
//            new Shop("8-Mart"),
//            new Shop("9-Mart"),
//
//    @Test
//    public List<String> findPrices(String product) {
//        return shops.stream()
//                .map(shop -> shop.getPrice(product))
//                .map(Quote::parse)
//                .map(Discount::applyDiscount)
//                .collect(Collectors.toList());
//    }
//
//
//}
