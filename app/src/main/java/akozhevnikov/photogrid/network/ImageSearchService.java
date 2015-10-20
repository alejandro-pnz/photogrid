package akozhevnikov.photogrid.network;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ImageSearchService {

    @GET("/customsearch/v1")
    void customSearch(@Query("key") String key, @Query("cx") String cx,
                      @Query("q") String query, @Query("searchType") String searchType,
                      @Query("start") int position,
                      @Query("num") int number,
                      Callback<ImageResponse> cb);
}