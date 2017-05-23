package com.tafe.mcintosh.onenote;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sp;
    EditText longText;
    public static final String preferences = "pref";
    public static final String oneNote = "Saved note";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        longText = (EditText) findViewById(R.id.longText);
        sp = getSharedPreferences(preferences, Context.MODE_PRIVATE);
        longText.setText(sp.getString(oneNote,""));

        Log.d("idea", "OnCreate was called.");
    }

    public void saveNote(View view) {
        // Collect user input and store in preferences
        String saveThis = longText.getText().toString();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(oneNote, saveThis);
        editor.apply();
    }

    public void showNote(View view) {
        // Collect preferences and show note in TextView
        sp = getSharedPreferences(preferences, Context.MODE_PRIVATE);
        longText.setText(sp.getString(oneNote, ""));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("idea", "App has been paused, save user input");
        String saveThis = longText.getText().toString();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(oneNote, saveThis);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("idea", "App has been resumed, show user input");
        sp = getSharedPreferences(preferences, Context.MODE_PRIVATE);
        longText.setText(sp.getString(oneNote, ""));
        Toast.makeText(this,"Message saved!",Toast.LENGTH_SHORT).show();
    }
}
