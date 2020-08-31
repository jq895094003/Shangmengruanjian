package com.smrj.shangmengruanjian.baseactivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.asynchttpclient.MyAsyncClient;
import com.smrj.shangmengruanjian.asynchttpclient.MyResponseHandler;
import com.smrj.shangmengruanjian.progressdialog.ProgressDialogUtil;
import com.smrj.shangmengruanjian.util.MyUrl;
import com.smrj.shangmengruanjian.util.Uuid;

import org.json.JSONObject;

import java.io.File;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class Login extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.btn_log)
    Button button;//声明登录按钮
    Editor editor;//声明内部存储控件得输入对象
    @BindView(R.id.password)
    EditText password;//声明密码框
    private String permission[] = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.REQUEST_INSTALL_PACKAGES};//声明获取手机信息权限
    private static final int PERMISSION_REQUEST = 1;
    List<String> mPermissionList = new ArrayList<>();
    String power = "";//初始话角色权限值
    @BindView(R.id.login_progress)
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;//初始化内部存储控件
    @BindView(R.id.username)
    EditText username;//初始化账号输入框
    private Uuid uuid = new Uuid(this);
    private ProgressDialogUtil progressDialogUtil;

    //Activity生命周期，页面创建方法
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        progressDialogUtil = new ProgressDialogUtil();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置App只能竖屏使用
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);//实例化内部存储空间
        editor = sharedPreferences.edit();//实例化写入对象
        button.setOnClickListener(this);//声明登录按钮的点击监听事件
        checkPermission();//调用检查权限方法
        uuidQuery();//查询uuid是否存在
    }

    private void uuidQuery() {
        progressDialogUtil.showProgressDialog(Login.this, "登录中");
        RequestParams requestParams = new RequestParams();
        requestParams.put("phonenumber", uuid.createUUID());
        requestParams.put("appname", MyUrl.appName);
        MyAsyncClient.doPost(new MyUrl().getUuidQueryMethod(), requestParams, new MyResponseHandler(this) {
            @Override
            public void success(JSONObject jsonObject) {
                progressDialogUtil.dismiss(Login.this);
                if (jsonObject.optString("code").equals("200")) {
                    JSONObject data = jsonObject.optJSONObject("data");
                    if (data.optString("state").equals("未审核") || data.optString("state").equals("已停用")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                        builder.setTitle("温馨提醒：");
                        builder.setMessage("该账号未审核，请联系供应商审核账号！");
                        builder.setCancelable(false);
                        // 相当于确定
                        builder.setPositiveButton("退出",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                        builder.show();
                    } else {
                        if (data.optString("loginstate").equals("在线")) {
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            Login.this.finish();
                            return;
                        }
                        updateMethod(getAppVersionName(Login.this));
                        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                        if (data.optInt("enddate") < 7 && data.optInt("enddate") >= 0) {
                            builder.setTitle("温馨提醒：");
                            builder.setMessage("您的使用权限即将到期，还剩余 ：" + data.optInt("enddate") + "天\n\n为避免影响您正常使用\n\n请及时联系供应商进行续费授权");
                            builder.setPositiveButton("确定", null);
                            builder.show();
                        } else if (data.optInt("enddate") < 0) {
                            builder.setTitle("温馨提醒：");
                            builder.setMessage("您的使用权限已到期\n\n暂时无法进行登录使用\n\n请及时联系供应商进行开通授权");
                            builder.setCancelable(false);
                            builder.setPositiveButton("退出",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    });
                            builder.show();
                        }
                        return;
                    }
                }
                if (jsonObject.optString("code").equals("400")) {
                    Toast.makeText(Login.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, Register.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setClass(Login.this, Register.class);
                    startActivity(intent);
                }
            }

            @Override
            public void faile(JSONObject jsonObject) {
                progressDialogUtil.dismiss(Login.this);
                Toast.makeText(Login.this, "请求失败", Toast.LENGTH_SHORT).show();
            }

        });
    }

    ProgressDialog progressDialog;

    private void updateMethod(final String serviceVersion) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("userapkname", MyUrl.updateAppName + MyUrl.appName);
        requestParams.put("userapkcoed", getAppVersionName(this));
        MyAsyncClient.doPost(new MyUrl().getUpDateQueryMethod(), requestParams, new MyResponseHandler(Login.this) {
            @Override
            public void success(JSONObject jsonObject) {
                if (jsonObject.optString("code").equals("200")) {

                } else if ("400".equals(jsonObject.optString("code"))) {
                    System.out.println("jsonObject = " + jsonObject);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    builder.setTitle("更新提示");
                    builder.setMessage("发现新版本，是否更新?");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (getDirAppVersion().equals(serviceVersion)) {//比对本地安装包的版本号
                                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), MyUrl.updateAppName + MyUrl.appName + ".apk");//获取本地安装包路径
                                Login.this.installApk(file);//调用安装方法
                            } else {
                                progressDialog = new ProgressDialog(Login.this);
                                progressDialog.setProgressStyle(R.style.update_progress_horizontal);
                                progressDialog.setTitle("正在下载");
                                progressDialog.setMessage("请稍后...");
                                progressDialog.setProgress(0);
                                progressDialog.setMax(100);
                                progressDialog.show();
                                progressDialog.setCancelable(false);
                                downLoadAppMethod();
                            }
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            progressDialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    /**
     * 下载Apk方法
     */
    private void downLoadAppMethod() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();//声明请求框架
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), MyUrl.updateAppName);//获取要下载的路径
//        MyAsyncClient.downLoadFile(Login.this, new MyUrl().getDownLoadMethod() + MyUrl.updateAppName + MyUrl.appName + ".apk", new FileAsyncHttpResponseHandler(Login.this) {//发起下载请求
        asyncHttpClient.get(new MyUrl().getDownLoadMethod() + MyUrl.updateAppName + MyUrl.appName + ".apk", (ResponseHandlerInterface) new FileAsyncHttpResponseHandler(file) {//发起post请求
            //下载成功方法
            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                if (progressDialog.isShowing()) {//判断弹出框是否在打开状态
                    progressDialog.dismiss();//关闭弹出框
                }
                Toast.makeText(Login.this, "下载成功", Toast.LENGTH_SHORT).show();//弹出页面提示
                installApk(file);//调用安装方法
            }

            //下载失败方法
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                if (progressDialog.isShowing()) {//判断弹出框是否在打开状态
                    progressDialog.dismiss();//关闭弹出框
                }
                Toast.makeText(Login.this, "下载失败", Toast.LENGTH_SHORT).show();//弹出页面提示
            }

            /**
             * 进度条设置方法
             * @param bytesWritten
             * @param totalSize
             */
            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);
                double d1 = bytesWritten;//获取进度值
                Double.isNaN(d1);//Double类型判空
                double d2 = d1 * 1.0D;//将d1参数转换为double
                double d3 = totalSize;//获取进度总值
                Double.isNaN(d3);//Double类型判空
                int i = (int) (100.0D * (d2 / d3));//计算进度值
                progressDialog.setProgress(i);//给进度条设置值
            }

        });
    }

    //安装apk方法
    private void installApk(final File file) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setTitle("安装");
        builder.setMessage("下载已完成，是否安装？");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri uri;
                if (!file.exists()) {//判断下载的文件是否存在
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW);//设置跳转页面
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//如果安卓版本大于等于7
                    uri = FileProvider.getUriForFile(Login.this, "com.smrj.shangmengruanjian.fileprovider", file);//设置URI，
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//解决页面启动异常
                } else {
                    uri = Uri.fromFile(file);//设置uri
                }
                intent.setDataAndType(uri, "application/vnd.android.package-archive");//打开外部文件，设置跳转类型
                startActivity(intent);//跳转页面
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {//弹出框点击“否”监听事件
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.dismiss();//关闭弹出框
            }
        });
        builder.show();//显示弹出框
    }

    /**
     * 登录方法
     */
    private void loginMethod() {
        progressDialogUtil.showProgressDialog(Login.this, "登录");
        RequestParams requestParams = new RequestParams();//声明参数对象
        requestParams.put("dworkerno", username.getText().toString());//获取username输入框内的值
        requestParams.put("dpassword", password.getText().toString());//获取password输入框的值
        MyAsyncClient.doPost(new MyUrl().getLoginMethod(), requestParams, new MyResponseHandler(Login.this) {//发起post请求
            //成功方法
            @Override
            public void success(JSONObject jsonObject) {
                if (jsonObject.optString("code").equals("200")) {//如果code值为200
                    JSONObject data = jsonObject.optJSONObject("data");//从jsonobject中获取到data参数
                    editor.putString("workercode", data.optString("dworkercode"));
                    editor.putString("workerno", data.optString("dworkerno"));//从data参数中取出workerno值放入写入对象
                    editor.putString("shopno", data.optString("dshopno"));//从data参数中取出shopno值放入写入对象
                    editor.commit();//提交写入对象
                    Toast.makeText(Login.this, "登录成功", Toast.LENGTH_SHORT).show();//弹出页面提示
                    loginStatusMethod();
                } else {
                    progressDialogUtil.dismiss(Login.this);
                    Toast.makeText(Login.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void faile(JSONObject jsonObject) {
                progressDialogUtil.dismiss(Login.this);
                Toast.makeText(Login.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //更改登录状态
    private void loginStatusMethod() {
        RequestParams requestParams = new RequestParams();//声明参数对象
        requestParams.put("phonenumber", new Uuid(Login.this).createUUID());
        MyAsyncClient.doPost(new MyUrl().getLoginstatusMethod(), requestParams, new MyResponseHandler(Login.this) {
            @Override
            public void success(JSONObject jsonObject) {
                progressDialogUtil.dismiss(Login.this);
                if (jsonObject.optString("code").equals("200")) {
                    String session = jsonObject.optString("data");
                    editor.putString("SESSION", session);
                    editor.commit();//提交写入对象
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    Login.this.finish();
                }
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }


    /**
     * 获取当前已安装App版本号
     *
     * @param paramContext
     * @return
     */
    public static String getAppVersionName(Context paramContext) {
        Date localDate = new Date();
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        PrintStream localPrintStream = System.out;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(localSimpleDateFormat.format(localDate));
        localPrintStream.println(localStringBuilder.toString());
        try {
            return paramContext.getApplicationContext().getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException localNameNotFoundException) {
            Log.e("", localNameNotFoundException.getMessage());
        }
        return "";
    }

    /**
     * 获取本地安装包的版本号，用于查询是否需要下载服务器安装包
     *
     * @return
     */
    private String getDirAppVersion() {
        PackageManager packageManager = getPackageManager();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        stringBuilder.append("/" + MyUrl.updateAppName + MyUrl.appName + ".apk");
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(stringBuilder.toString(), PackageManager.GET_ACTIVITIES);
        return (packageInfo == null) ? "null" : packageInfo.versionName;
    }

    private void checkPermission() {
        mPermissionList.clear();
        for (int i = 0; i < permission.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permission[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permission[i]);
            }
        }
        if (mPermissionList.size() == 0) {

        } else {
            Log.i("PermissionListSize", String.valueOf(mPermissionList.size()));
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST:
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_log:
                loginMethod();
                break;
        }
    }
}