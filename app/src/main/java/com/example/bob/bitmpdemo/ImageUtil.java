package com.example.bob.bitmpdemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.attr.bitmap;

/**
 * Created by bob on 2017/11/5.
 */

public class ImageUtil {
    public static final String LOCAL_URL = Environment.getExternalStorageDirectory().getAbsolutePath()
            +"/Android/data/com.example.bob.bitmpdemo/images/";


    /**
     *
     * 尺寸压缩：根据目标View的尺寸压缩图片返回bitmap
     * @param resources
     * @param resId
     * @param width
     * @param height
     * @return
     */
    public static Bitmap decodeBitmapFromResource(Resources resources, int resId,int width ,int height){

        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(resources,resId,options);

        options.inSampleSize = calculateInSampleSize(options,width,height);

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(resources,resId,options);

    }

    /**
     * 获取采样率
     * @param options
     * @param reqWidth 目标view的宽
     * @param reqHeight 目标view的高
     * @return 采样率
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        int originalWidth = options.outWidth;
        int originalHeight = options.outHeight;

        int inSampleSize = 1;


        if (originalHeight > reqHeight || originalWidth > reqHeight){
            int halfHeight = originalHeight / 2;
            int halfWidth = originalWidth / 2;

            while ((halfWidth / inSampleSize) >= reqHeight && (halfHeight /inSampleSize)>=reqWidth){
                inSampleSize *= 2;
            }

        }

        return inSampleSize;



    }

    /**
     * 保存图片并返回存储路径
     * @param bitmap
     * @return
     */
    public static String savePicInSD(Bitmap bitmap){
        int i2 = bitmap.getWidth() * bitmap.getHeight() / 1024;
        SimpleDateFormat formatYMD = new SimpleDateFormat("yyyy/MM/dd");

        String compressImgUri = LOCAL_URL + "000/"
                + formatYMD.format(new Date()) + "/" + System.currentTimeMillis() + ".jpg";

        File outputFile = new File(compressImgUri);
        try {
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
            } else {
                outputFile.delete();
            }
            FileOutputStream out = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100, out);

            int i3 = bitmap.getWidth() * bitmap.getHeight() / 1024;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputFile.getPath();
    }

    /**
     * 质量压缩并存到SD卡中
     * @param bitmap
     * @param reqSize 需要的大小
     * @return
     */

    public static String qualityCompress1(Bitmap bitmap ,int reqSize){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //这里100表示不压缩，把压缩后的数据存放到baos中
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        int options = 95;
        //如果压缩后的大小超出所要求的，继续压缩
        while (baos.toByteArray().length / 1024 > reqSize){
            baos.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG,options,baos);

            //每次减少5%质量
            if (options>5){
                options -=5;
            } else {
                break;
            }

        }


        //存入SD卡中
        SimpleDateFormat formatYMD = new SimpleDateFormat("yyyy/MM/dd");

        String compressImgUri = LOCAL_URL + "000/"
                + formatYMD.format(new Date()) + "/" + System.currentTimeMillis() + "a.jpg";

        File outputFile = new File(compressImgUri);
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
            } else {
                outputFile.delete();
            }
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(outputFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            bitmap.compress(Bitmap.CompressFormat.JPEG, options, out);

            return outputFile.getPath();

    }



}
