package fi.shadow.mysocialmedia;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.AccessToken;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(AccessToken.getCurrentAccessToken() == null){
            Intent loginIntent = new Intent(this, LoginActivity.class);
        }
    }
}
