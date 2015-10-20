package akozhevnikov.photogrid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import akozhevnikov.photogrid.network.ImageItem;


public class ImageRecyclerViewAdapter extends RecyclerView.Adapter<ImageViewHolder> {

    private List<ImageItem> itemList;
    private Context context;

    public ImageRecyclerViewAdapter(Context context, List<ImageItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_card_item, null);
        ImageViewHolder imageViewHolder = new ImageViewHolder(layoutView);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Picasso.with(context)
                .load(itemList.get(position).getLink())
                .error(R.drawable.error_image)
                .placeholder(R.drawable.progress_animation)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}