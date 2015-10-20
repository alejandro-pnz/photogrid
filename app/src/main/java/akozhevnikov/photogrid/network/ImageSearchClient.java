package akozhevnikov.photogrid.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class ImageSearchClient {
    final String BASE_URL = "https://www.googleapis.com";

    private ImageSearchService service;
    private String query;

    public ImageSearchClient(String query) {
        Gson gson = new GsonBuilder()
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        this.query = query;
        service = restAdapter.create(ImageSearchService.class);
    }

    public ImageSearchService getService() {
        return service;
    }
}
