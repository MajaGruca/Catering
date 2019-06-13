package Security;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

public class Hash {
    public static void main(String[] args) throws Exception {
        String password = "user";
        MessageDigest md = null;
        md = MessageDigest.getInstance("SHA");
        byte[] passwordBytes = password.getBytes();
        byte[] hash = md.digest(passwordBytes);
        String base64String = Base64.encodeBase64String(hash);
        String sameCharactery = "0DPiKuNIrrVmD8IUCuw1hQxNqZc=";

        System.out.println("skrót hasła: \"" + base64String + "\" XXXXXX") ;

    }
}