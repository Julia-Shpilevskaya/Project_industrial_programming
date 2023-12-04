package decorator;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class FileDecoratorEncryption extends DataSourceDECorator {

    public FileDecoratorEncryption(DataSource source) {
        super(source);
    }

    @Override
    public void writeData(String filename, String text) {
        super.writeData(filename, encode(text));
    }

    @Override
    public String readData(String filename) {
        return decode(super.readData(filename));
    }

    private static final String key = "aesEncryptionKey";
    private static final String initVector = "encryptionIntVec";
    public String encode(String data) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString((encrypted));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String decode(String data) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(data));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}

