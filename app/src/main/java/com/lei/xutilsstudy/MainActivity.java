package com.lei.xutilsstudy;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private TextView text;
    private TextView text2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermission();
        text = findViewById(R.id.text);
        text2 = findViewById(R.id.text2);
    }

    public void doGet(View view) {
        String url = "http://www.tuling123.com/openapi/api";

        //?key=bdae886baa234e17bdba421783d178de&info=
        RequestParams params = new RequestParams(url);
        params.addParameter("key", "bdae886baa234e17bdba421783d178de");
        params.addParameter("info","Fuck");
        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {

            @Override//成功
            public void onSuccess(String result) {
                Log.i("Test","get click,onSuccess called,result" + result);
                final String str = result;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text.setText(str);
                    }
                });
            }

            @Override//失败
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("Test","get click,onError called" + ex);
            }
            /*
            调用cancelable.cancle();时回调
             */
            @Override
            public void onCancelled(CancelledException cex) {
                Log.i("Test","get click,onCancelled called");
            }

            @Override//结束
            public void onFinished() {
                Log.i("Test","get click,onFinished called");
            }
        });
    }

    public void doPost(View view) {
        String url = "http://www.tuling123.com/openapi/api";
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("key","bdae886baa234e17bdba421783d178de");
        params.addBodyParameter("info","Hello");
        params.addHeader("head","tuling");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("Test","post click,onFinished called " + result);

                final String str = result;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text.setText(str);
                    }
                });
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("Test","post click,onError called " + ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i("Test","post click,onCancelled called");
            }

            @Override
            public void onFinished() {
                Log.i("Test","post click,onFinished called");
            }
        });
    }

    public void doDownload(View view) {
        String url = "http://192.168.0.101:8080/okhttpDemo/files/a.mp4";
        RequestParams params = new RequestParams(url);
        //文件保存路径
        params.setSaveFilePath(Environment.getExternalStorageDirectory() + "/AAA/aa.mp4");
        //允许重命名
        params.setAutoRename(true);
        x.http().post(params, new Callback.ProgressCallback<File>(){

            @Override
            public void onSuccess(File result) {
                Log.i("Test","doDownload click,onError called " + result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("Test","doDownload click,onError called " + ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i("Test","doDownload click,onCancelled called ");
            }

            @Override
            public void onFinished() {
                Log.i("Test","doDownload click,onFinished called ");
            }

            @Override//下载中。。。
            public void onWaiting() {
                Log.i("Test","doDownload click,onWaiting called ");
            }

            @Override
            public void onStarted() {
                Log.i("Test","doDownload click,onStarted called ");
            }

            @Override//一直被回调
            public void onLoading(long total, long current, boolean isDownloading) {
                Log.i("Test","doDownload click,onLoading called " + current);

                final String str = current + "/" + total;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text2.setText(str);
                    }
                });
            }
        });
    }

    public void doUpload(View view) {
        String url = "http://192.168.0.101:8080/okhttpDemo/postFile";
        File file =new File(Environment.getExternalStorageDirectory() + "/a.zip");

        RequestParams params = new RequestParams(url);
        //表单提交的形式
        params.setMultipart(true);
        params.addBodyParameter("file",file);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("Test","doUpload click,onSuccess called " + result);
                final String str = result;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text.setText("上传" + str);
                    }
                });
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("Test","doUpload click,onError called " + ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i("Test","doUpload click,onCancelled called");
            }

            @Override
            public void onFinished() {
                Log.i("Test","doUpload click,onFinished called");
            }
        });
    }

    private void getPermission() {

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        }

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        }

    }
}
