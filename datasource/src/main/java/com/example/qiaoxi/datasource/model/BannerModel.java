package com.example.qiaoxi.datasource.model;

public class BannerModel {
    public int imgPath;
    public String abstractContent;

    public BannerModel(int path, String text) {
        imgPath = path;
        abstractContent = text;
    }
}
