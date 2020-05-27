package com.example.myapplication;

import android.graphics.Bitmap;

public class ListComparedItem {
    private Bitmap comparedBmp;
    private int keypoint1;
    private int keypoint2;
    private int good_matches;

    public ListComparedItem(Bitmap comparedBmp, int keypoint1, int keypoint2, int good_matches) {
        this.comparedBmp = comparedBmp;
        this.keypoint1 = keypoint1;
        this.keypoint2 = keypoint2;
        this.good_matches = good_matches;
    }

    public Bitmap getComparedBmp() {
        return comparedBmp;
    }

    public Integer getGood_matches() {
        return good_matches;
    }

    public long getKeypoint1() {
        return keypoint1;
    }

    public long getKeypoint2() {
        return keypoint2;
    }
}
