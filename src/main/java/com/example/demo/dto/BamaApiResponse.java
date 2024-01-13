package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class BamaApiResponse {
    private boolean status;
    private List<String> errors;
    private Metadata metadata;
    private List<DataItem> data;

    // سترها و گترها
    @Data
    public static class Metadata {
        private String last_update;
        private String title;
        private String description;

        // سترها و گترها
    }

    @Data
    public static class DataItem {
        private String brand;
        private String brand_fa;
        private int items_count;
        private List<Item> items;

        // سترها و گترها
        @Data
        public static class Item {
            private int id;
            private String brand;
            private String brand_fa;
            private String model;
            private String model_fa;
            private int model_year;
            private String price_date;
            private String price_provider;
            private int price;
            private int price_diff;
        }
    }
}
