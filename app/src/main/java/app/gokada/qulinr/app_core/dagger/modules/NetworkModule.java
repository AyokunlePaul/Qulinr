package app.gokada.qulinr.app_core.dagger.modules;

import android.content.Context;

import java.io.File;

import app.gokada.qulinr.BuildConfig;
import app.gokada.qulinr.app_core.dagger.scopes.MainScope;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

@Module(includes = {ContextModule.class})
public class NetworkModule {

    @Provides
    @MainScope
    public Retrofit provideRetrofit(OkHttpClient client){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        builder.baseUrl(BuildConfig.BASE_URL);
        builder.client(client);

        return builder.build();
    }

    @Provides
    @MainScope
    public OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor, Cache cache){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(loggingInterceptor);
        builder.cache(cache);

        return builder.build();
    }

    @Provides
    @MainScope
    public HttpLoggingInterceptor provideLoggingInterceptor(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Timber.i(message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return interceptor;
    }

    @Provides
    @MainScope
    public Cache provideCache(File file){
        return new Cache(file, 10 * 1000 * 1000);
    }

    @Provides
    @MainScope
    public File provideCacheFile(Context context){
        return new File(context.getCacheDir(), "Qulinr_Cache");
    }

//    @Provides
//    public Interceptor provideInterceptor(){
//        Interceptor interceptor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                return null;
//            }
//        };
//    }

}
