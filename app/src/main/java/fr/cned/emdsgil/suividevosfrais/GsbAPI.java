package fr.cned.emdsgil.suividevosfrais;

/**
 * Created by francois on 3/20/18.
 */

import java.util.Map;

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

    @POST("test/test.php")
    Call<ResponseBody> login(
            @HeaderMap Map<String, String> headers,
            @Query("login") String login,
            @Query("password") String password
    );

}