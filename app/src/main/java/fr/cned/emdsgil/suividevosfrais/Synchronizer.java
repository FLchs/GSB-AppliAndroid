package fr.cned.emdsgil.suividevosfrais;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Hashtable;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by francois on 3/24/18.
 */

public class Synchronizer {
    /**
     * Convertir un objet en json et l'envoyer au serveur.
     *
     * @param object Objet à sérialiser
     */
    private static Hashtable<Integer, FraisMois> myMap;

    public static void syncToServer(User user, Object object, final Context context) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(object);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://cned.francoislachese.fr/")
                .build();
        GsbAPI gsbAPI = retrofit.create(GsbAPI.class);

        HashMap<String, String> headermap = new HashMap<>();
        headermap.put("Content-type", "Application/json");

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonString);

        Call<ResponseBody> call = gsbAPI.post(body, user.getLogin(), user.getPassword());

        call.enqueue(new Callback<ResponseBody>() {

            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Toast.makeText(context, "Données synchronisées", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Erreur pendant la synchronisation", Toast.LENGTH_SHORT).show();
                }

                String json = (response.code() + " ");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });

    }

    public static Object syncFromServer(User user, final Context context) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://cned.francoislachese.fr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GsbAPI gsbAPI = retrofit.create(GsbAPI.class);

        HashMap<String, String> headermap = new HashMap<String, String>();
        headermap.put("Content-type", "Application/json");
        Call<Hashtable<Integer, FraisMois>> call = gsbAPI.read(headermap, user.getLogin(), user.getPassword());

        call.enqueue(new Callback<Hashtable<Integer, FraisMois>>() {

            public void onResponse(Call<Hashtable<Integer, FraisMois>> call, Response<Hashtable<Integer, FraisMois>> response) {
                // Vérification de la connexion
                if (response.code() == 200) {
                    // Si tout va bien, on récupére les données directement en tant que Hashtable grâce à Gson.
                    myMap = response.body();
                    // Et si les données ne sont pas vides, on cast directement.
                    if (myMap != null) {
                        Global.listFraisMois = myMap;
                    }
                    // Sinon, on créer la liste.
                    else  {
                        Global.listFraisMois = new Hashtable<>();
                    }

                } else {
                    Toast.makeText(context, "Erreur d'authentification", Toast.LENGTH_SHORT).show();
                    // TODO: 3/27/18  retour au login 
                }
            }

            @Override
            public void onFailure(Call<Hashtable<Integer, FraisMois>> call, Throwable t) {
            }

        });


        return null;

    }


}
