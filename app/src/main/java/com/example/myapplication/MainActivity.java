package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;


import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.DMatch;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Scalar;
import org.opencv.features2d.AKAZE;
import org.opencv.features2d.BRISK;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.KAZE;
import org.opencv.features2d.ORB;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "OcvTest1";
    private final int REQ_CODE_SELECT_IMAGE = 100;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");

        if(!OpenCVLoader.initDebug()){
            Log.d(TAG, "OpenCV is not loaded!");
        } else {
            Log.d(TAG, "OpenCV is loaded!");
        }
    }

    private ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();
    private ArrayList<ListComparedItem> comparedBitmapArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // permission 확인
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(!hasPermissions(PERMISSIONS)){
                requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        }

    }

    // destory할 때 bitmap recycle
    @Override
    protected void onDestroy() {
        bitmapArrayList.clear();
        comparedBitmapArrayList.clear();
        super.onDestroy();
    }

    public void onButton1Clicked(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    ClipData clipData = data.getClipData();

                    ArrayList<String> pathArrayList = new ArrayList<>();
                    for (int i = 0; i < clipData.getItemCount(); i++){
                        String path = getImagePathFromURI(clipData.getItemAt(i).getUri());
                        pathArrayList.add(path);
                    }

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 4;
                    for (int i = 0; i< pathArrayList.size(); i++){
                        bitmapArrayList.add(BitmapFactory.decodeFile(pathArrayList.get(i), options));
                    }

                    // detectEdge가 피처매칭 부분
                    if (bitmapArrayList != null) {
                        detectEdge();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getImagePathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor == null){
            return contentUri.getPath();
        } else{
            int idx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String imgPath = cursor.getString(idx);
            cursor.close();
            return imgPath;
        }
    }

    static final int PERMISSIONS_REQUEST_CODE = 1000;
    String[] PERMISSIONS = {"android.permission.READ_EXTERNAL_STORAGE"};

    private boolean hasPermissions(String[] permissions){
        int result;

        for (String perms : permissions){
            result = ContextCompat.checkSelfPermission(this, perms);

            if(result == PackageManager.PERMISSION_DENIED){
                return false;
            }
        }

        return true;
    }

    public void detectEdge(){

        ArrayList<Mat> matArrayList = new ArrayList<>();

        for(int i = 0; i < bitmapArrayList.size(); i++){
            Mat mat = new Mat();
            Utils.bitmapToMat(bitmapArrayList.get(i), mat);
            matArrayList.add(mat);
        }

        // detector 모음
////         ORB detector
         ORB detector1 = ORB.create();
         //ORB detector2 = ORB.create();

        // KAZE detector
        //KAZE detector1 = KAZE.create();
//        KAZE detector2 = KAZE.create();

        // AKAZE detector

        //AKAZE detector1 = AKAZE.create();
//        AKAZE detector2 = AKAZE.create();
        // BRISK detector
         //BRISK detector1 = BRISK.create();
//         BRISK detector2 = BRISK.create();


        // keypoint와 description 생성
        ArrayList<MatOfKeyPoint> keyPointArrayList = new ArrayList<>();
        ArrayList<Mat> desriptorMatArrayList = new ArrayList<>();

        for (int i = 0; i < matArrayList.size(); i++){
            MatOfKeyPoint keyPoint = new MatOfKeyPoint();
            Mat descriptor = new Mat();
            detector1.detectAndCompute(matArrayList.get(i), new Mat(), keyPoint, descriptor);

            keyPointArrayList.add(keyPoint);
            desriptorMatArrayList.add(descriptor);
        }

        // Matcher 종류 선택

        DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMINGLUT);
