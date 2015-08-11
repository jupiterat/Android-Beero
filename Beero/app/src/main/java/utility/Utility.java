package utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import com.au.beero.beero.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean isEmailValid(String email) {
        final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        final Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String getCachDir(Context context) {
        File cacheDir;
        // Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(),
                    context.getString(R.string.app_name));
        else
            cacheDir = context.getCacheDir();
        if (!cacheDir.exists())
            cacheDir.mkdirs();

        if (cacheDir != null)
            return cacheDir.getAbsolutePath();
        else
            return "/";
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = ((Activity) context).managedQuery(contentUri, proj, null, null, null);

        if (cursor == null)
            return null;

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    public static byte[] getByteArray(Bitmap photo, int percent) {
        if (photo == null)
            return null;

        byte[] byteArray = null;
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, percent, stream);
            byteArray = stream.toByteArray();

            if (byteArray == null) {
                photo.compress(Bitmap.CompressFormat.PNG, percent, stream);
                byteArray = stream.toByteArray();
            }
        } catch (OutOfMemoryError e) {

        } catch (Exception e) {
        }

        return byteArray;
    }

    public static Bitmap getBitmapFromUri(Context context, Uri uri, int sample) {
        if (context == null || uri == null)
            return null;
        Bitmap resultBitmap = null;
        Options options = new Options();
        options.inSampleSize = sample;
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);

            resultBitmap = BitmapFactory.decodeStream(inputStream, null, options);
        } catch (OutOfMemoryError e) {

        } catch (Exception e) {

        }

        return resultBitmap;
    }

    public static String calculateDifferenceFromCurrent(long time) {

        Date date = new Date();
        long duration = date.getTime() - time;
        String res = "";
        long days = TimeUnit.MILLISECONDS.toDays(duration);
        long hours = TimeUnit.MILLISECONDS.toHours(duration)
                - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration)
                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration));

        if (days < 0 || hours < 0 || minutes < 0 || seconds < 0) {
            return res = "just now";
        }

        if (days == 0 && hours == 0 && minutes == 0) {
            if (seconds >= 30) {
                res = "01 min";
            } else {
                res = "just now";
            }
        } else if (days == 0 && hours == 0) {
            if (seconds >= 30) {
                // res = String.format("%02dmin", minutes + 1);
                minutes = minutes + 1;
            }
            // else {
            // res = String.format("%02dmin", minutes);
            // }
            if (minutes > 1) {
                res = String.format("%02d mins", minutes);
            } else {
                res = String.format("%02d min", minutes);
            }

        } else if (days == 0) {
            // if (seconds >= 30) {
            // res = String.format("%02dh:%02dm", hours, minutes + 1);
            // } else {
            // res = String.format("%02dh:%02dm", hours, minutes);
            // }

            if (minutes >= 30) {
                hours = hours + 1;
                // res = String.format("%02dhrs", hours + 1);
            }
            // else {
            // res = String.format("%02dhrs", hours);
            // }
            if (hours > 1) {
                res = String.format("%02d hrs", hours);
            } else {
                res = String.format("%02d hr", hours);
            }

        } else {
            // if (seconds >= 30) {
            // res = String.format("%dd:%02dh:%02dm", days, hours, minutes + 1);
            // } else {
            // res = String.format("%dd:%02dh:%02dm", days, hours, minutes);
            // }
            // if (seconds >= 30) {
            if (days > 1) {
                res = String.format("%d days", days);
            } else {
                res = String.format("%d day", days);
            }
            // } else {
            // res = String.format("%dd:%02dh:%02dm", days);
            // }

        }
        return res;
    }

    public static String convertNumberFormat(int number) {
        String result = "";
        result = NumberFormat.getNumberInstance(Locale.US).format(number);
        return result;
    }

    public static void sendFeedBack(Context context, String content) {
        // works with blank mailId - user will have to fill in To: field
        String mailId = "feedback@consumr.com";
        String subject = "Android App Feedback";
        // or can work with pre-filled-in To: field
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                Uri.fromParts("mailto", mailId, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, content);
        // you can use simple text like this
        context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }
    public static void showInputKeyboard(Context ctx, EditText edt) {
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edt, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * hide keyboards
     *
     * @param ctx
     * @param edt
     */
    public static void hideInputKeyboard(Context ctx, EditText edt) {
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edt.getWindowToken(), 0);
    }
    public static void hideInputKeyboard(Context ctx, View view) {
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
