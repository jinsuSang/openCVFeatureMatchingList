package com.example.myapplication;

import android.graphics.Bitmap;

public class ListComparedItem {
    private Bitmap comparedBmp;
    private double min_length;
    private double max_length;
    private int number;
    private int good_matches;
    private double correlation;
    private double chi_square;
    private double intersection;
    private double bhattacharyya;
    private boolean histogramResult;

    public ListComparedItem(Bitmap comparedBmp, double min_length, double max_length, int number, int good_matches,
                            double correlation, double chi_square, double intersection, double bhattacharyya,
                            boolean histogramResult) {
        this.comparedBmp = comparedBmp;
        this.min_length = min_length;
        this.max_length = max_length;
        this.number = number;
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

    public double getMin_length(){return min_length;}

    public double getMax_length(){return max_length;}

    public int getNumber(){return number;}

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
