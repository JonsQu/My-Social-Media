package fi.shadow.mysocialmedia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.BuildConfig;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.ProfilePictureView;

public class MyFacebookProfile extends AppCompatActivity {
    private CallbackManager callbackManager;
    private RelativeLayout fbProfile;
    private Button fbLogin;
    private ProfilePictureView myProPic;
    private TextView myName;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    private AccessToken accessToken;
    private Profile profile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MyFacebookProfile", "Start of onCreate");
        setContentView(R.layout.activity_my_facebook_profile);
        callbackManager = CallbackManager.Factory.create();
        fbProfile = findViewById(R.id.fbProfile);
        fbLogin = findViewById(R.id.fbLogin);
        myName = findViewById(R.id.myName);
        myProPic =findViewById(R.id.myProPic);
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                accessToken = currentAccessToken;
            }
        };
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                profile = currentProfile;
            }
        };

        Log.d("MyFacebookProfile", "Before");
        if(AccessToken.getCurrentAccessToken() == null){
            Log.d("MyFacebookProfile", "AccessToken is null");
            loginFacebook();
        }else{
            Log.d("MyFacebookProfile", "AccessToken is not null");
            profile = Profile.getCurrentProfile();
            Log.d("MyFacebookProfile", profile.toString());
            fbLogin.setVisibility(View.GONE);
            fbProfile.setVisibility(View.VISIBLE);
            myName.setText(profile.getName());
            myProPic.setProfileId(profile.getId());
        }
        /*ProfilePictureView myProPic = findViewById(R.id.profilePic);
        myProPic.setPresetSize(ProfilePictureView.LARGE);
        myProPic.setProfileId(Profile.getCurrentProfile().getId());*/
    }
    public void loginFacebook(){
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        fbLogin.setVisibility(View.GONE);
                        fbProfile.setVisibility(View.VISIBLE);
                        myName.setText(Profile.getCurrentProfile().getName());
                        myProPic.setProfileId(Profile.getCurrentProfile().getId());
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Forward onActivityResult to the callbackManager//
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
