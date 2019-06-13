package Security;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.AttributeConverter;

public class PasswordConverter implements AttributeConverter<String,String> {

    public String convertToDatabaseColumn(String s) {
        byte[] hash = DigestUtils.sha(s.getBytes());
        String strHash=Base64.encodeBase64String(hash);
        strHash=strHash.replace("\r\n","");
        return strHash;
    }

    public String convertToEntityAttribute(String s) {
        return s;
    }


}