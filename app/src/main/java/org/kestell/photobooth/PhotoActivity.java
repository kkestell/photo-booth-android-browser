package org.kestell.photobooth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PhotoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        String filename = Api.BASE_URL + "/photos/" + getIntent().getStringExtra("filename");
        ImageView imageView = (ImageView)findViewById(R.id.photo);

        Picasso.with(this)
            .load(filename)
            .tag(this)
            .into(imageView);
    }
}
