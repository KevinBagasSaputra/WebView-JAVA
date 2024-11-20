package sinersimobile.id;

import static android.view.WindowManager.LayoutParams.FLAG_SECURE;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class MainActivity extends AppCompatActivity {

    WebView myWeb;
    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;

    @SuppressLint({"SetJavaScriptEnabled", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Logika tombol back
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (myWeb.canGoBack()) {
                    myWeb.goBack(); // Kembali ke halaman sebelumnya
                } else {
                    finish(); // Keluar dari aplikasi jika tidak ada halaman sebelumnya
                }
            }
        });



        //progressBar START
            progressBar = findViewById(R.id.progress);
        //progressBar END



        //swipeRefreshLayout START
            swipeRefreshLayout = findViewById(R.id.swipe);
        //swipeRefreshLayout END










        myWeb = findViewById(R.id.myWeb);
        myWeb.getSettings().setJavaScriptEnabled(true);

//new
        myWeb.getSettings().setSupportZoom(true);
        myWeb.getSettings().setDomStorageEnabled(true);
//new

        myWeb.setWebViewClient(new MyWebViewClient()); //WebViewClient
        myWeb.loadUrl("https://sinersimobile.id/login/index.php/login");

//new
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);

            // Ambil URL saat ini
            String currentUrl = myWeb.getUrl();

            // Refresh dengan URL yang sedang aktif
            new Handler().postDelayed(() -> {
                swipeRefreshLayout.setRefreshing(false);
                if (currentUrl != null && !currentUrl.isEmpty()) {
                    myWeb.loadUrl(currentUrl);
                } else {
                    // Jika URL kosong, arahkan ke URL default
                    myWeb.loadUrl("https://sinersimobile.id/login/index.php/login");
                }
            }, 3000); // memberi delay
        });

        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_blue_dark),
                getResources().getColor(android.R.color.holo_green_dark),
                getResources().getColor(android.R.color.holo_red_dark)
        );
//new





        //        Restrict screenshots
            getWindow().setFlags(FLAG_SECURE, FLAG_SECURE);
        //        Restrict screenshots

    }

    //Pass URL
    public class MyWebViewClient extends WebViewClient {
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            Toast.makeText(getApplicationContext(), "Tidak ada koneksi internet", Toast.LENGTH_LONG).show();
            view.loadUrl("file:///android_asset/lost.html");
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
            view.loadUrl("file:///android_asset/lost.html");
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }






}
    //Pass URL

