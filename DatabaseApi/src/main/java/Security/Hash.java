package Security;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

public class Hash {
    public static void main(String[] args) throws Exception {
        String password = "olga1";
        PasswordConverter pass = new PasswordConverter();
        pass.convertToDatabaseColumn(password);

        System.out.println("skrót hasła: \"" + pass.convertToDatabaseColumn(password) + "\" XXXXXX") ;

    }
}