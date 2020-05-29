package com.example.myapplication;

import android.graphics.Bitmap;

public class ListComparedItem {
    private Bitmap comparedBmp;
    private int keypoint1;
    private int keypoint2;
    private int good_matches;
    private double correlation;
    private double chi_square;
    private double intersection;
    private double bhattacharyya;
    private boolean histogramResult;

    public ListComparedItem(Bitmap comparedBmp, int keypoint1, int keypoint2, int good_matches,
                            double correlation, double chi_square, double intersection, double bhattacharyya,
                            boolean histogramResult) {
        this.comparedBmp = comparedBmp;
        this.keypoint1 = keypoint1;
        this.keypoint2 = keypoint2;
        this.good_matches = good_matches;
        this.correlation = correlation;
        this.chi_square = chi_square;
        this.intersection = intersection;
        this.bhattacharyya = bhattacharyya;
        this.histogramResult = histogramResult;
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

    public double getCorrelation(){return correlation;}

    public double getChi_square() {
        return chi_square;
    }

    public double getIntersection() {
        return intersection;
    }

    public double getBhattacharyya() {
        return bhattacharyya;
    }

    public boolean isHistogramResult() {
        return histogramResult;
    }
}
