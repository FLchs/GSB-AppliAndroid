package fr.cned.emdsgil.suividevosfrais;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;


import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by francois on 3/24/18.
 */

public class Synchronizer {
    /**
     * Convertir un objet en json et l'envoyer au serveur.
     *
     * @param object Objet à sérialiser
     */
    public static void syncToServer(Object object, final Context context) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(object);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://cned.francoislachese.fr/")
                .build();
        GsbAPI gsbAPI = retrofit.create(GsbAPI.class);

        HashMap<String, String> headermap = new HashMap<String, String>();
        headermap.put("Content-type", "Application/json");
        Call<ResponseBody> call = gsbAPI.post(jsonString, Global.login, Global.password);

        call.enqueue(new Callback<ResponseBody>() {

            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Toast.makeText(context, "Données synchronisées", Toast.LENGTH_SHORT).show();
                }

                else {
                    Toast.makeText(context, "Erreur pendant la synchronisation", Toast.LENGTH_SHORT).show();
                }

                String json = (response.code() + " ");
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });

    }
}
