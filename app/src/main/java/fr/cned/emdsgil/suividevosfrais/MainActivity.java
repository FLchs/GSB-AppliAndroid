package fr.cned.emdsgil.suividevosfrais;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by francois on 3/12/18.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //verifier si le login est stocké dans le serializer
        // si oui, on redirige vers main, sinon vers login
        Global.user = SaveUser.load(MainActivity.this);

        if (Global.user != null) {
            Synchronizer.syncFromServer(Global.user,MainActivity.this);

            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
            startActivity(intent);
        }


        setContentView(R.layout.activity_main);
        setTitle("GSB : Suivi des frais");
        // chargement des méthodes événementielles
        cmdLogin_clic();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Bouton de login
     */
    private void cmdLogin_clic() {

        findViewById(R.id.cmdLogin).setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {

                final String login = ((EditText) findViewById(R.id.txtLogin)).getText().toString();
                final String password = ((EditText) findViewById(R.id.txtPassword)).getText().toString();
                final Boolean permLog = ((CheckBox) findViewById(R.id.chbxSave)).isChecked();

                Toast.makeText(MainActivity.this, "Login en cours...", Toast.LENGTH_SHORT).show();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://cned.francoislachese.fr/")
                        .build();
                GsbAPI gsbAPI = retrofit.create(GsbAPI.class);

                HashMap<String, String> headermap = new HashMap<String, String>();
                headermap.put("Content-type", "Application/json");
                Call<ResponseBody> call = gsbAPI.login(headermap, login, password);

                call.enqueue(new Callback<ResponseBody>() {

                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        // Vérification de la connexion
                        if (response.code() == 200) {
                            Toast.makeText(MainActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
                            // Si le login est bon et que la checkbox "rester connecté" est coché, on stocke le login/mdp
                            if (permLog) {
                                // TODO: 3/20/18 Stockage en local du login/mdp
                                SaveUser.save(login, password, MainActivity.this);
                            }

                            Global.user = new User();
                            Global.user.setLoginPassword(login, password);
                            // Stockage de l'utilisateur pour pouvoir l'envoyer à chaques requettes.
                            Synchronizer.syncFromServer(Global.user, MainActivity.this);

                            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                            startActivity(intent);
                        }

                        else {
                            Toast.makeText(MainActivity.this, "Mauvais login ou mot de passe", Toast.LENGTH_SHORT).show();
                        }

                        String json = (response.code() + " ");
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                    }
                });
            }
        });
    }
}
