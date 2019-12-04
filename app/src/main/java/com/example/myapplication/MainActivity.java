package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> strings = new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button press;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCenter.start(getApplication(), "3d6a25c6-5f6f-4c1b-bc00-ec3d61da8000", Analytics.class, Crashes.class);
        Analytics.setEnabled(true);
        press = (Button) findViewById(R.id.press);
        sharedPreferences = getSharedPreferences("MYPREF", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String s1 = "abc";
        String s2 = "def";
        String s3 = "ghi";
        strings.add(s1);
        strings.add(s2);
        strings.add(s3);

        try {

            editor.putString("mylist", ObjectSerializer.serialize(strings));
            editor.commit();


            ArrayList dss = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("mylist", ObjectSerializer.serialize(new ArrayList<String>())));
            Log.e("TAG===", "onCreate: " + dss.size());


            press.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    strings.remove(0);
                    try {
                        editor.putString("mylist", ObjectSerializer.serialize(strings));
                        editor.commit();
                        ArrayList d2ss = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("mylist", ObjectSerializer.serialize(new ArrayList<String>())));
                        Log.e("TAG===", "onCreate: " + d2ss.size());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
