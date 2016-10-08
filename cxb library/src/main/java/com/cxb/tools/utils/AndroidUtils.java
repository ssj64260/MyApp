package com.cxb.tools.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.lang.ref.WeakReference;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.security.auth.x500.X500Principal;

public class AndroidUtils {

    public static Boolean isEmulator() {
        return "google_sdk".equals(Build.PRODUCT);
    }

    /*public static String getAndroidId() {
        return Secure.getString(MsqFrameworkApp.getInstance().getContentResolver(),
                Secure.ANDROID_ID);
    }

    public static String getVersionName() {
        return getPackageInfo().versionName;
    }

    public static Integer getVersionCode() {
        return getPackageInfo().versionCode;
    }

    public static String getPackageName() {
        return getPackageInfo().packageName;
    }

    public static String getApplicationName() {
        Context context = MsqFrameworkApp.getInstance();
        ApplicationInfo applicationInfo = AndroidUtils.getApplicationInfo();
        return context.getPackageManager().getApplicationLabel(applicationInfo)
                .toString();
    }

    public static PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            Context context = MsqFrameworkApp.getInstance();
            info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            // Do Nothing
        }
        return info;
    }

    public static ApplicationInfo getApplicationInfo() {
        ApplicationInfo info = null;
        try {
            Context context = MsqFrameworkApp.getInstance();
            info = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (NameNotFoundException e) {
            // Do Nothing
        }
        return info;
    }

    public static void hideSoftInput(View view) {
        ((InputMethodManager) MsqFrameworkApp.getInstance().getSystemService(
                Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                view.getWindowToken(), 0);
    }

    public static WindowManager getWindowManager() {
        return (WindowManager) MsqFrameworkApp.getInstance().getSystemService(
                Context.WINDOW_SERVICE);
    }

    public static Integer getHeapSize() {
        return ((ActivityManager) MsqFrameworkApp.getInstance().getSystemService(
                Context.ACTIVITY_SERVICE)).getMemoryClass();
    }*/


    public static Boolean isMediaMounted() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public static String getDeviceModel() {
        return Build.MODEL;
    }

    public static Integer getApiLevel() {
        return Build.VERSION.SDK_INT;
    }

