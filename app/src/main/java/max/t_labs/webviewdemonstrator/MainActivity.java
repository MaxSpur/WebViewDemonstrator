package max.t_labs.webviewdemonstrator;

import android.app.Activity;
//import android.app.DownloadManager;
//import android.content.Context;
//import android.content.Intent;
import android.content.pm.ActivityInfo;
//import android.content.pm.PackageManager;
//import android.content.pm.ResolveInfo;
//import android.content.res.Configuration;
//import android.net.Uri;
//import android.os.Build;
import android.os.Environment;
//import android.provider.Settings;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
//import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
//import java.util.List;

//import static android.content.pm.PackageManager.*;


public class MainActivity extends Activity {

    private WebView mWebView;

//    public static boolean isDownloadManagerAvailable(Context context) {
//        try {
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
//                return false;
//            }
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_LAUNCHER);
//            intent.setClassName("com.android.providers.downloads.ui", "com.android.providers.downloads.ui.DownloadList");
//            List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent,
//                    MATCH_DEFAULT_ONLY);
//            return list.size() > 0;
//        } catch (Exception e) {
//            return false;
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = 1.0f;
        getWindow().setAttributes(layoutParams);


        mWebView = (WebView) findViewById(R.id.activity_main_webview);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        // enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // open all things inside WebView
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if(url.endsWith(".txt")) {
                    String content = url;
                    FileOutputStream outputStream;

                    Calendar c = Calendar.getInstance();

                    String fileName = Long.toString(c.getTimeInMillis()) + ".txt";

                    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    File file = new File(path, fileName);

                    try {
                        outputStream = new FileOutputStream(file);
                        outputStream.write(content.getBytes());
                        outputStream.close();

                        Log.i("saved file:", fileName);

                        return false;

                    } catch (IOException e) {
                        Log.e("SAVE DATA", "Could not write file " + e.getMessage());
                        e.printStackTrace();
                    }

//                    Uri source = Uri.parse(url);
//                    DownloadManager.Request request = new DownloadManager.Request(source);
//                    request.setDescription("PDF Download");
//                    request.setTitle("Demonstrator.pdf");
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                        request.allowScanningByMediaScanner();
//                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                    }
//                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "name-of-the-file.ext");
//
//                    // get download service and enqueue file
//                    DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
//                    manager.enqueue(request);
                }
//        else view.loadUrl(url);
                return false;
            }
        });

//        mWebView.setWebChromeClient(new WebChromeClient(){        });

        // load that page
//        mWebView.loadUrl("file:///android_asset/index.html");
        mWebView.loadUrl("file:///"
                + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + "/index.html");
//        mWebView.loadUrl("http://www.princexml.com/samples/");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
