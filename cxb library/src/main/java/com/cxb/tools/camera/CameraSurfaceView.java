package com.cxb.tools.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import com.cxb.tools.utils.SDCardUtil;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 相机Preview控件
 */

public class CameraSurfaceView extends SurfaceView {

    private Context mContext;
    private SurfaceHolder mSurfaceHolder;

    private int mCameraCount;
    private int mCurrentCameraFacing = Camera.CameraInfo.CAMERA_FACING_BACK;//后置摄像头
    private Camera mCamera;

    private OnSavePictureListener mOnSavePictureListener;

    public CameraSurfaceView(Context context) {
        this(context, null);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        safeCameraOpen();
        mCameraCount = Camera.getNumberOfCameras();
        mSurfaceHolder = getHolder();
        mSurfaceHolder.setKeepScreenOn(true);
        mSurfaceHolder.addCallback(surfaceCallback);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCamera != null) {
                    mCamera.autoFocus(null);
                }
            }
        });
    }

    //安全打开摄像机
    private void safeCameraOpen() {
        try {
            stopPreviewAndFreeCamera();
            mCamera = Camera.open(mCurrentCameraFacing);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopPreviewAndFreeCamera() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    private SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Logger.t("surfaceCreated").d("");
            safeCameraOpen();
            startPreview(holder);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Logger.t("surfaceChanged").d("width = " + width + "\nheight = " + height + "\ngetWidth() = " + getWidth() + "\ngetHeight() = " + getHeight());
            Camera.Parameters parameters = mCamera.getParameters();
            List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
            List<Camera.Size> pictureSizes = parameters.getSupportedPictureSizes();
            Camera.Size mPreviewSize = getOptimalPreviewSize(previewSizes, getWidth(), getHeight());
            parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
            Camera.Size mPictureSize = pictureSizes.get(1);
            parameters.setPictureSize(mPictureSize.width, mPictureSize.height);
            mCamera.setParameters(parameters);
            mCamera.startPreview();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Logger.t("surfaceDestroyed").d("");
            if (mCamera != null) {
                mCamera.stopPreview();
            }
        }
    };

    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(final byte[] data, Camera camera) {
            try {
                saveToSDCard(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
            safeCameraOpen();
            startPreview(mSurfaceHolder);
        }
    };

    private void startPreview(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            setCameraDisplayOrientation();
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onResume() {
        safeCameraOpen();
    }

    public void onPause() {
        stopPreviewAndFreeCamera();
    }

    public void changCameraFacing() {
        if (mCameraCount > 1) {
            mCurrentCameraFacing = (mCurrentCameraFacing == Camera.CameraInfo.CAMERA_FACING_BACK) ?
                    Camera.CameraInfo.CAMERA_FACING_FRONT : Camera.CameraInfo.CAMERA_FACING_BACK;
            safeCameraOpen();
            startPreview(mSurfaceHolder);
        } else {
            //手机不支持前置摄像头
        }
    }

    public void takePicture() {
        if (mCamera != null) {
            mCamera.takePicture(null, null, pictureCallback);
        } else {
            //TODO: 提示用户
        }
    }

    private void saveToSDCard(byte[] data) throws IOException {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()); // 格式化时间
        String filename = format.format(date) + ".jpg";

        File fileFolder = new File(SDCardUtil.getAutoCachePath(mContext));

        File jpgFile = new File(fileFolder, filename);
        FileOutputStream outputStream = new FileOutputStream(jpgFile); // 文件输出流

//        outputStream.write(data);
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        outputStream.flush(); // 写入sd卡中
        outputStream.close(); // 关闭输出流
        String savePath = jpgFile.getAbsolutePath();

        Logger.d("####################################");
//        if (jpgFile.exists()) {
//            if (mOnSavePictureListener != null) {
//                mOnSavePictureListener.onSuccess(savePath);
//            }
//        }
    }

    public void setCameraDisplayOrientation() {
        Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        Camera.getCameraInfo(mCurrentCameraFacing, info);
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int rotation = windowManager.getDefaultDisplay().getRotation();
        int degrees = 0;
        if (rotation == Surface.ROTATION_0) {
            degrees = 0;
        } else if (rotation == Surface.ROTATION_90) {
            degrees = 90;
        } else if (rotation == Surface.ROTATION_180) {
            degrees = 180;
        } else if (rotation == Surface.ROTATION_270) {
            degrees = 270;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;
        } else {
            result = (info.orientation - degrees + 360) % 360;
        }
        mCamera.setDisplayOrientation(result);
    }

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) w / h;
        if (sizes == null) return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        // Try to find an size match aspect ratio and size
        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    public void setOnSavePictureListener(OnSavePictureListener onSavePictureListener) {
        mOnSavePictureListener = onSavePictureListener;
    }

    public interface OnSavePictureListener {
        void onSuccess(String filePath);
    }
}
