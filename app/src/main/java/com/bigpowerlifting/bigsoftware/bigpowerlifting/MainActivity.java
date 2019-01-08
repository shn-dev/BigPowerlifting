package com.bigpowerlifting.bigsoftware.bigpowerlifting;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.bigpowerlifting.bigsoftware.bigpowerlifting.tools.ToolContent;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements com.bigpowerlifting.bigsoftware.bigpowerlifting.ToolFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        try {
            String filename = "BS Logo Final PNG-01.png";
            Drawable actionBarLogo = Drawable.createFromStream(getAssets().open(filename), filename);
            myToolbar.setLogo(actionBarLogo);
            myToolbar.setTitle(""); //Don't want a title; we have an image for this
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        setSupportActionBar(myToolbar);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainActivityContainer, new com.bigpowerlifting.bigsoftware.bigpowerlifting.ToolFragment(), "TOOL_FRAGMENT")
                .commit();
    }

    @Override
    public void onListFragmentInteraction(ToolContent.Tool item) {
        //Toast.makeText(this, "Touched " + item.id, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }
}
