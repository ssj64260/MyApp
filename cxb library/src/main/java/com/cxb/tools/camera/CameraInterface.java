package com.cxb.tools.camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.List;

public class CameraInterface {

    private Camera mCamera;
    private Camera.Parameters mParams;
    private boolean isPreviewing = false;
    private static CameraInterface mCameraInterface;
    private int mCameraCount;//摄像头数量
    private int mCurrentCameraFacing = Camera.CameraInfo.CAMERA_FACING_BACK;//后置摄像头

    public interface CamOpenOverCallback {
        public void cameraHasOpened();
    }

    private CameraInterface() {
        mCameraCount = Camera.getNumberOfCameras();
    }

    public static synchronized CameraInterface getInstance() {
        if (mCameraInterface == null) {
            mCameraInterface = new CameraInterface();
        }
        return mCameraInterface;
    }

    private void initCamera() {
        if (mCamera != null) {
            mParams = mCamera.getParameters();
            mParams.setPictureFormat(PixelFormat.JPEG);//设置拍照后存储的图片格式
//          CamParaUtil.getInstance().printSupportPictureSize(mParams);
//          CamParaUtil.getInstance().printSupportPreviewSize(mParams);
            //设置PreviewSize和PictureSize
//            Size pictureSize = CamParaUtil.getInstance().getPropPictureSize(
//                    mParams.getSupportedPictureSizes(), 1.777f, 1920);
            List<Camera.Size> pictureSizes = mParams.getSupportedPictureSizes();
            Camera.Size pictureSize = pictureSizes.get(1);
            mParams.setPictureSize(pictureSize.width, pictureSize.height);
            Size previewSize = CamParaUtil.getInstance().getPropPreviewSize(
                    mParams.getSupportedPreviewSizes(), 1.777f, 1920);
            mParams.setPreviewSize(previewSize.width, previewSize.height);

            mCamera.setDisplayOrientation(90);

//          CamParaUtil.getInstance().printSupportFocusMode(mParams);
            List<String> focusModes = mParams.getSupportedFocusModes();
            if (focusModes.contains("continuous-video")) {
                mParams.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            }

            mParams.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);//一开始关闭闪关灯

            mCamera.setParameters(mParams);
            mCamera.startPreview();//开启预览

            isPreviewing = true;

            mParams = mCamera.getParameters(); //重新get一次

            StringBuilder sb = new StringBuilder();
            sb.append("最终设置:\nPreviewSize--With = ").append(mParams.getPreviewSize().width);
            sb.append("\nHeight = ").append(mParams.getPreviewSize().height);
            sb.append("\n\n最终设置:\nPictureSize--With = ").append(mParams.getPictureSize().width);
            sb.append("\nHeight = ").append(mParams.getPictureSize().height);

            Logger.d(sb.toString());
        }
    }

    //打开Camera
    public void doOpenCamera(CamOpenOverCallback callback) {
        mCamera = Camera.open(mCurrentCameraFacing);
        callback.cameraHasOpened();
    }

    //使用TextureView预览Camera
    public void doStartPreview(SurfaceTexture surface) {
        if (isPreviewing) {
            mCamera.stopPreview();
            return;
        }
        if (mCamera != null) {
            try {
                mCamera.setPreviewTexture(surface);
                initCamera();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //停止预览，释放Camera
    public void doStopCamera() {
        if (null != mCamera) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            isPreviewing = false;
            mCamera.release();
            mCamera = null;
        }
    }

    //拍照
    public void doTakePicture() {
        if (isPreviewing && (mCamera != null)) {
            mCamera.takePicture(mShutterCallback, null, mJpegPictureCallback);
        }
    }

    public void changCameraFacing(final SurfaceTexture surface) {
        if (mCameraCount > 1) {
            mCurrentCameraFacing = (mCurrentCameraFacing == Camera.CameraInfo.CAMERA_FACING_BACK) ?
                    Camera.CameraInfo.CAMERA_FACING_FRONT : Camera.CameraInfo.CAMERA_FACING_BACK;
            doStopCamera();
            doOpenCamera(new CamOpenOverCallback() {
                @Override
                public void cameraHasOpened() {
                    doStartPreview(surface);
                }
            });
        } else {
            //手机不支持前置摄像头
        }
    }

    public String turnLight() {
        if (mCamera == null) {
            return "";
        }
        Camera.Parameters parameters = mCamera.getParameters();
        if (parameters == null) {
            return "";
        }
        String flashMode = parameters.getFlashMode();

        if (Camera.Parameters.FLASH_MODE_OFF.equals(flashMode)) {
            flashMode = Camera.Parameters.FLASH_MODE_AUTO;
        } else if (Camera.Parameters.FLASH_MODE_AUTO.equals(flashMode)) {
            flashMode = Camera.Parameters.FLASH_MODE_ON;
        } else if (Camera.Parameters.FLASH_MODE_ON.equals(flashMode)) {
            flashMode = Camera.Parameters.FLASH_MODE_OFF;
        }

        parameters.setFlashMode(flashMode);
        mCamera.setParameters(parameters);
        mCamera.startPreview();

        return flashMode;
    }

    public void setAutoFocus(){
        if (mCamera != null) {
            mCamera.autoFocus(null);
        }
    }

    /*为了实现拍照的快门声音及拍照保存照片需要下面三个回调变量*/
    ShutterCallback mShutterCallback = new ShutterCallback() {
        public void onShutter() {
            Logger.d("快门按下的回调，在这里我们可以设置类似播放“咔嚓”声之类的操作。默认的就是咔嚓。");
        }
    };
    PictureCallback mRawCallback = new PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            Logger.d("拍摄的未压缩原数据的回调,可以为null");
        }
    };
    PictureCallback mJpegPictureCallback = new PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            //对jpeg图像数据的回调,最重要的一个回调
            Bitmap b = null;
            if (null != data) {
                b = BitmapFactory.decodeByteArray(data, 0, data.length);//data是字节数据，将其解析成位图
                mCamera.stopPreview();
                isPreviewing = false;
            }
            //保存图片到sdcard
            if (null != b) {
                //设置FOCUS_MODE_CONTINUOUS_VIDEO)之后，myParam.set("rotation", 90)失效。
                //图片竟然不能旋转了，故这里要旋转下
                Bitmap rotaBitmap = ImageUtil.getRotateBitmap(b, 90.0f);
                FileUtil.saveBitmap(rotaBitmap);
            }
            //再次进入预览
            mCamera.startPreview();
            isPreviewing = true;
        }
    };
}
