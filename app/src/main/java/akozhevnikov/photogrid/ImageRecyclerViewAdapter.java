package akozhevnikov.photogrid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import akozhevnikov.photogrid.network.ImageItem;

public class ImageRecyclerViewAdapter extends RecyclerView.Adapter<ImageViewHolder> {

    private List<ImageItem> itemList;
    private Context context;
    private int parentWidth = 0;

    public ImageRecyclerViewAdapter(Context context, List<ImageItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_card_item, null);
        ImageViewHolder imageViewHolder = new ImageViewHolder(layoutView);
        parentWidth = parent.getWidth();
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, int position) {
        Picasso.with(context)
                .load(itemList.get(position).getLink())
                .error(R.drawable.error_image)
                .placeholder(R.drawable.progress_animation)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        int targetWidth = parentWidth / 2;
                        float ratio = (float) bitmap.getHeight() / (float) bitmap.getWidth();
                        float heightFloat = ((float) targetWidth) * ratio;

                        final android.view.ViewGroup.MarginLayoutParams layoutParams
                                = (ViewGroup.MarginLayoutParams) holder.image.getLayoutParams();

                        layoutParams.height = (int) heightFloat;
                        layoutParams.width = (int) targetWidth;
                        holder.image.setLayoutParams(layoutParams);
                        holder.image.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}