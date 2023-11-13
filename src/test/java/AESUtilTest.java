import org.junit.Assert;
import org.junit.jupiter.api.Test;

class AESUtilTest {

    @Test
    void encrypt() {
        String file_n = "file_enc.txt";
        String text ="3+1088*(1-3)";

        AESUtil aesu = new AESUtil();

        String text_e = aesu.encrypt(text,file_n);
        String text_res = "HZl+7h1OEGcVc9TNPxxQng==";

        Assert.assertEquals(text_e,text_res);
    }

    @Test
    void decrypt() {
        String text = "HZl+7h1OEGcVc9TNPxxQng==";

        AESUtil aesu = new AESUtil();

        String text_de = aesu.decrypt(text);
        String text_res ="3+1088*(1-3)";

        Assert.assertEquals(text_de,text_res);
    }
}