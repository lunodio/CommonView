package cn.lunodio.commonview.util;

import static android.os.Environment.MEDIA_MOUNTED;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.math.BigDecimal;
import java.util.Objects;

public class StorageUtil {
    private static final String TAG = StorageUtil.class.getSimpleName();

    /**
     * 获取图片
     *
     * @param context context
     */
    public static void getPhoto(Context context) {
        Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = context.getContentResolver();

        String[] projImage = {MediaStore.Images.Media._ID
                , MediaStore.Images.Media.DATA
                , MediaStore.Images.Media.SIZE
                , MediaStore.Images.Media.DISPLAY_NAME};


        // 只查询jpeg和png的图片 //"image/mp4","image/3gp"
        Cursor mCursor = mContentResolver.query(imageUri, projImage,
                MediaStore.Images.Media.MIME_TYPE + " in(?, ?, ?)",
                new String[]{"image/jpeg", "image/png", "image/jpg"},
                MediaStore.Images.Media.DATE_MODIFIED + " desc");

        int pathIndex = mCursor
                .getColumnIndex(MediaStore.Images.Media.DATA);

        if (mCursor.moveToFirst()) {
            do {    // 获取图片的路径
                String path = mCursor.getString(pathIndex);
                // 获取该图片的父路径名
                File parentFile = new File(path).getParentFile();
                if (parentFile == null) {
                    continue;
                }
                //获取的文件地址
                String dirPath = parentFile.getAbsolutePath();
            } while (mCursor.moveToNext());
        }
        mCursor.close();
    }

