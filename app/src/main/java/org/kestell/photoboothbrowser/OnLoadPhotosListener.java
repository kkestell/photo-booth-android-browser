package org.kestell.photoboothbrowser;

import java.util.List;

public interface OnLoadPhotosListener {
    void OnLoadPhotosSuccess(List<Photo> photos);
    void OnLoadPhotosError();
}
