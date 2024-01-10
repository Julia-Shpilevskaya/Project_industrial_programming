package expression;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpressions {

    public String EvaluateExpression(String text) {
        String r = text+" = ";
        text  = text.replaceAll("\\s+","");

        if(!text.matches("[0-9+\\-*/.()]+"))
            throw new IllegalArgumentException("Inadmissible symbols in expression.");

        String numberRegex = "\\d+(\\.\\d+)?";
        String operatorRegex = "[+\\-*/]";

        Pattern numberPattern = Pattern.compile(numberRegex);
        Pattern operatorPattern = Pattern.compile(operatorRegex);

        Stack<Double> numberStack = new Stack<>();
        Stack<String> operatorStack = new Stack<>();

        Matcher numberMatcher = numberPattern.matcher(text);
        Matcher operatorMatcher = operatorPattern.matcher(text);

        while(numberMatcher.find())
        {
            double number = Double.parseDouble(numberMatcher.group());
            numberStack.push(number);
        }

        while(operatorMatcher.find())
        {
            String operator = operatorMatcher.group();
            operatorStack.push(operator);
        }

        while(!operatorStack.isEmpty())
        {
            String oper = operatorStack.pop();
            double operand2 = numberStack.pop();
            double operand1 = numberStack.pop();
            double result = ApplyOperator(oper,operand1,operand2);
            numberStack.push(result);
        }

        r +=numberStack.pop();
        System.out.println(r);
        return r;
    }


    public static double ApplyOperator(String operator, double op1, double op2) {
        switch(operator)
        {
            case "+":
                return op1+op2;
            case "-":
                return op1-op2;
            case "*" :
                return op1*op2;
            case "/":
                return op1/op2;
            default:
                throw new IllegalArgumentException("Inadmissible operator.");
        }
    }
}

