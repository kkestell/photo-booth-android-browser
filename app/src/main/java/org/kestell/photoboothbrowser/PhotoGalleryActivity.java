package org.kestell.photoboothbrowser;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

public class PhotoGalleryActivity extends AppCompatActivity implements OnLoadPhotosListener {
    private List<Photo> photos;
    private GridView gridView;
    private Api api;
    private Handler handler;
    private GridViewAdapter adapter;

    private Runnable photoUpdater = new Runnable() {
        @Override
        public void run() {
            try {
                updatePhotos();
            } finally {
                handler.postDelayed(photoUpdater, 1000);
            }
        }
    };

    @Override
    public void OnLoadPhotosSuccess(List<Photo> photos) {
        if(adapter == null) {
            adapter = new GridViewAdapter(this, photos);
            gridView.setAdapter(adapter);
        } else {
            adapter.updatePhotos(photos);
        }
    }

    @Override
    public void OnLoadPhotosError() {
        Toast.makeText(this, "Couldn't load photos.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);

        api = new Api();
        handler = new Handler();

        initializeGridView();

        photoUpdater.run();
    }

    private void initializeGridView() {
        gridView = (GridView)findViewById(R.id.gridview);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Photo photo = ((GridViewAdapter)parent.getAdapter()).getItem(position);
                Intent intent = new Intent(PhotoGalleryActivity.this, PhotoActivity.class);
                intent.putExtra("filename", photo.getFilename());
                startActivity(intent);
            }
        });
    }

    private void updatePhotos() {
        api.loadPhotos(this);
    }
}
