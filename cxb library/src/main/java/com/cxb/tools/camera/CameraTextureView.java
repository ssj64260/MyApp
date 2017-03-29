package com.cxb.tools.camera;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.View;

import com.cxb.tools.utils.ThreadPoolUtil;
import com.orhanobut.logger.Logger;

/**
 * 相机TextureView
 */

public class CameraTextureView extends TextureView implements TextureView.SurfaceTextureListener {

    private SurfaceTexture mSurface;

    public CameraTextureView(Context context) {
        this(context, null, 0);
    }

    public CameraTextureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setSurfaceTextureListener(this);

        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraInterface.getInstance().setAutoFocus();
            }
        });
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Logger.t("onSurfaceTextureAvailable").d("width = " + width + "\nheight = " + height);
        mSurface = surface;

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        Logger.t("onSurfaceTextureSizeChanged").d("width = " + width + "\nheight = " + height);

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        Logger.t("onSurfaceTextureDestroyed").d("surface");
        CameraInterface.getInstance().doStopCamera();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
//        Logger.t("onSurfaceTextureUpdated").d("surface");
    }

    public void onResume() {
        ThreadPoolUtil.getInstache().singleExecute(new Runnable() {
            @Override
            public void run() {
                CameraInterface.getInstance().doOpenCamera(new CameraInterface.CamOpenOverCallback() {
                    @Override
                    public void cameraHasOpened() {
                        CameraInterface.getInstance().doStartPreview(mSurface);
                    }
                });
            }
        });
    }

    public void onPause() {
        CameraInterface.getInstance().doStopCamera();
        ThreadPoolUtil.getInstache().singleShutDown(0);
    }

    public void takePhoto() {
        CameraInterface.getInstance().doTakePicture();
    }

    public void changCameraFacing() {
        CameraInterface.getInstance().changCameraFacing(mSurface);
    }

    public String changeFlashMode() {
        return CameraInterface.getInstance().turnLight();
    }
}