    /**
     * 获取视频
     *
     * @param context context
     */
    public static void getVideo(Context context) {

        Uri videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = context.getContentResolver();

        String[] projVideo = {MediaStore.Video.Thumbnails._ID
                , MediaStore.Video.Thumbnails.DATA
                , MediaStore.Video.Media.DURATION
                , MediaStore.Video.Media.SIZE
                , MediaStore.Video.Media.DISPLAY_NAME
                , MediaStore.Video.Media.DATE_MODIFIED};

        Cursor mCursor = mContentResolver.query(videoUri, projVideo,
                MediaStore.Video.Media.MIME_TYPE + " in(?, ?, ?, ?)",
                new String[]{"video/mp4", "video/3gp", "video/avi", "video/rmvb"},
                MediaStore.Video.Media.DATE_MODIFIED + " desc");

        if (mCursor != null) {
            while (mCursor.moveToNext()) {
                // 获取视频的路径
                String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Video.Media.DATA));
                // 获取该视频的父路径名
                File parentFile = new File(path).getParentFile();
                if (parentFile == null) {
                    LogUtil.e(TAG, "父路径不存在");
                } else {
                    String dirPath = parentFile.getAbsolutePath();
                }
            }
            mCursor.close();
        }
    }

    public static boolean isPathExist(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * 查询内容解析器，找到文件存储地址
     * <p>ef: android中转换content://media/external/images/media/539163为/storage/emulated/0/DCIM/Camera/IMG_20160807_123123.jpg
     * <p>把content://media/external/images/media/X转换为file:///storage/sdcard0/Pictures/X.jpg
     *
     * @param context
     * @param contentUri
     * @return
     */
    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            android.util.Log.i(TAG, "getRealPathFromUri: " + contentUri);
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            if (cursor != null && cursor.getColumnCount() > 0) {
                cursor.moveToFirst();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                String path = cursor.getString(column_index);
                android.util.Log.i(TAG, "getRealPathFromUri: column_index=" + column_index + ", path=" + path);
                return path;
            } else {
                android.util.Log.w(TAG, "getRealPathFromUri: invalid cursor=" + cursor + ", contentUri=" + contentUri);
            }
        } catch (Exception e) {
            android.util.Log.e(TAG, "getRealPathFromUri failed: " + e + ", contentUri=" + contentUri, e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return "";
    }

    /**
     * 获取完整文件名(包含扩展名)
     *
     * @param filePath
     * @return
     */
    public static String getFilenameWithExtension(String filePath) {
        if (filePath == null || filePath.length() == 0) {
            return "";
        }
        int lastIndex = filePath.lastIndexOf(File.separator);
        String filename = filePath.substring(lastIndex + 1);
        return filename;
    }

    /**
     * 判断文件路径的文件名是否存在文件扩展名 eg: /external/images/media/2283
     *
     * @param filePath
     * @return
     */
    public static boolean isFilePathWithExtension(String filePath) {
        String filename = getFilenameWithExtension(filePath);
        return filename.contains(".");
    }

    /**
     * 内部与外部的私有目录
     *
     * @param context context
     * @return absolutePath
     */
    public static String getCachePath(Context context) {
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return context.getExternalCacheDir().getAbsolutePath();
        } else {
            return context.getCacheDir().getAbsolutePath();
        }
    }

    /**
     * @param uri 本地路径
     * @return 内容类型，格式："image/jpg"
     */
    public static String getContentType(String uri) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(uri, options);
        return options.outMimeType;
    }

    /**
     * @param uri 路径
     * @return 格式："jpg"
     */
    public static String getFormat(String uri) {
        return getContentType(uri).substring(StorageUtil.getContentType(uri).indexOf('/') + 1);
    }


    /**
     * * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * *
     *
     * @param context context
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }


    /**
     * * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * *
     *
     * @param context context
     */
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/databases"));
    }

    /**
     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) *
     *
     * @param context context
     */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * * 按名字清除本应用数据库 * *
     *
     * @param context context
     * @param dbName
     */
    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * * 清除/data/data/com.xxx.xxx/files下的内容 * *
     *
     * @param context context
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     *
     * @param context context
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     * * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * *
     *
     * @param filePath
     */
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }


    /**
     * * 清除本应用所有的数据 * *
     *
     * @param context  context
     * @param filepath
     */
    public static void cleanApplicationData(Context context, String... filepath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        if (filepath == null) {
            return;
        }
        for (String filePath : filepath) {
            cleanCustomCache(filePath);
        }
    }

    /**
     * * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * *
     *
     * @param directory 目录
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                if (file.delete()) {
                    LogUtil.i(TAG, "成功删除");
                } else {
                    LogUtil.i(TAG, "删除失败");
                }
            }
        }
    }


    /**
     * @param file Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
     *             Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
     * @return 文件大小L
     */
    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] files = file.listFiles();
            if (files != null) {
                for (File value : files) {
                    // 如果下面还有文件
                    if (value.isDirectory()) {
                        size = size + getFolderSize(value);
                    } else {
                        size = size + value.length();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 删除指定目录下文件及目录
     *
     * @param deleteThisPath
     * @param filePath
     * @return
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 如果下面还有文件
                    File[] files = file.listFiles();
                    if (files != null) {
                        for (File value : files) {
                            deleteFolderFile(value.getAbsolutePath(), true);
                        }
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (Objects.requireNonNull(file.listFiles()).length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double KB = size / 1024;
        if (KB < 1) {//小于1KB
            return size + "KB";
        }
        double MB = KB / 1024;
        if (MB < 1) {//大于等于1KB，小于1MB
            BigDecimal result1 = new BigDecimal(Double.toString(KB));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double GB = MB / 1024;
        if (GB < 1) {//大于等于1MB，小于1GB
            BigDecimal result2 = new BigDecimal(Double.toString(MB));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double TB = GB / 1024;
        if (TB < 1) {//大于等于1GB，小于1TB
            BigDecimal result3 = new BigDecimal(Double.toString(GB));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(TB);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    /***
     * 获取应用缓存大小
     * @param file
     * @return
     * @throws Exception
     */

    public static String getCacheSize(File file) throws Exception {
        return getFormatSize(getFolderSize(file));
    }


    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param delFile 要删除的文件夹或文件名
     * @return 删除成功返回true，否则返回false
     */
    public boolean delete(String delFile) {
        File file = new File(delFile);
        if (!file.exists()) {
            LogUtil.e("删除文件失败" + delFile + "不存在");
            return false;
        } else {
            if (file.isFile())
                return deleteSingleFile(delFile);
            else
                return deleteDirectory(delFile);
        }
    }

    /**
     * 删除单个文件
     *
     * @param filePath$Name 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteSingleFile(String filePath$Name) {
        File file = new File(filePath$Name);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                LogUtil.i("成功删除文件" + filePath$Name);
                return true;
            } else {
                LogUtil.e("删除文件失败" + filePath$Name);
                return false;
            }
        } else {
            LogUtil.e("删除文件夹失败,文件夹不存在");
            return false;
        }
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param filePath 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String filePath) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator))
            filePath = filePath + File.separator;
        File dirFile = new File(filePath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            LogUtil.e("不存在路径");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (File file : files) {
            // 删除子文件
            if (file.isFile()) {
                flag = deleteSingleFile(file.getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (file.isDirectory()) {
                flag = deleteDirectory(file
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            LogUtil.i(TAG, "成功删除当前目录");
            return true;
        } else {
            return false;
        }
    }
}
