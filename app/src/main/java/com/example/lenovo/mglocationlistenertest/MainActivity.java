package com.example.lenovo.mglocationlistenertest;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private SensorManager sensorManager;
    private MgLocation mg;
    private PointFinger pf1,pf2,pf3;
    double m,n,t,y;
    private PointFinger pfs1,pfs2,pfs3;
    private String TAg="min";
    public static String SystemDirPath= Environment.getExternalStorageDirectory().toString();
    public static String MyFolderPath=SystemDirPath+ File.separator+"sun";
    public static String MagFingerPointTxt=MyFolderPath+File.separator+"aa.txt";
    public static String str=Environment.getExternalStorageDirectory()+File.separator+"aa.txt";
    List<PointFinger> PF1=new ArrayList<PointFinger>();
    List<PointFinger> PF2=new ArrayList<PointFinger>();
    List<PointFinger> PF3=new ArrayList<PointFinger>();
    List<MatchPoint> MP=new ArrayList<MatchPoint>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        //读取文件
        Permission.RequestPermissions(this);
        ReadFile(MagFingerPointTxt);
        //拿到MG的引用，并把MP的值传递过去
        mg=new MgLocation(MainActivity.this,sensorManager);
        mg.setListener(new InterFace());
        //初始化
        mg.initList(PF1,PF2,PF3);

        mg.initial();
    }
    //读取文件
    public void ReadFile(String string) {
            try {
            //InputStream is = getResources().openRawResource(R.raw.aa);
            //String string=Environment.getExternalStorageDirectory()+File.separator+"aa.txt";
            FileInputStream fis=new FileInputStream(string);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String str = "";
                //一行一行的读取
                while ((str = br.readLine()) != null) {
                    //字符串空格分割
                    String[] data = str.split(",");
                    m = Double.valueOf(data[0]);
                    n = Double.valueOf(data[1]);
                    t = Double.valueOf(data[2]);
                    y = Double.valueOf(data[3]);
                    //初始化集合
                    pf1 = new PointFinger(m, y);
                    PF1.add(pf1);
                    pf2 = new PointFinger(n, y);
                    PF2.add(pf2);
                    pf3 = new PointFinger(t, y);
                    PF3.add(pf3);
                    //Toast.makeText(this, "OK",Toast.LENGTH_SHORT).show();
                    Log.d("PF1的大小", " " + PF1.size());

                }
                fis.close();
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    //接口里面的方法

    private class InterFace implements LocationListener{
        @Override
        public void Sprint(String str) {
            Log.i(TAg,"接口传递的坐标是:"+str);
        }
    }


}
