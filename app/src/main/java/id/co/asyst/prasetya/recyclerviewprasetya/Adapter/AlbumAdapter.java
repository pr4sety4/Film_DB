package id.co.asyst.prasetya.recyclerviewprasetya.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import id.co.asyst.prasetya.recyclerviewprasetya.Model.Album;
import id.co.asyst.prasetya.recyclerviewprasetya.R;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<Album> mListAlbum;
    OnItemClickListener listener;

    public AlbumAdapter(Context context, ArrayList<Album> listAlbum, OnItemClickListener listener) {
        this.mContext = context;
        this.mListAlbum = listAlbum;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemVi = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false);
        return new AlbumAdapter.MyViewHolder(itemVi);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Album album = mListAlbum.get(position);
        holder.albumName_tV.setText(album.getTitle());
        holder.artist_tV.setText(album.getArtist());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(album);
            }
        });
        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background);
        Glide.with(mContext).load(album.getImage()).apply(requestOptions).into(holder.album_iV);
    }

    @Override
    public int getItemCount() {
        return mListAlbum.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Album album);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView album_iV;
        TextView artist_tV, albumName_tV;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            album_iV = itemView.findViewById(R.id.album_imageView);
            artist_tV = itemView.findViewById(R.id.artist_textView);
            albumName_tV = itemView.findViewById(R.id.album_textView);

            cardView = itemView.findViewById(R.id.cardView);
        }
    }

}
