package de.rheinfabrik.trackdux.network;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

public final class TraktTVApiFactory {

    // Constants

    private static final String BASE_URL = "https://api-v2launch.trakt.tv";
    private static final String API_KEY = "ad005b8c117cdeee58a1bdb7089ea31386cd489b21e14b19818c91511f12a086";

    // Public Api

    public static TraktTVApi newApi() {
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(chain -> {
            Request request = chain.request();
            Request newRequest = request.newBuilder()
                    .addHeader("trakt-api-key", API_KEY)
                    .addHeader("trakt-api-version", "2")
                    .addHeader("Content-Type", "application/json")
                    .build();

            return chain.proceed(newRequest);
        });

        return new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(TraktTVApi.class);
    }

    // Constructor

    private TraktTVApiFactory() {
        throw new IllegalStateException("No instantiation possible!");
    }
}
