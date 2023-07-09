package android.maxim.freshwallpapers.data.network

import android.maxim.freshwallpapers.data.models.ImageList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WallpapersApi {

    @GET("api/")
    suspend fun getImageList(
        @Query("key") apiKey: String,
        @Query("per_page") perPage: Int,
        @Query("category") category: String,
        @Query("orientation") orientation: String,
        @Query("image_type") imageType: String,
        @Query("q") collection: String
    ): Response<ImageList>

}