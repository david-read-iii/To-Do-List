package com.davidread.todolist;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * {@link PermissionsUtil} provides {@link #hasPermissions(Activity, String, int, int)} to check
 * for and request for permissions.
 */
public class PermissionsUtil {

    /**
     * Checks if this app has been granted the given permission. If not, this permission is
     * requested.
     *
     * @param activity           Reference to the calling {@link Activity}.
     * @param permission         {@link String} containing the permission being requested.
     * @param rationaleMessageId String resource id to a message rationalizing the permission's use.
     * @param requestCode        Request code to identify a request permission result.
     * @return Whether this permission was granted.
     */
    public static boolean hasPermissions(final Activity activity, final String permission,
                                         int rationaleMessageId, final int requestCode) {

        // See if permission is granted.
        if (ContextCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED) {

            // Explain why permission needed?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {

                // Show why permission is needed.
                showPermissionRationaleDialog(activity, rationaleMessageId, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Request permission again.
                        ActivityCompat.requestPermissions(activity,
                                new String[]{permission}, requestCode);
                    }
                });
            } else {
                // Request permission.
                ActivityCompat.requestPermissions(activity,
                        new String[]{permission}, requestCode);
            }
            return false;
        }
        return true;
    }

    /**
     * Pops an {@link AlertDialog} informing the user why the permission is requested.
     *
     * @param activity        Reference to the calling {@link Activity}.
     * @param messageId       String resource id to a message rationalizing the permission's use.
     * @param onClickListener {@link DialogInterface.OnClickListener} to attach to the positive
     *                        button of this {@link AlertDialog}.
     */
    private static void showPermissionRationaleDialog(Activity activity, int messageId,
                                                      DialogInterface.OnClickListener onClickListener) {
        // Show dialog explaining why permission is needed
        new AlertDialog.Builder(activity)
                .setTitle(R.string.permission_needed)
                .setMessage(messageId)
                .setPositiveButton(R.string.ok, onClickListener)
                .create()
                .show();
    }
}
