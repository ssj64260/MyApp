package com.cxb.tools.camera;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CameraUtil {
    private static final String TAG = "yanzi";
    private CameraSizeComparator sizeComparator = new CameraSizeComparator();
    private static CameraUtil myCamPara = null;

    private static final File parentPath = Environment.getExternalStorageDirectory();
    private static String storagePath = "";
    private static final String DST_FOLDER_NAME = "PlayCamera";

    private CameraUtil() {

    }

    public static CameraUtil getInstance() {
        if (myCamPara == null) {
            myCamPara = new CameraUtil();
        }
        return myCamPara;
    }

    public Size getPropSize(List<Camera.Size> sizes, float rate, int minWidth) {
        Collections.sort(sizes, sizeComparator);

        for (Size size : sizes) {
            if ((size.width >= minWidth) && equalRate(size, rate)) {
                Log.i(TAG, "Size:w = " + size.width + "h = " + size.height);
                return size;
            }
        }
        return sizes.get(0);
    }

    public boolean equalRate(Size size, float rate) {
        float r = (float) (size.width) / (float) (size.height);
        if (Math.abs(r - rate) <= 0.03) {
            return true;
        } else {
            return false;
        }
    }

    public class CameraSizeComparator implements Comparator<Camera.Size> {
        public int compare(Size lhs, Size rhs) {
            // TODO Auto-generated method stub
            if (lhs.width == rhs.width) {
                return 0;
            } else if (lhs.width > rhs.width) {
                return 1;
            } else {
                return -1;
            }
        }

    }

    /**
     * 打印支持的previewSizes
     *
     * @param params
     */
    public void printSupportPreviewSize(Camera.Parameters params) {
        List<Size> previewSizes = params.getSupportedPreviewSizes();
        for (int i = 0; i < previewSizes.size(); i++) {
            Size size = previewSizes.get(i);
            Log.i(TAG, "previewSizes:width = " + size.width + " height = " + size.height);
        }

    }

    /**
     * 打印支持的pictureSizes
     *
     * @param params
     */
    public void printSupportPictureSize(Camera.Parameters params) {
        List<Size> pictureSizes = params.getSupportedPictureSizes();
        for (int i = 0; i < pictureSizes.size(); i++) {
            Size size = pictureSizes.get(i);
            Log.i(TAG, "pictureSizes:width = " + size.width
                    + " height = " + size.height);
        }
    }

    /**
     * 打印支持的聚焦模式
     *
     * @param params
     */
    public void printSupportFocusMode(Camera.Parameters params) {
        List<String> focusModes = params.getSupportedFocusModes();
        for (String mode : focusModes) {
            Log.i(TAG, "focusModes--" + mode);
        }
    }

    /**
     * 初始化保存路径
     *
     * @return
     */
    public String initPath() {
        if (storagePath.equals("")) {
            storagePath = parentPath.getAbsolutePath() + "/" + DST_FOLDER_NAME;
            File f = new File(storagePath);
            if (!f.exists()) {
                f.mkdir();
            }
        }
        return storagePath;
    }

    /**
     * 保存Bitmap到sdcard
     *
     * @param b
     */
    public void saveBitmap(Bitmap b) {

        String path = initPath();
        long dataTake = System.currentTimeMillis();
        String jpegName = path + File.separator + "Camera_" + dataTake + ".jpg";
        Log.i(TAG, "saveBitmap:jpegName = " + jpegName);
        try {
            FileOutputStream fout = new FileOutputStream(jpegName);
            BufferedOutputStream bos = new BufferedOutputStream(fout);
            b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            Log.i(TAG, "saveBitmap成功");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.i(TAG, "saveBitmap:失败");
            e.printStackTrace();
        }
    }

    /**
     * 旋转Bitmap
     *
     * @param b
     * @param rotateDegree
     * @return
     */
    public Bitmap getRotateBitmap(Bitmap b, float rotateDegree) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) rotateDegree);
        Bitmap rotaBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, false);
        return rotaBitmap;
    }
}
