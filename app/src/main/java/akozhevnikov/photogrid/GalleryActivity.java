package akozhevnikov.photogrid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import akozhevnikov.photogrid.network.ImageItem;
import akozhevnikov.photogrid.network.ImageResponse;
import akozhevnikov.photogrid.network.ImageSearchClient;
import akozhevnikov.photogrid.network.NetworkUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GalleryActivity extends AppCompatActivity {

    private StaggeredGridLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private ImageRecyclerViewAdapter imageAdapter;

    private List<ImageItem> images;
    private ImageSearchClient imageSearchClient;

    private String query;
    private int currentStartPosition = 1;
    private boolean loading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getSupportActionBar().setHomeButtonEnabled(true);

        query = getIntent().getStringExtra(Utils.QUERY_TAG);
        if (query == null)
            return;
        imageSearchClient = new ImageSearchClient(query);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(layoutManager);

        images = new ArrayList<>();

        imageAdapter = new ImageRecyclerViewAdapter(this, images);
        recyclerView.setAdapter(imageAdapter);
        recyclerView.addOnScrollListener(new EndlessRecyclerScrollListener());

        loadMoreData();
    }

    private void loadMoreData() {
        loading = true;
        imageSearchClient.getService().customSearch(Utils.API_KEY, Utils.CX_KEY, query,
                Utils.IMAGE_SEARCH_TYPE,
                currentStartPosition,
                Utils.ITEMS_COUNT,
                new Callback<ImageResponse>() {
                    @Override
                    public void success(ImageResponse imageResponse, Response response) {
                        List<ImageItem> items = imageResponse.getItems();
                        for (ImageItem item : items) {
                            images.add(item);
                        }
                        imageAdapter.notifyDataSetChanged();
                        currentStartPosition += items.size();
                        loading = false;
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e(GalleryActivity.class.getSimpleName(),
                                error.getResponse().getReason());
                    }
                });
    }

    private class EndlessRecyclerScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int[] visibleItems = layoutManager.findLastVisibleItemPositions(null);
            int lastItem = 0;
            for (int i : visibleItems) {
                lastItem = Math.max(lastItem, i);
            }
            if (lastItem > 0 && lastItem > images.size() - Utils.ITEMS_COUNT && !loading) {
                if (NetworkUtils.hasConnection(GalleryActivity.this)) {
                    loadMoreData();
                } else {
                    Toast.makeText(GalleryActivity.this, R.string.network_error,
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
