package fr.cned.emdsgil.suividevosfrais;

/**
 * Created by francois on 3/20/18.
 */

import java.util.Hashtable;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GsbAPI {

    String BASE_URL = "http://cned.francoislachese.fr/";

    @Headers("Content-Type: Application/json")
    @POST(BASE_URL+"test/test.php")

    Call<ResponseBody> login(
            @HeaderMap Map<String, String> headers,
            @Query("login") String login,
            @Query("password") String password
    );

    @POST(BASE_URL+"test/save.php")
    Call<ResponseBody> post(
            //@HeaderMap Map<String, String> headers,
            @Query("data") String body,
            // TODO: 3/24/18 Recuperation du login/mdp
            @Query("login") String login,
            @Query("password") String password
    );

}