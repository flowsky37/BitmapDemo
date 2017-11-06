package com.example.bob.bitmpdemo;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         boolean b = PermissionsUtils.getInstance().verifyStoragePermissions(this);
        if (b){
            PermissionsUtils.getInstance().showMissingPermissionDialog(this);
        }
        readPic();

    }

    private void readPic() {
        Resources resources = getResources();

        //将资源中图片进行规定尺寸压缩
        Bitmap bitmap = ImageUtil.decodeBitmapFromResource(resources, R.mipmap.weixin1, 480, 800);
//        //将图片存储到SD卡中
        String str = ImageUtil.savePicInSD(bitmap);

        //质量压缩并存储到SD卡中
        ImageUtil.qualityCompress1(BitmapFactory.decodeResource(resources, R.mipmap.weixin1), 300);

        System.out.println("----------location: "+ str);

    }
}
