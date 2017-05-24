package com.tafe.mcintosh.onenote;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

        refreshFont();
        Log.d("idea", "OnCreate was called.");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.settings:
                Log.d("menu", "Settings was clicked");
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.about:
                Log.d("menu", "About was clicked");
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void refreshFont(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String fontSize = sharedPref.getString("font_preference", "");
        int fontSizeInt = Integer.parseInt(fontSize);
        longText.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSizeInt);
        Log.d("menu", String.valueOf(fontSizeInt));
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
        refreshFont();
        Log.d("idea", "App has been resumed, show user input");
        sp = getSharedPreferences(preferences, Context.MODE_PRIVATE);
        longText.setText(sp.getString(oneNote, ""));
        Toast.makeText(this,"Message saved!",Toast.LENGTH_SHORT).show();
    }
}
