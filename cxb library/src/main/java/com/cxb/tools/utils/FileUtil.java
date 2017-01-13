package com.cxb.tools.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by meimarco on 15-8-1.
 */
public class FileUtil {
    /**
     * 写文本文件 在Android系统中，文件保存在 /data/data/PACKAGE_NAME/files 目录下
     */
    public static void write(Context context, String fileName, String content) {
        if (content == null)
            content = "";

        try {
            FileOutputStream fos = context.openFileOutput(fileName,
                    Context.MODE_PRIVATE);
            fos.write(content.getBytes());

            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文本文件
     */
    public static String read(Context context, String fileName) {
        try {
            FileInputStream in = context.openFileInput(fileName);
            return readInStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String readInStream(FileInputStream inStream) {
        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[512];
            int length = -1;
            while ((length = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, length);
            }

            outStream.close();
            inStream.close();
            return outStream.toString();
        } catch (IOException e) {
            Log.i("FileTest", e.getMessage());
        }
        return null;
    }

    public static File createFile(String folderPath, String fileName) {
        File destDir = new File(folderPath);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        return new File(folderPath, fileName + fileName);
    }

    /**
     * 向手机写图片
     */
    public static boolean writeFile(byte[] buffer, String folder,
                                    String fileName) {
        boolean writeSucc = false;

        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);

        String folderPath = "";
        if (sdCardExist) {
            folderPath = Environment.getExternalStorageDirectory()
                    + File.separator + folder + File.separator;
        } else {
            writeSucc = false;
        }

        File fileDir = new File(folderPath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        File file = new File(folderPath + fileName);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(buffer);
            writeSucc = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return writeSucc;
    }

    /**
     * 根据文件绝对路径获取文件名
     */
    public static String getFileName(String filePath) {
        if (StringUtil.isEmpty(filePath))
            return "";
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
    }

    /**
     * 根据文件的绝对路径获取文件名但不包含扩展名
     */
    public static String getFileNameNoFormat(String filePath) {
        if (StringUtil.isEmpty(filePath)) {
            return "";
        }
        int point = filePath.lastIndexOf('.');
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1,
                point);
    }

    /**
     * 获取文件扩展名
     */
    public static String getFileFormat(String fileName) {
        if (StringUtil.isEmpty(fileName))
            return "";

        int point = fileName.lastIndexOf('.');
        return fileName.substring(point + 1);
    }

    /**
     * 获取文件大小
     */
    public static long getFileSize(String filePath) {
        long size = 0;

        File file = new File(filePath);
        if (file != null && file.exists()) {
            size = file.length();
        }
        return size;
    }

    /**
     * 获取文件大小
     *
     * @param size 字节
     */
    public static String getFileSize(long size) {
        if (size <= 0)
            return "0";
        java.text.DecimalFormat df = new java.text.DecimalFormat("##.##");
        float temp = (float) size / 1024;
        if (temp >= 1024) {
            return df.format(temp / 1024) + "M";
        } else {
            return df.format(temp) + "K";
        }
    }

    /**
     * 获取目录文件大小
     *
     * @param dir
     * @return
     */
    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file); // 递归调用继续统计
            }
        }
        return dirSize;
    }

    /**
     * 获取目录文件个数
     */
    public long getFileList(File dir) {
        long count = 0;
        File[] files = dir.listFiles();
        count = files.length;
        for (File file : files) {
            if (file.isDirectory()) {
                count = count + getFileList(file);// 递归
                count--;
            }
        }
        return count;
    }

    public static byte[] toBytes(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int ch;
        while ((ch = in.read()) != -1) {
            out.write(ch);
        }
        byte buffer[] = out.toByteArray();
        out.close();
        return buffer;
    }

    /**
     * 检查文件是否存在
     */
    public static boolean checkFileExists(String name) {
        boolean status;
        if (!name.equals("")) {
            File path = Environment.getExternalStorageDirectory();
            File newPath = new File(path.toString() + name);
            status = newPath.exists();
        } else {
            status = false;
        }
        return status;

    }

    /**
     * 计算SD卡的剩余空间
     *
     * @return 返回-1，说明没有安装sd卡
     */
    public static long getFreeDiskSpace() {
        String status = Environment.getExternalStorageState();
        long freeSpace = 0;
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            try {
                File path = Environment.getExternalStorageDirectory();
                StatFs stat = new StatFs(path.getPath());
                long blockSize = stat.getBlockSize();
                long availableBlocks = stat.getAvailableBlocks();
                freeSpace = availableBlocks * blockSize / 1024;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return -1;
        }
        return (freeSpace);
    }

    /**
     * 新建目录
     */
    public static boolean createDirectory(String directoryName) {
        boolean status;
        if (!directoryName.equals("")) {
            File path = Environment.getExternalStorageDirectory();
            File newPath = new File(path.toString() + directoryName);
            status = newPath.mkdir();
            status = true;
        } else
            status = false;
        return status;
    }

    /**
     * 检查是否安装SD卡
     */
    public static boolean checkSaveLocationExists() {
        String sDCardStatus = Environment.getExternalStorageState();
        boolean status;
        if (sDCardStatus.equals(Environment.MEDIA_MOUNTED)) {
            status = true;
        } else
            status = false;
        return status;
    }

    /**
     * 删除目录(包括：目录里的所有文件)
     */
    public static boolean deleteDirectory(String fileName) {
        boolean status;
        SecurityManager checker = new SecurityManager();

        if (!fileName.equals("")) {

            File path = Environment.getExternalStorageDirectory();
            File newPath = new File(path.toString() + fileName);
            checker.checkDelete(newPath.toString());
            if (newPath.isDirectory()) {
                String[] listfile = newPath.list();
                // delete all files within the specified directory and then
                // delete the directory
                try {
                    for (int i = 0; i < listfile.length; i++) {
                        File deletedFile = new File(newPath.toString() + "/"
                                + listfile[i].toString());
                        deletedFile.delete();
                    }
                    newPath.delete();
//                    Log.i("DirectoryManager deleteDirectory", fileName);
                    status = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    status = false;
                }

            } else
                status = false;
        } else
            status = false;
        return status;
    }

    /**
     * 删除文件
     */
    public static boolean deleteFile(String fileName) {
        boolean status;
        SecurityManager checker = new SecurityManager();

        if (!fileName.equals("")) {

            File path = Environment.getExternalStorageDirectory();
            File newPath = new File(path.toString() + fileName);
            checker.checkDelete(newPath.toString());
            if (newPath.isFile()) {
                try {
//                    Log.i("DirectoryManager deleteFile", fileName);
                    newPath.delete();
                    status = true;
                } catch (SecurityException se) {
                    se.printStackTrace();
                    status = false;
                }
            } else
                status = false;
        } else
            status = false;
        return status;
    }

    /**
     * 得到本地或者网络上的bitmap url - 网络或者本地图片的绝对路径,比如:
     * <p/>
     * A.网络路径: url="http://blog.foreverlove.us/girl2.png" ;
     * <p/>
     * B.本地路径:url="file://mnt/sdcard/photo/image.png";
     * <p/>
     * C.支持的图片格式 ,png, jpg,bmp,gif等等
     */
    public static Bitmap GetLocalOrNetBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            //2*1024为常量
            in = new BufferedInputStream(new URL(url).openStream(), 2 * 1024);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 2 * 1024);
            copy(in, out);
            out.flush();
            byte[] data = dataStream.toByteArray();
            try {
                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            } catch (OutOfMemoryError error) {
                data = null;
                return null;
            }
            data = null;
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap GetNetBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            URL myUrl = new URL(url);
            URLConnection myurlcon = myUrl.openConnection();
            myurlcon.setConnectTimeout(5000);
            myurlcon.setReadTimeout(5000);
            //2*1024为常量
            in = new BufferedInputStream(myurlcon.getInputStream(), 2 * 1024);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 2 * 1024);
            copy(in, out);
            out.flush();
            byte[] data = dataStream.toByteArray();
            try {
                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            } catch (OutOfMemoryError error) {
                data = null;
                return null;
            }
            data = null;
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] b = new byte[2 * 1024];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }


    /*
    * 把Bitmap转换成jpg图片
    * */
    public static File saveBitmapToJpg(Bitmap bitmap,String path, String bitName,int size) {
        File file = new File(path);

        try {
            if (!file.exists()) {
                file.mkdir();
            }

            file = new File(path + bitName);

            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, size, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    //根据content://media/external/images/media/***获取真实地址
    public static String getContentImage(String url, Context context) {
        Uri uri = Uri.parse(url);
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = ((Activity)context).managedQuery(uri, proj, null, null, null);
        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor.getString(actual_image_column_index);
        return img_path;
    }


    /**
     * 保存裁剪之后的图片数据
     */
    private Bitmap bigBitmap = null;

    private Canvas bigCanvas;

//    public static Bitmap setPicToView(Bitmap photo, int photoWidth) {
//
//        float scale = 1;
//        if (photo.getWidth() > photoWidth
//                || photo.getHeight() > photoWidth) {
//            if (photo.getWidth() < photo.getHeight()) {
//                scale = (float) photoWidth / photo.getHeight();
//
//            } else {
//                scale = (float) photoWidth / photo.getWidth();
//            }
//        } else {
//            if (photo.getWidth() < photo.getHeight()) {
//                scale = (float) photoWidth / photo.getHeight();
//
//            } else {
//                scale = (float) photoWidth / photo.getHeight();
//            }
//        }
//
//        int width = (int) (photo.getWidth() * scale);
//        int height = (int) (photo.getHeight() * scale);
//
//        bigBitmap = Bitmap.createBitmap(width, height,
//                Bitmap.Config.ARGB_8888);
//
//        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//
//        bigCanvas = new Canvas(bigBitmap);
//        bigCanvas.drawColor(Color.WHITE);
//        bigCanvas.save();
//        bigCanvas.scale(scale, scale,
//                (bigBitmap.getWidth() - photo.getWidth()) / 2
//                        + photo.getWidth() / 2,
//                (bigBitmap.getHeight() - photo.getHeight())
//                        / 2 + photo.getHeight() / 2);
//        bigCanvas.drawBitmap(
//                photo,
//                (bigBitmap.getWidth() - photo.getWidth()) / 2,
//                (bigBitmap.getHeight() - photo.getHeight()) / 2,
//                paint);
//        bigCanvas.restore();
//
//        return bigBitmap;
//    }

    /**
     * 1.加载位图
     * 2.为位图设置100K的缓存
     * 3.设置位图颜色显示优化方式
     * ALPHA_8：每个像素占用1byte内存（8位）
     * ARGB_4444:每个像素占用2byte内存（16位）
     * ARGB_8888:每个像素占用4byte内存（32位）
     * RGB_565:每个像素占用2byte内存（16位）
     * Android默认的颜色模式为ARGB_8888，这个颜色模式色彩最细腻，显示质量最高。
     * 但同样的，占用的内存也最大。也就意味着一个像素点占用4个字节的内存。
     * 我们来做一个简单的计算题：3200*2400*4 bytes =30M。如此惊人的数字！
     * 哪怕生命周期超不过10s，Android也不会答应的。
     * 4.设置图片可以被回收，创建Bitmap用于存储Pixel的内存空间在系统内存不足时可以被回收
     * 5.设置位图缩放比例
     * width，hight设为原来的四分一（该参数请使用2的整数倍）,这也减小了位图占用的内存大小；例如，一张
     * 分辨率为2048*1536px的图像使用inSampleSize值为4的设置来解码，产生的Bitmap大小约为
     * 512*384px。相较于完整图片占用12M的内存，这种方式只需0.75M内存(假设Bitmap配置为ARGB_8888)。
     * 6.设置解码位图的尺寸信息
     * 7.解码位图
     */
    public static Bitmap setPicToView(String path,int width,int height) {

        float scale = 1;
        File file = new File(path);

        if (file.exists()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            int outHeight = options.outHeight;
            int outWidth = options.outWidth;

            if(outHeight > height && outWidth > width){
                float s1 = (float) width / (float) height;
                float s2 = (float) outWidth / (float) outHeight;
                if(s2 > s1){
                    if(width > outWidth){
                        scale = (float) width / (float) outWidth;
                    }else{
                        scale = (float) outWidth / (float) width;
                    }
                }else{
                    if(height > outHeight){
                        scale = (float) height / (float) outHeight;
                    }else{
                        scale = (float) outHeight / (float) height;
                    }
                }
            }

            options.outHeight = (int) (outHeight / scale);
            options.outWidth = (int) (outWidth / scale);
            options.inSampleSize = (int) (scale + 0.5);
            options.inJustDecodeBounds = false;
            options.inDither = false;//不进行图片抖动处理
            options.inPreferredConfig = null;//设置让解码器以最佳方式解码
//            options.inPurgeable = true;
//            options.inInputShareable = true;
            Bitmap photo = BitmapFactory.decodeFile(path, options);
            return photo;
        }
        return null;
    }

    public static Bitmap setPicToView(String path,int size) {

        File file = new File(path);

        if (file.exists()) {
            String p = file.getAbsolutePath();
            InputStream is = null;
            try {
                is = new FileInputStream(p);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inTempStorage = new byte[16 * 1024];
            opts.inPreferredConfig = Bitmap.Config.ALPHA_8;
            opts.inPurgeable = true;
            opts.inSampleSize = size;
            opts.inInputShareable = true;
            Bitmap photo = BitmapFactory.decodeStream(is, null, opts);
            return photo;
        }
        return null;
    }

    public static Bitmap setPicToView(Bitmap bitmap, int maxWidth) {
        int size = 1;
        InputStream is = null;
        is = Bitmap2IS(bitmap);
        BitmapFactory.Options opts = new BitmapFactory.Options();
//        float tempSize = Math.max(bitmap.getWidth(),bitmap.getHeight()) / maxWidth;
//        if(tempSize > 1.4){
//            size = 2;
//        }
        if(Math.max(bitmap.getWidth(),bitmap.getHeight()) > maxWidth){
            size = 2;
        }
        opts.inTempStorage = new byte[100 * 1024];
        opts.inPreferredConfig = Bitmap.Config.ALPHA_8;
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        opts.inSampleSize = size;
        Bitmap photo = BitmapFactory.decodeStream(is, null, opts);
        return photo;
    }

    public static InputStream Bitmap2IS(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        InputStream sbs = new ByteArrayInputStream(baos.toByteArray());
        return sbs;
    }

    //使用BitmapFactory.Options的inSampleSize参数来缩放
    public static Bitmap resizeImage2(Bitmap bitmap, int width,int height)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//不加载bitmap到内存中
//        BitmapFactory.decodeFile(path,options);
        BitmapFactory.decodeStream(Bitmap2IS(bitmap));
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        options.inDither = false;
        options.inPreferredConfig = Bitmap.Config.ALPHA_8;
        options.inSampleSize = 1;

        if (outWidth != 0 && outHeight != 0 && width != 0 && height != 0)
        {
            int sampleSize=(outWidth/width+outHeight/height)/2;

            options.inSampleSize = sampleSize;
        }

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(Bitmap2IS(bitmap),null,options);
    }

    //使用Bitmap加Matrix来缩放
    public static Bitmap resizeImage(Bitmap bitmap, int newWidth, int newHeight)
    {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,
                height, matrix, true);

        if(bitmap != null){
            bitmap.recycle();
            bitmap = null;
            System.gc();
        }

        return resizedBitmap;
    }

    /**
     * 获取图片信息
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 图片旋转
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
//        System.out.println("angle=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }
}
