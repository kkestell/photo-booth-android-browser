package org.kestell.photoboothbrowser;

import java.util.List;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class Api {
    public static String BASE_URL = "http://10.0.0.1:4567";

    private ApiClient client;

    public interface ApiClient {
        @GET("/photos")
        Call<List<Photo>> listPhotos();

        @POST("/photos/{filename}/prints")
        Call<Void> printPhoto(@Path("filename") String filename);
    }

    public Api() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        client = retrofit.create(ApiClient.class);
    }

    public void printPhoto(String filename, final OnPrintPhotoListener listener) {
        Call<Void> call = client.printPhoto(filename);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                listener.OnPrintPhotoSuccess();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.OnPrintPhotoError();
            }
        });
    }

    public void loadPhotos(final OnLoadPhotosListener listener) {
        Call<List<Photo>> call = client.listPhotos();

        call.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                listener.OnLoadPhotosSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                listener.OnLoadPhotosError();
            }
        });
    }
}