package com.smrj.shangmengruanjian.util;

import android.content.Context;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Uuid {

    private Context context;
    private static final String TEMP_DIR = "zdt_config";//声明uuid存储文件夹
    private static final String TEMP_FILE_NAME = "zdt_file";//声明uuid存储文件名

    public Uuid(Context context) {
        this.context = context;
    }



    //创建UUID
    public String createUUID(){
        String uuid = java.util.UUID.randomUUID().toString().replace("-","");
        File externalDownloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File zfcxFileDir = new File(externalDownloadsDir,TEMP_DIR);
        Toast.makeText(context,zfcxFileDir.getPath(),Toast.LENGTH_LONG);
        if (!zfcxFileDir.exists()){
            if (!zfcxFileDir.mkdir()){
                Toast.makeText(context,"文件夹创建失败: " + externalDownloadsDir.getPath(),Toast.LENGTH_SHORT).show();
            }
        }
        File file = new File(zfcxFileDir,TEMP_FILE_NAME);
        if (!file.exists()){
            FileWriter fileWriter = null;
            try {
                if (file.createNewFile()){
                    fileWriter = new FileWriter(file,false);
                    fileWriter.write(uuid);
                }else{
                    Toast.makeText(context,"文件创建失败" + externalDownloadsDir.getPath(),Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                Toast.makeText(context,"文件创建失败" + externalDownloadsDir.getPath(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                if (fileWriter != null){
                    try {
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else {
            FileReader fileReader = null;
            BufferedReader bufferedReader = null;
            try {
                fileReader = new FileReader(file);
                bufferedReader = new BufferedReader(fileReader);
                uuid = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bufferedReader != null){
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fileReader != null){
                    try {
                        fileReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Log.i("CREATEUUID",uuid);
        return uuid;
    }
}
