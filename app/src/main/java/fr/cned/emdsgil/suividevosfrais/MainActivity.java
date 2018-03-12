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

/**
 * Created by francois on 3/12/18.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //verifier si le login est stocké dans le serializer
        // si oui, on redirige vers main, sinon vers login

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

                // À faire, verification du login auprés de l'API
                if (login.equals("user") && password.equals("password")) {

                    // Si authentification, redirection vers le dashboard.
                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this, "Mauvais login/mot de passe", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
