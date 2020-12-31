package me.liuning.core.crash;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import me.liuning.core.log.LogUtils;

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private volatile static CrashHandler sInstance;
    private Thread.UncaughtExceptionHandler mDefault;

    private static final String CRASH_DIRECTORY = "crash";

    private volatile boolean mInit;
    private Context mContext;
    private File mCrashDirectory;

    private CrashHandler() {
        Thread.currentThread().setUncaughtExceptionHandler(this);
        mDefault = Thread.getDefaultUncaughtExceptionHandler();
        mInit = false;

    }

    public static CrashHandler getInstance() {
        if (sInstance == null) {
            synchronized (CrashHandler.class) {
                if (sInstance == null) {
                    sInstance = new CrashHandler();
                }
            }
        }

        return sInstance;

    }

    public void init(Context context) {
        if (mInit) {
            return;
        }
        mInit = true;
        this.mContext = context.getApplicationContext();
        String crashPath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            crashPath = Environment.getExternalStorageDirectory() + File.separator + mContext.getPackageName() + File.separator + CRASH_DIRECTORY;
        } else {
            crashPath = mContext.getFilesDir() + File.separator + CRASH_DIRECTORY;
        }

        LogUtils.debug("crash file path is:" + crashPath);

        mCrashDirectory = new File(crashPath);
        if (!mCrashDirectory.exists()) {
            mCrashDirectory.mkdirs();
        }
    }

    public String getCrashDirectory() {
        if (mCrashDirectory == null) {
            return "";
        }
        return mCrashDirectory.getAbsolutePath();
    }


    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {

        //record
        Map<String, String> buildInformation = getBuildInformation();
        Map<String, String> packageInformation = getPackageInformation(mContext);
        String exceptionInformation = getExceptionInformation(e);
        StringBuilder builder = buildInformation(buildInformation, packageInformation, exceptionInformation);
        File crashFile = new File(mCrashDirectory, generalFileName());
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(crashFile);
            fileOutputStream.write(builder.toString().getBytes());
            fileOutputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

        }

        mDefault.uncaughtException(t, e);

    }

    private StringBuilder buildInformation(Map<String, String> buildInformation, Map<String, String> packageInformation, String exceptionInformation) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> item : buildInformation.entrySet()) {
            builder.append(item.getKey()).append(":");
            builder.append(item.getValue());
            builder.append("\r\n");
        }

        for (Map.Entry<String, String> item : packageInformation.entrySet()) {
            builder.append(item.getKey()).append(":");
            builder.append(item.getValue());
            builder.append("\r\n");
        }
        //写入文件
        builder.append("exception:").append("\r\n");
        builder.append(exceptionInformation);
        return builder;
    }


    private Map<String, String> getPackageInformation(Context mContext) {
        Map<String, String> packageInformation = new HashMap<>();
        PackageManager packageManager = mContext.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);

            packageInformation.put("versionCode", String.valueOf(packageInfo.versionCode));
            packageInformation.put("packageName", packageInfo.packageName);
            packageInformation.put("versionName", packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            //ignore
        }


        return packageInformation;
    }

    private String getExceptionInformation(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter printWriter = new PrintWriter(sw);
        e.printStackTrace(printWriter);
        return sw.toString();

    }

    private Map<String, String> getBuildInformation() {
        Map<String, String> mobileInformation = new HashMap<>();
        mobileInformation.put("model", Build.MODEL);
        mobileInformation.put("brand", Build.BRAND);
        mobileInformation.put("manufacturer", Build.MANUFACTURER);
        mobileInformation.put("product", Build.PRODUCT);
        mobileInformation.put("cpu_abi", Build.CPU_ABI + "," + Build.CPU_ABI2);
        mobileInformation.put("sdk", String.valueOf(Build.VERSION.SDK_INT));

        return mobileInformation;
    }

    private String generalFileName() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String name = simpleDateFormat.format(Calendar.getInstance().getTime());
        return name + ".txt";
    }
}
