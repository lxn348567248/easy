package me.liuning.core.hotfix;

import android.content.Context;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.DexClassLoader;
import me.liuning.core.log.LogUtils;
import me.liuning.core.util.IOUtls;
import me.liuning.core.util.ReflectUtils;

public class HotFixManager {


    private static final String DEX = "dex";
    public static final String OPTIMIZED = "opt_out";
    private static HotFixManager sInstance = new HotFixManager();


    private Context mContext;
    private volatile boolean init;
    private File mDexPathFile;
    private File mOptimizedDirectoryFile;


    private HotFixManager() {

    }

    public static HotFixManager getInstance() {
        return sInstance;

    }

    public void init(Context context) {
        if (init) {
            return;
        }
        init = true;
        LogUtils.debug("init");
        mContext = context.getApplicationContext();
        mDexPathFile = mContext.getDir(DEX, Context.MODE_PRIVATE);
        LogUtils.debug("path is :"+mDexPathFile);
        mOptimizedDirectoryFile = new File(mDexPathFile.getAbsolutePath(), OPTIMIZED);
        if (!mOptimizedDirectoryFile.exists()) {
            mOptimizedDirectoryFile.mkdirs();
        }
    }


    public void loadPatch(String pathFilePath) throws IOException {
        File srcFile = new File(pathFilePath);
        if (!srcFile.exists()) {
            throw new FileNotFoundException(pathFilePath);
        }
        File destFile = new File(mDexPathFile, srcFile.getName());
        if (destFile.exists()) {
            LogUtils.error("patch [" + pathFilePath + "] has be loaded.");
            return;
        }
        IOUtls.copyFile(srcFile, destFile);
        List<File> dexFiles = new ArrayList<>();
        dexFiles.add(destFile);
        loadDexFiles(dexFiles.toArray(new File[0]));
    }


    public void loadPatch() {

        File[] dexFiles = mDexPathFile.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile() && pathname.getName().endsWith(".dex");
            }
        });

        if (dexFiles == null || dexFiles.length == 0) {
            LogUtils.error("dexFiles is empty ");
            return;
        }

        loadDexFiles(dexFiles);


    }

    private void loadDexFiles(File[] dexFiles) {

        ClassLoader classLoader = mContext.getClassLoader();
        Object dexElements = getDexElements(classLoader);

        // merge value
        for (File dexFile : dexFiles) {
            DexClassLoader dexClassLoader = new DexClassLoader(dexFile.getPath(), mOptimizedDirectoryFile.getAbsolutePath(), null, classLoader);
            dexElements = combineElements(getDexElements(dexClassLoader), dexElements);
        }

        //update dexElements
        setDexElements(classLoader, dexElements);

    }

    private Object combineElements(Object left, Object right) {
        int leftSize = Array.getLength(left);
        int rightSize = Array.getLength(right);
        int totalSize = leftSize + rightSize;
        Object result = Array.newInstance(left.getClass().getComponentType(), totalSize);
        for (int index = 0; index < totalSize; index++) {
            if (index < leftSize) {
                Array.set(result, index, Array.get(left, index));
            } else {
                Array.set(result, index, Array.get(right, index - leftSize));
            }
        }

        return result;

    }

    private Object getDexElements(ClassLoader loader) {
        Object pathListValue = ReflectUtils.getFieldValue(loader, "pathList");
        Object dexElementsValue = ReflectUtils.getFieldValue(pathListValue, "dexElements");
        return dexElementsValue;
    }

    private void setDexElements(ClassLoader loader, Object dexElementsValue) {
        Object pathListValue = ReflectUtils.getFieldValue(loader, "pathList");
        ReflectUtils.setFieldValue(pathListValue, "dexElements", dexElementsValue);
    }


}
