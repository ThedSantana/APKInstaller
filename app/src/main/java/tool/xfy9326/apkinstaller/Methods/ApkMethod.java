package tool.xfy9326.apkinstaller.Methods;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.graphics.drawable.Drawable;

public class ApkMethod {
    private final String applicationPath;
    private final PackageManager packageManager;

    public ApkMethod(Context context, String applicationPath) {
        this.applicationPath = applicationPath;
        this.packageManager = context.getPackageManager();
    }

    public String getApplicationPkgName() {
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(applicationPath, PackageManager.GET_ACTIVITIES);
        if (packageInfo != null) {
            return packageInfo.packageName;
        }
        return null;
    }

    public String getApplicationName() {
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(applicationPath, PackageManager.GET_ACTIVITIES);
        if (packageInfo != null) {
            packageInfo.applicationInfo.sourceDir = applicationPath;
            packageInfo.applicationInfo.publicSourceDir = applicationPath;
            return packageInfo.applicationInfo.loadLabel(packageManager).toString();
        }
        return null;
    }

    public String[] getApplicationVersion() {
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(applicationPath, PackageManager.GET_ACTIVITIES);
        if (packageInfo != null) {
            String[] result = new String[2];
            result[0] = packageInfo.versionName + " (" + packageInfo.versionCode + ")";
            try {
                PackageInfo sysPackageInfo = packageManager.getPackageInfo(packageInfo.packageName, PackageManager.GET_ACTIVITIES);
                result[1] = sysPackageInfo.versionName + " (" + sysPackageInfo.versionCode + ")";
            } catch (PackageManager.NameNotFoundException ignored) {
            }
            return result;
        }
        return null;
    }

    public Drawable getApplicationIcon() {
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(applicationPath, PackageManager.GET_ACTIVITIES);
        packageInfo.applicationInfo.sourceDir = applicationPath;
        packageInfo.applicationInfo.publicSourceDir = applicationPath;
        return packageInfo.applicationInfo.loadIcon(packageManager);
    }

    public String getApplicationPermission() {
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(applicationPath, PackageManager.GET_PERMISSIONS);
        if (packageInfo != null) {
            StringBuilder result = new StringBuilder();
            String[] allPermission = packageInfo.requestedPermissions;
            if (allPermission != null) {
                if (allPermission.length != 0) {
                    PermissionInfo permissionInfo;
                    for (String permissionName : allPermission) {
                        try {
                            permissionInfo = packageManager.getPermissionInfo(permissionName, 0);
                            result.append(permissionInfo.loadLabel(packageManager).toString()).append("\n");
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                            result.append(permissionName).append("\n");
                        }
                    }
                }
                return result.toString();
            }
        }
        return null;
    }
}
