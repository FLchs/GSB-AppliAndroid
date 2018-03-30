package fr.cned.emdsgil.suividevosfrais;

import android.content.Context;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by francois on 3/29/18.
 * Classe permettant de stocker et récupérer un utilisateur.
 */

public class SaveUser {
    public static void save(String login, String password, Context context) {
        try {

            FileOutputStream file = context.openFileOutput("user.json", Context.MODE_PRIVATE);
            ObjectOutputStream oos;

            oos = new ObjectOutputStream(file);
            // Serialisation de l'utilisateur en json.
            Gson gson = new Gson();
            String jsonString = gson.toJson(Global.user);

            // Écriture de l'utilisateur dans le fichier.
            oos.writeObject(jsonString);
            oos.flush();
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User load(Context context) {
        try {
            // Ouverture du fichier contenant les identifiants.
            FileInputStream fich = context.openFileInput("user.json");
            ObjectInputStream ois;
            try {
                ois = new ObjectInputStream(fich);
                String returnjsn = (String) ois.readObject();
                try {
                    ois.close();
                    // Déserialisation de l'utilisateur
                    // Amélioration possible : utiliser directement le json pour l'authentification via API...
                    Gson gson = new Gson();
                    Global.user = gson.fromJson(returnjsn, User.class);
                    return Global.user;

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User delete(Context context) {
        try {

            FileOutputStream file = context.openFileOutput("user.json", Context.MODE_PRIVATE);
            ObjectOutputStream oos;
            oos = new ObjectOutputStream(file);

            // Écriture de l'utilisateur dans le fichier.
            oos.writeObject("");
            oos.flush();
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