    public static Boolean isPreHoneycomb() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB;
    }

    public static String getPlatformVersion() {
        return Build.VERSION.RELEASE;
    }


    /*public static Boolean isSmallScreen() {
        int screenSize = MsqFrameworkApp.getInstance().getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK;
        return screenSize == Configuration.SCREENLAYOUT_SIZE_SMALL;
    }

    public static Boolean isNormalScreen() {
        int screenSize = MsqFrameworkApp.getInstance().getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK;
        return screenSize == Configuration.SCREENLAYOUT_SIZE_NORMAL;
    }

    public static Boolean isLargeScreen() {
        int screenSize = MsqFrameworkApp.getInstance().getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK;
        return screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static Boolean isXLargeScreen() {
        int screenSize = MsqFrameworkApp.getInstance().getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK;
        return screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    public static Boolean isLargeScreenOrBigger() {
        int screenSize = MsqFrameworkApp.getInstance().getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK;
        return (screenSize != Configuration.SCREENLAYOUT_SIZE_SMALL)
                && (screenSize != Configuration.SCREENLAYOUT_SIZE_NORMAL);
    }

    public static Boolean isXLargeScreenOrBigger() {
        int screenSize = MsqFrameworkApp.getInstance().getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK;
        return (screenSize != Configuration.SCREENLAYOUT_SIZE_SMALL)
                && (screenSize != Configuration.SCREENLAYOUT_SIZE_NORMAL)
                && (screenSize != Configuration.SCREENLAYOUT_SIZE_LARGE);
    }

    public static String getScreenSize() {
        String screenSize = "";
        if (AndroidUtils.isSmallScreen()) {
            screenSize = "small";
        } else if (AndroidUtils.isNormalScreen()) {
            screenSize = "normal";
        } else if (AndroidUtils.isLargeScreen()) {
            screenSize = "large";
        } else if (AndroidUtils.isXLargeScreen()) {
            screenSize = "xlarge";
        }
        return screenSize;
    }

    public static Boolean isLdpiDensity() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.densityDpi == DisplayMetrics.DENSITY_LOW;
    }

    public static Boolean isMdpiDensity() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.densityDpi == DisplayMetrics.DENSITY_MEDIUM;
    }

    public static Boolean isHdpiDensity() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.densityDpi == DisplayMetrics.DENSITY_HIGH;
    }

    public static Boolean isXhdpiDensity() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.densityDpi == DisplayMetrics.DENSITY_XHIGH;
    }

    public static Boolean isTVdpiDensity() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.densityDpi == DisplayMetrics.DENSITY_TV;
    }

    public static String getScreenDensity() {
        String density = "";
        if (AndroidUtils.isLdpiDensity()) {
            density = "ldpi";
        } else if (AndroidUtils.isMdpiDensity()) {
            density = "mdpi";
        } else if (AndroidUtils.isHdpiDensity()) {
            density = "hdpi";
        } else if (AndroidUtils.isXhdpiDensity()) {
            density = "xhdpi";
        } else if (AndroidUtils.isTVdpiDensity()) {
            density = "tvdpi";
        }
        return density;
    }

    public static DisplayMetrics getDisplayMetrics() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }*/

    public interface ISafeHandler {
        void handleMessage(Message msg);
    }

    public static class SafeHandler extends Handler {

        private final WeakReference<ISafeHandler> activity;

        public SafeHandler(ISafeHandler activity) {
            this.activity = new WeakReference<ISafeHandler>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            ISafeHandler activity = this.activity.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static void showMarket(Activity activity) {
        final String appPackageName = activity.getPackageName();
        try {
            Intent launchIntent = activity.getPackageManager()
                    .getLaunchIntentForPackage("com.android.vending");
            ComponentName comp = new ComponentName("com.android.vending",
                    "com.google.android.finsky.activities.LaunchUrlHandlerActivity");
            try {
                launchIntent.setComponent(comp);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(activity, "Google Play not found.", Toast.LENGTH_SHORT);
                return;
            }

            launchIntent.setData(Uri.parse("market://details?id="
                    + appPackageName));

            activity.startActivity(launchIntent);

        } catch (android.content.ActivityNotFoundException anfe) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri
                    .parse("http://play.google.com/store/apps/details?id="
                            + appPackageName)));
        }
    }


    /*
     * @package name
     * Google Play Store : com.android.vending
     * Google Map : com.google.android.apps.maps
     *
     */
    public static boolean isApkAvailable(Context context, String packagename) {
        PackageInfo packageInfo;

        try {
            packageInfo = context.getPackageManager().getPackageInfo(
                    packagename, 0);

        } catch (NameNotFoundException e) {
            packageInfo = null;
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    private final static X500Principal DEBUG_DN = new X500Principal(
            "CN=Android Debug,O=Android,C=US");

    public static boolean isDebuggable(Context ctx) {
        boolean debuggable = false;
        try {
            PackageInfo pinfo = ctx.getPackageManager().getPackageInfo(
                    ctx.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature signatures[] = pinfo.signatures;
            for (int i = 0; i < signatures.length; i++) {
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                ByteArrayInputStream stream = new ByteArrayInputStream(
                        signatures[i].toByteArray());
                X509Certificate cert = (X509Certificate) cf
                        .generateCertificate(stream);
                debuggable = cert.getSubjectX500Principal().equals(DEBUG_DN);
                if (debuggable)
                    break;
            }

        } catch (NameNotFoundException e) {
        } catch (CertificateException e) {
        }
        return debuggable;
    }

    public static boolean isApkDebugable(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {

        }
        return false;
    }

}
