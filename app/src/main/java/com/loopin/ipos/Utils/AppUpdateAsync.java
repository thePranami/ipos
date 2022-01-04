package com.loopin.ipos.Utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import com.loopin.ipos.R;

import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;

public class AppUpdateAsync extends AsyncTask<String, String, JSONObject> {

    private String latestVersion;
    private String currentVersion;
    @SuppressLint("StaticFieldLeak")
    private Context context;

    public AppUpdateAsync(String currentVersion, Context context) {
        this.currentVersion = currentVersion;
        this.context = context;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        try {
            latestVersion = Jsoup.connect("https://play.google.com/store/apps/details?id="+context.getPackageName()+"&hl=en")
                    .timeout(30000)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get()
                    .select("div.hAyfc:nth-child(4) > span:nth-child(2) > div:nth-child(1) > span:nth-child(1)")
                    .first()
                    .ownText();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        if (latestVersion != null){
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = null;
            try {
                packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            }catch (PackageManager.NameNotFoundException e){
                e.printStackTrace();
            }
            assert packageInfo != null;
            String currentVersion = packageInfo.versionName;
            if (!currentVersion.equalsIgnoreCase(latestVersion)){
                updateDialog(context);
            }else {
                Log.d("noUpdate", "No update");
            }
        }
        super.onPostExecute(jsonObject);
    }
    private void updateDialog(Context context){
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(context);
        aBuilder.setCancelable(false).setTitle("Ipos Update!").setIcon(R.drawable.ic_update)
                .setMessage("A new version of IPOS is available on Play Store. Click on update to latest version.")
                .setPositiveButton("Update", (dialog, which) -> {
                    String iPos_uri = "https://play.google.com/store/apps/details?id=com.loopin.ipos&hl=en";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(iPos_uri));
                    context.startActivity(intent);
                    dialog.cancel();
                }).setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = aBuilder.create();
        alertDialog.show();
    }

}
