package fr.cned.emdsgil.suividevosfrais;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.Hashtable;
import java.util.Map;

/**
 * Classe qui permet de sérialiser et désérialiser des objets
 *
 * @author Emds
 */
abstract class Serializer {

    /**
     * Sérialisation d'un objet
     *
     * @param object Objet à sérialiser
     */
    public static void serialize(Object object, Context context) {
        try {
            FileOutputStream file = context.openFileOutput(Global.filename, Context.MODE_PRIVATE);
            ObjectOutputStream oos;

            oos = new ObjectOutputStream(file);
            Gson gson = new Gson();
            String jsonString = gson.toJson(object);

            oos.writeObject(jsonString);
            oos.flush();
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Désérialisation d'un objet
     *
     * @param context Accès au contexte de l'application
     * @return Objet déserialisé
     */
    public static Object deSerialize(Context context) {
        try {
            FileInputStream fich = context.openFileInput(Global.filename);

            ObjectInputStream ois;

            try {
                ois = new ObjectInputStream(fich);
                String returnjsn = (String) ois.readObject();
                try {
                    ois.close();
                    Gson gson = new Gson();
                    Type type = new TypeToken<Hashtable<Integer, FraisMois>>() {
                    }.getType();

                    Map<String, String> myMap = gson.fromJson(returnjsn, type);

                    Log.d("lol", "serialize() returned: " + myMap);
                    return myMap;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // fichier non trouvé
            e.printStackTrace();
        }
        return null;
    }

}
