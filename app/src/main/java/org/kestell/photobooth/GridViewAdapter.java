package org.kestell.photobooth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

final class GridViewAdapter extends BaseAdapter {
    private final Context context;
    private List<Photo> photos = new ArrayList<>();
    private LayoutInflater inflater;

    GridViewAdapter(Context context, List<Photo> photos) {
        this.context = context;
        this.photos = photos;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.grid_view_item_image, parent, false);

        Photo photo = getItem(position);

        String filename = Api.BASE_URL + "/photos/" + photo.getThumbnail();

        Picasso.with(context)
                .load(filename)
                .tag(context)
                .into((ImageView)convertView);

        return convertView;
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Photo getItem(int position) {
        return photos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updatePhotos(List<Photo> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }
}