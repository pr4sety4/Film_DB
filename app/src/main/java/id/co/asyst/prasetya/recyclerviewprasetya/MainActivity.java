package id.co.asyst.prasetya.recyclerviewprasetya;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import id.co.asyst.prasetya.recyclerviewprasetya.Adapter.AlbumAdapter;
import id.co.asyst.prasetya.recyclerviewprasetya.Model.Album;
import id.co.asyst.prasetya.recyclerviewprasetya.retrofit.ApiClient;
import id.co.asyst.prasetya.recyclerviewprasetya.retrofit.ApiServices;
import retrofit2.Call;
import retrofit2.Callback;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AlbumAdapter albumAdapter;
    ArrayList<Album> listAlbum = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);


        albumAdapter = new AlbumAdapter(this, listAlbum, new AlbumAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Album album) {
                Toast.makeText(getApplicationContext(), "" + album.getTitle() + "\n" + album.getArtist(), Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(albumAdapter);


        getDataWithRetrofit();

    }

    public void getDataWithVolley() {
        String url = "http://rallycoding.herokuapp.com/api/music_albums";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("nama artist", response.toString());
                try {
                    for (int i = 0; i < response.length(); i++) {
                        Album album = new Gson().fromJson(response.getString(i), Album.class);
                        listAlbum.add(album);

                    }
                    albumAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public void getDataWithRetrofit() {

        ApiServices apiServices = ApiClient.newInstance(getApplicationContext()).create(ApiServices.class);
        Call<ArrayList<Album>> call = apiServices.getAlbums();

        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Content loading....");
        progressDoalog.setTitle("Loading...");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<ArrayList<Album>>() {
            @Override
            public void onResponse(Call<ArrayList<Album>> call, retrofit2.Response<ArrayList<Album>> response) {

                if (response.body() != null) {
                    if (response.body().size() > 0) {
                        progressDoalog.dismiss();
                        listAlbum.addAll(response.body());
                        albumAdapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Album>> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(getApplicationContext(), "Error" + t, Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

    }

}
