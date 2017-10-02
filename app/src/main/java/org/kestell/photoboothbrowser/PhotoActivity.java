package org.kestell.photoboothbrowser;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class PhotoActivity extends AppCompatActivity implements OnPrintPhotoListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String filename = getIntent().getStringExtra("filename");

        String photoPath = Api.BASE_URL + "/photos/" + filename;
        ImageView imageView = (ImageView)findViewById(R.id.photo);

        Picasso.with(this)
            .load(photoPath)
            .tag(this)
            .into(imageView);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Api api = new Api();
            api.printPhoto(filename, this);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnPrintPhotoSuccess() {
        Toast.makeText(this, "Your photo will print shortly.", Toast.LENGTH_SHORT).show();
        NavUtils.navigateUpFromSameTask(this);
    }

    @Override
    public void OnPrintPhotoError() {
        Toast.makeText(this, "Couldn't print photo!", Toast.LENGTH_SHORT).show();
    }
}
