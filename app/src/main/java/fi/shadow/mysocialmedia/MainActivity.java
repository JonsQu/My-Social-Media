package fi.shadow.mysocialmedia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private AccessTokenTracker accessTokenTracker;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }
        callbackManager = CallbackManager.Factory.create();
        //Log.d("MainActivity", String.valueOf(AccessToken.getCurrentAccessToken()));
        if(AccessToken.getCurrentAccessToken() != null){
            accessToken = AccessToken.getCurrentAccessToken();
            goToFaceProfile();
        }else{
            loginFacebook();
        }
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                accessToken = currentAccessToken;
            }
        };
        //accessToken = AccessToken.getCurrentAccessToken();
        //LoginButton loginButton = findViewById(R.id.loginButton);

    }
    public void goToFaceProfile(){
        Intent profile = new Intent(this, MainPageActivity.class);
        profile.putExtra("accessToken", accessToken);
        startActivity(profile);
    }
    public void loginFacebook(){
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("MainActivity", String.valueOf(loginResult.getRecentlyGrantedPermissions()));
                        Log.d("MainActivity", Profile.getCurrentProfile().getFirstName());
                        accessToken = loginResult.getAccessToken();
                        getProfile();
                        goToFaceProfile();
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException exception) {
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", String.valueOf(AccessToken.getCurrentAccessToken()));
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        accessTokenTracker.startTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Forward onActivityResult to the callbackManager//
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    private void getProfile(){
        //TextView myName = findViewById(R.id.myName);
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                (object, response) -> {
                    // Insert your code here
                    //Log.d("MainActivity", Profile.getCurrentProfile().getFirstName());
                    //Log.d("MainActivity", Profile.getCurrentProfile().getProfilePictureUri(200,200).toString());
                    //Log.d("MainActivity", response.toString());
                    //Log.d("MainActivity", object.toString());
                    try {
                        userId = object.getString("id");
                        //myName.setText(object.getString("name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //getFeed();
                    Log.d("MainActivity", String.valueOf(object));
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link");
        request.setParameters(parameters);
        request.executeAsync();

    }
    private void getFeed(){
        Log.d("MainActivity", String.valueOf(AccessToken.getCurrentAccessToken()));
        new GraphRequest(
                accessToken,
                "/me",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        /* handle the result */
                        Log.d("MainActivity", String.valueOf(response));
                    }
                }
        ).executeAsync();
    }
}
