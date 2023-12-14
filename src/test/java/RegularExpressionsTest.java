import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegularExpressionsTest {

    @Test
    void evaluateExpression() {
        String expression ="5*(7-4/2)";
        String result = "5*(7-4/2) = 25.0";
        RegularExpressions reg = new RegularExpressions();
        expression = reg.EvaluateExpression(expression);

        Assert.assertEquals(expression,result);
    }
}