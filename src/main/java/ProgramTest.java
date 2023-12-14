import org.junit.Assert;
import org.junit.jupiter.api.Test;

class ProgramTest {
    @Test
    void evaluate() {
        String text ="5+10*3";
        String result = "5+10*3=35.0";
        Program pr = new Program();
        text = pr.evaluate(text);

        Assert.assertEquals(text,result);
    }

    @Test
    void evaluate_reg() {
        String text ="5*(7-4/2)";
        String result = "5*(7-4/2) = 25.0";
        Program pr = new Program();
        text = pr.evaluate_reg(text);

        Assert.assertEquals(text,result);
    }
}