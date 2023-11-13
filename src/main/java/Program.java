
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Program {

    public static void main(String[] args) throws InvalidAlgorithmParameterException, NoSuchPaddingException,
                                                   IllegalBlockSizeException, IOException, NoSuchAlgorithmException,
                                                   BadPaddingException, InvalidKeyException {
        Scanner in = new Scanner(System.in);
        System.out.print("Input filename in: ");
        String filename = in.nextLine();

        System.out.print("Input type of the file in(txt,xml,json): ");
        String file_type = in.nextLine();

        System.out.print("Input text in: ");
        String text = in.nextLine();

        write_to_fail(text,filename,file_type);

        read_from_file(filename,file_type);

        int n = 0;

        System.out.print("Is your file archived?)\nEnter 3, if \"YES\"\nEnter 4, if \"NO\"");
        n = in.nextInt();
        if(n==3)
        {
            System.out.print("\nWant to zip, enter 1\n ");
            n = in.nextInt();
        }

        if(n==1)
        {
            Compress_File_to_zip(filename, file_type);

            System.out.print("Input  file name out from zip (for instance, \"j.txt\"): ");
            String file_out_name = in.next();

            Decompress_File_from_zip(file_out_name);
        }

        System.out.print("\nDo you want to encrypt your text?\nYES - enter 1, NO - enter 2 ");
        n = in.nextInt();

        String text_enc = text;

        if(n==1)
        {
            text_enc = Encrypt_text((text));
        }

        System.out.print("\nDo you want to decrypt your text? \nYES - enter 1, NO - enter 2 ");
        n = in.nextInt();

        String text_dec = text;

        if(n==1)
        {
            text_dec = Decrypt_text(text_enc);
        }

        System.out.println("\nInput 1(regular) or 2(without regular) : ");
        n = in.nextInt();

        if(n == 1)
        {
            String res =add_reg(text);
        }
        else if(n == 2)
        {
            String res =add(text);
        }
    }


    public static String add(String text)
    {
        String[] text2 = new String[1];
        text2[0] = text;
        String r = "";
        MatchParser p = new MatchParser();
        for( int i = 0; i < text2.length; i++){
            try{
                System.out.println( text2[i] + "=" + p.Parse( text2[i] ) );
                r += text2[i] + "=" + p.Parse( text2[i] );

            }catch(Exception e){
                System.err.println( "Error while parsing '"+text2[i]+"' with message: " + e.getMessage() );
            }
        }
        return r;
    }


    public static String add_reg(String text)
    {
        String r ="";

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
            double result = applyOperator(oper,operand1,operand2);
            numberStack.push(result);
        }

        r +=numberStack.pop();

        System.out.println(r);

        return r;
    }


    public static double applyOperator(String operator,double op1,double op2)
    {
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


    public static void write_to_fail(String text,String filename,String file_type)
    {
        try(FileOutputStream fos=new FileOutputStream(filename+"."+file_type))//запись в файл
        {
            byte[] buffer = text.getBytes();

            fos.write(buffer, 0, buffer.length);
            System.out.println("The file has been written\n");
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }


    public static void read_from_file(String filename,String file_type)
    {
        try(FileReader reader = new FileReader(filename+"."+file_type))//чтение из файла
        {
            int c;
            System.out.print("Text in file: ");
            while((c =reader.read())!=-1)
            {
                System.out.print((char)c);//то что в файле
            }
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }


    public static void Compress_File_to_zip(String filename,String file_type)
    {
        Scanner in = new Scanner(System.in);
        System.out.print("\nInput zip file name in : ");
        String zip_name = in.nextLine();

        Compression_zip.compress(filename, zip_name, file_type);
    }


    public static void Decompress_File_from_zip(String file_out_name)
    {
        Scanner in = new Scanner(System.in);
        System.out.print("\nInput  file name zip (for instance, \"j\"): ");
        String zip_name = in.nextLine();

        Compression_zip.decompress(file_out_name, zip_name);
    }


    public static String Encrypt_text(String text)
    {
        Scanner in = new Scanner(System.in);
        System.out.print("\nEnter name of encrypted file (j.txt) : ");
        String file_name_enc = in.nextLine();
        System.out.print("\nAfter encryption: ");

        String text_enc = AESUtil.encrypt(text, file_name_enc);

        System.out.print(text_enc);

        return text_enc;
    }


    public static String Decrypt_text(String text_enc)
    {
        System.out.print("\nAfter decryption: ");

        String text_dec = AESUtil.decrypt(text_enc);

        System.out.print(text_dec);

        return text_dec;
    }
}
