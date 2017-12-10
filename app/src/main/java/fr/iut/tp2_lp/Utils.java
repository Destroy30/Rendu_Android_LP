package fr.iut.tp2_lp;

import android.webkit.MimeTypeMap;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by destr_000 on 04/12/2017.
 */

public class Utils {

    /**
     * Methode de hachage permettant de transfomer du texte en md5
     * @param s : String à hacher (dans notre cas d'applicaiton, un email)
     * @return
     */

    public static final String md5(final String s) {
        if(s == null) { //Si le string est null, on renvoi le hachage renvoyant au profil par défaut sur gravtar
            return "205e460b479e2e5b48aec07710c08d50?f=y";
        }
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Cette méthode permet de récupérer le type de fichier associé à une url
     * C'est utile pour savoir si on doit convertir un lien reçu en image (png ou gif) et l'afficher dans l'adpter et le holder
     * @param url url du fichier
     * @return Le type de données du fichier
     */
    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }
}