//         BFMatcher matcher = BFMatcher.create();
//         FlannBasedMatcher matcher = FlannBasedMatcher.create();

        for (int i = 1; i< matArrayList.size(); i++) {
            Mat mainDescriptor = desriptorMatArrayList.get(0);
            Mat subDescriptor = desriptorMatArrayList.get(i);
            MatOfDMatch matches = new MatOfDMatch();
            matcher.match(mainDescriptor, subDescriptor, matches);

            List<DMatch> matchesList = matches.toList();
            Double max_dist = 0.0;
            Double min_dist = 100.0;


            // 최소 최대 거리 확인
            for (int j = 0; j < matchesList.size(); j++) {
                Double dist = (double) matchesList.get(j).distance;
                if (dist < min_dist)
                    min_dist = dist;
                if (dist > max_dist)
                    max_dist = dist;
            }

            LinkedList<DMatch> good_matches = new LinkedList<DMatch>();
            for (int j = 0; j < matchesList.size(); j++) {
                // 중요!!
                // 얼마나 유사한 피처를 매칭시킬건지 설정
                // 숫자가 높을수록 피처간 정확도 상승 매칭 개수 감소, 낮을수록 피처간 정확도 감소 매칭 개수 상승
                if (matchesList.get(j).distance <= 40)
                    good_matches.addLast(matchesList.get(j));
            }

            MatOfDMatch goodMatches = new MatOfDMatch();
            goodMatches.fromList(good_matches);


            // 피처는 레드, 선은 그린
            Scalar RED = new Scalar(255, 0, 0);
            Scalar GREEN = new Scalar(0, 255, 0);

            Mat edge = new Mat();
            MatOfByte drawnMatches = new MatOfByte();

            // 두 이미지간 피처 매칭 그리기
            Features2d.drawMatches(matArrayList.get(0), keyPointArrayList.get(0), matArrayList.get(i), keyPointArrayList.get(i), goodMatches, edge, GREEN, RED, drawnMatches, Features2d.DrawMatchesFlags_NOT_DRAW_SINGLE_POINTS);
//        Features2d.drawMatches(src1, keyPoint1, src2, keyPoint2, goodMatches, edge, GREEN, RED, drawnMatches, Features2d.DrawMatchesFlags_DEFAULT);

            // 이미지별 키 포인트 개수와 매칭된 포인트 개수
            Bitmap bitmap = Bitmap.createBitmap(edge.cols(), edge.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(edge, bitmap);

            edge.release();

            // histogram matching
            Mat hsvImg1 = new Mat();
            Mat hsvImg2 = new Mat();

            Imgproc.cvtColor(matArrayList.get(0), hsvImg1, Imgcodecs.IMREAD_ANYCOLOR);
            Imgproc.cvtColor(matArrayList.get(i), hsvImg2, Imgcodecs.IMREAD_ANYCOLOR);

            List<Mat> matList1 = new ArrayList<Mat>();
            List<Mat> matList2 = new ArrayList<Mat>();

            matList1.add(hsvImg1);
            matList2.add(hsvImg2);

            MatOfFloat range = new MatOfFloat(0,255);
            MatOfInt histSize = new MatOfInt(50);
            MatOfInt channels = new MatOfInt(0);

            Mat histogram1 = new Mat();
            Mat histogram2 = new Mat();

            Imgproc.calcHist(matList1, channels, new Mat(), histogram1, histSize, range);
            Imgproc.calcHist(matList2, channels, new Mat(), histogram2, histSize, range);

            Core.normalize(histogram1, histogram1, 0, 1, Core.NORM_MINMAX, -1, new Mat());
            Core.normalize(histogram2, histogram2, 0, 1, Core.NORM_MINMAX, -1, new Mat());

            /*
            * correlation: the higher the metric, the more accurate the match ">0.9"
            * chi_square: the lower the metric, the more accurate the match "<0.1"
            * intersection: the higher the metric, the more accurate the match ">1.5"
            * bhattacharyya: the lower the metric, the more accurate the match "<0.3"
            * */

            double correlation, chi_square, intersection, bhattacharyya;
            correlation = Imgproc.compareHist(histogram1, histogram2, 0);
            chi_square = Imgproc.compareHist(histogram1, histogram2, 1);
            intersection = Imgproc.compareHist(histogram1, histogram2, 2);
            bhattacharyya = Imgproc.compareHist(histogram1, histogram2, 3);

            int count = 0;
            boolean result = false;

            if(correlation > 0.9) count++;
            if(chi_square < 0.1) count++;
            if(intersection > 1.5) count++;
            if(bhattacharyya < 0.3) count++;

            if(count >= 3) result = true;

            comparedBitmapArrayList.add(new ListComparedItem(bitmap, min_dist,
                    max_dist, i, good_matches.size(),
                    correlation, chi_square, intersection, bhattacharyya, result));
        }
        ListView listView;
        ListViewAdapter adapter;

        adapter = new ListViewAdapter(comparedBitmapArrayList);

        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);

        comparedBitmapArrayList.clear();
        bitmapArrayList.clear();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
