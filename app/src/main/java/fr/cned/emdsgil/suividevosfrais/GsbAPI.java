package fr.cned.emdsgil.suividevosfrais;

/**
 * Created by francois on 3/20/18.
 */

import java.util.Hashtable;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GsbAPI {

    String BASE_URL = "http://cned.francoislachese.fr/";

    @Headers("Content-Type: Application/json")
    @POST(BASE_URL+"api/auth.php")
    Call<ResponseBody> login(
            @HeaderMap Map<String, String> headers,
            @Query("login") String login,
            @Query("password") String password
    );

    @POST(BASE_URL+"api/write.php")
    Call<ResponseBody> post(
            //@HeaderMap Map<String, String> headers,
            @Body RequestBody body,
            @Query("login") String login,
            @Query("password") String password
    );

    @POST(BASE_URL+"api/read.php")
    Call<Hashtable<Integer, FraisMois>> read(
            @HeaderMap Map<String, String> headers,
            @Query("login") String login,
            @Query("password") String password
    );

}