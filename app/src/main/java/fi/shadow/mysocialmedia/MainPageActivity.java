package fi.shadow.mysocialmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.BuildConfig;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MainPageActivity extends AppCompatActivity {
    private ProfilePictureView profilePic;
    private TextView myName;
    private AccessToken accessToken;
    private CallbackManager callbackManager;
    private Bitmap pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        callbackManager = CallbackManager.Factory.create();
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }
        Intent getE = getIntent();
        accessToken = AccessToken.getCurrentAccessToken();
        profilePic = findViewById(R.id.profilePic);
        Log.d("MainPageActivity", accessToken.toString());
        Log.d("MainPageActivity", AccessToken.getCurrentAccessToken().toString());

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Profile profile = Profile.getCurrentProfile();
        Log.d("MainPageActivity", profile.getProfilePictureUri(200,200).toString());
        myName = findViewById(R.id.myName);
        myName.setText(profile.getName());
        try {
            InputStream input = this.getContentResolver().openInputStream(profile.getProfilePictureUri(200,200));
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            Log.d("MainPageActivity", bitmap.toString());
            //profilePic.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }
        profilePic.setProfileId(profile.getId());
        Log.d("MainPageActivity", profilePic.toString());
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/feed",
                null,
                HttpMethod.GET,
                response -> {
                    Log.d("MainPageActivity", response.toString());
                }
        ).executeAsync();
        /*GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                (object, response) -> {
                    // Insert your code here
                    //Log.d("MainActivity", Profile.getCurrentProfile().getFirstName());
                    Log.d("MainPageActivity", Profile.getCurrentProfile().getProfilePictureUri(200,200).toString());
                    Bundle params = new Bundle();
                    params.putBoolean("redirect", false);
                    params.putString("height", "200");
                    params.putString("type", "normal");
                    params.putString("width", "200");

                    new GraphRequest(
                            AccessToken.getCurrentAccessToken(),
                            "/me/picture",
                            params,
                            HttpMethod.GET,
                            new GraphRequest.Callback() {
                                public void onCompleted(GraphResponse response) {
                                    try {
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                    ).executeAsync();

                    //Log.d("MainActivity", response.toString());
                    //Log.d("MainActivity", object.toString());
                    try {
                        //userId = object.getString("id");
                        //myName.setText(object.getString("name"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //getFeed();
                    Log.d("MainPageActivity", String.valueOf(object));
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link");
        request.setParameters(parameters);
        request.executeAsync();*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        //profilePic.setImageBitmap(pic);
    }
}
