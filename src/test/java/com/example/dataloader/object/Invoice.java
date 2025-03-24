package com.example.dataloader.object;

import java.util.List;

public record Invoice(
        String customer,
        List<Performance> performances
) {
}
