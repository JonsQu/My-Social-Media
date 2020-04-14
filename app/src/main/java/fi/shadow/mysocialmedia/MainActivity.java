package fi.shadow.mysocialmedia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.BuildConfig;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private AccessToken accessToken;
    private ProfileTracker profileTracker;
    private Profile profile;
    private AccessTokenTracker accessTokenTracker;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    public void toFacebook(View v){
        Intent fb = new Intent(this, MyFacebookProfile.class);
        startActivity(fb);
    }
    public void toTwitter(View v){
        Intent tt = new Intent(this, MyTwitterProfile.class);
        startActivity(tt);
    }
    public void toLinkedin(View v){
        Intent li = new Intent(this, MyLinkedinProfile.class);
        startActivity(li);
    }
}
