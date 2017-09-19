package org.kestell.photobooth;

import java.util.List;

public interface OnLoadPhotosListener {
    void OnLoadPhotosSuccess(List<Photo> photos);
    void OnLoadPhotosError();
}
