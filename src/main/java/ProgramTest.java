import org.junit.Assert;
import org.junit.jupiter.api.Test;

class ProgramTest {
    @Test
    void add() {
        String text ="5+10*3";
        String result = "5+10*3=35.0";
        Program pr = new Program();
        text = pr.add(text);

        Assert.assertEquals(text,result);
    }

    @Test
    void add_reg() {
        String text ="5*(7-4/2)";
        String result = "25.0";
        Program pr = new Program();
        text = pr.add_reg(text);

        Assert.assertEquals(text,result);
    }
}