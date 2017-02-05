package com.cxb.tools.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * 图片旋转处理工具类
 */

public class ImageUtil {

    /**
     * 获取图片角度
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
        // 创建新的图片
        return Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
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
        Bitmap bitmap;
        InputStream in;
        BufferedOutputStream out;
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
                return null;
            }
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


    //把Bitmap转换成jpg图片
    public static File saveBitmapToJpg(Bitmap bitmap, String path, String bitName, int size) {
        File file = new File(path);

        try {
            if (!file.exists()) {
                file.mkdir();
            }

            file = new File(path, bitName);

//            if (!file.exists()) {
//                file.createNewFile();
//            }

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
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            int actual_image_column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(actual_image_column_index);
        } else {
            return url;
        }
    }

    public static String getContentContactAvatar(String url, Context context){
        Uri uri = Uri.parse(url);
        String[] proj = {ContactsContract.Contacts.PHOTO_THUMBNAIL_URI};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            int index = cursor.getColumnIndexOrThrow(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI);
            cursor.moveToFirst();
            return cursor.getString(index);
        } else {
            return url;
        }
    }

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
    public static Bitmap setPicToView(String path, int width, int height) {

        float scale = 1;
        File file = new File(path);

        if (file.exists()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            int outHeight = options.outHeight;
            int outWidth = options.outWidth;

            if (outHeight > height && outWidth > width) {
                float s1 = (float) width / (float) height;
                float s2 = (float) outWidth / (float) outHeight;
                if (s2 > s1) {
                    if (width > outWidth) {
                        scale = (float) width / (float) outWidth;
                    } else {
                        scale = (float) outWidth / (float) width;
                    }
                } else {
                    if (height > outHeight) {
                        scale = (float) height / (float) outHeight;
                    } else {
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
            return BitmapFactory.decodeFile(path, options);
        }
        return null;
    }

    private static InputStream Bitmap2IS(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    //使用BitmapFactory.Options的inSampleSize参数来缩放
    public static Bitmap resizeImage2(Bitmap bitmap, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//不加载bitmap到内存中
//        BitmapFactory.decodeFile(path,options);
        BitmapFactory.decodeStream(Bitmap2IS(bitmap));
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        options.inDither = false;
        options.inPreferredConfig = Bitmap.Config.ALPHA_8;
        options.inSampleSize = 1;

        if (outWidth != 0 && outHeight != 0 && width != 0 && height != 0) {
            options.inSampleSize = (outWidth / width + outHeight / height) / 2;
        }

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(Bitmap2IS(bitmap), null, options);
    }

    //使用Bitmap加Matrix来缩放
    public static Bitmap resizeImage(Bitmap bitmap, int newWidth, int newHeight) {
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

        bitmap.recycle();

        return resizedBitmap;
    }

}
