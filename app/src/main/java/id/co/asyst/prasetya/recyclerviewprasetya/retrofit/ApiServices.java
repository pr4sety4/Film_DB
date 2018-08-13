package id.co.asyst.prasetya.recyclerviewprasetya.retrofit;

import java.util.ArrayList;

import id.co.asyst.prasetya.recyclerviewprasetya.Model.Album;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServices {

    @GET("music_albums")
    Call<ArrayList<Album>> getAlbums();

}
