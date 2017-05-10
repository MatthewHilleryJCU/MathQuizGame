package au.com.hillnet.mathquizgame;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class Authenticate extends AppCompatActivity {

    WebView webView;
    Twitter twitter = TwitterFactory.getSingleton();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);

        webView = (WebView) findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onLoadResource(WebView view, String url) {
                System.out.println(url);
                if (url.startsWith("http://crunchycodes.net")) {
                    Uri uri = Uri.parse(url);
                    final String oauthVerifier = uri.getQueryParameter("oauth_verifier");
                    if (oauthVerifier != null) {
                        System.out.println("authenticated!");
                        Background.run(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    AccessToken accessToken = twitter.getOAuthAccessToken(oauthVerifier);
                                    twitter.setOAuthAccessToken(accessToken);

                                    Intent intent = new Intent();
                                    intent.putExtra("access token", accessToken.getToken());
                                    intent.putExtra("access token secret", accessToken.getTokenSecret());

                                    setResult(RESULT_OK, intent);
                                    finish();
                                } catch (Exception e) {
                                    System.out.println(e.toString());
                                }
                            }
                        });
                    }
                }
                super.onLoadResource(view, url);
            }
        });

        Background.run(new Runnable() {
            @Override
            public void run() {
                try {
                    RequestToken requestToken = twitter.getOAuthRequestToken();
                    final String requestUrl = requestToken.getAuthenticationURL();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView.loadUrl(requestUrl);
                        }
                    });
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
        super.onBackPressed();
    }
}
