
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


        /*JSON j = new JSON();

        j.Write(filename+"."+file_type, text);

        String d = j.Read(filename+"."+file_type);
        System.out.print(d);*/

        int n = 0;

        System.out.print("Want use Decorator\nEnter 1\n Don't want use  \nEnter 2\n");
        n = in.nextInt();

        if(n==1)
        {
            int answer;

            System.out.print("Is your file encrypted and compressed? ( YES = 1\\NO )\n");
            answer = in.nextInt();

            if(answer == 1)
            {
                //FileDecoratorEnc enc = new FileDecoratorEnc(filename+"."+file_type);

//                DataSourceDecorator enc = null;// = new DataSourceDecorator(enc);
//
//                FileDecoratorEnc enc1 = new FileDecoratorEnc(enc);
//                enc.write(text);
//
//                String t = enc.read();
//
//                System.out.print("Text in file: ");
//                System.out.print(t);
            }
            else
            {
                System.out.print("Is your file  compressed? ( YES\\NO )\n");
                //answer = in.next();

                //if(answer=="YES")
               // {
//                    FileDecoratorGzip file_dec = new FileDecoratorGzip(filename+"."+file_type);
//
//                    file_dec.decompress();
//                    String txt111 = file_dec.read();
                //}
            }
        }
        else if (n==2)
        {
            write_to_fail(text, filename);

            String txt_from_file = read_from_file(filename, file_type);
            System.out.print("Text in file: ");
            System.out.print(txt_from_file);

            System.out.print("Is your file archived?)\nEnter 3, if \"YES\"\nEnter 4, if \"NO\"");
            n = in.nextInt();
            if (n == 3) {
                System.out.print("\nWant zip, enter 10\n ");
                n = in.nextInt();
            }

            if (n == 10) {
                Compress_File_to_zip(filename);

                System.out.print("Input  file name out from zip (for instance, \"j.txt\"): ");
                String file_out_name = in.next();

                Decompress_File_from_zip(file_out_name);
            }

            System.out.print("\nDo you want to encrypt your text?\nYES - enter 1, NO - enter 2 ");
            n = in.nextInt();

            String text_enc = text;

            if (n == 1) {
                text_enc = Encrypt_text((text));
            }

            System.out.print("\nDo you want to decrypt your text? \nYES - enter 1, NO - enter 2 ");
            n = in.nextInt();

            String text_dec = text;

            if (n == 1) {
                text_dec = Decrypt_text(text_enc);
            }
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


    public static void write_to_fail(String text,String filename)
    {
        try(FileOutputStream fos=new FileOutputStream(filename))//запись в файл
        {
            byte[] buffer = text.getBytes();
            fos.write(buffer, 0, buffer.length);
            //System.out.println("The file has been written\n");
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }


    public static String read_from_file(String filename,String file_type)
    {
        String txt_in_file = "";
        try(FileReader reader = new FileReader(filename+"."+file_type))//чтение из файла
        {
            int c;
            while((c =reader.read())!=-1)
            {
                txt_in_file += (char)c;
            }
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }

        return txt_in_file;
    }


    public static void Compress_File_to_zip(String filename)
    {
        Scanner in = new Scanner(System.in);
        System.out.print("\nInput zip file name in : ");
        String zip_name = in.nextLine();

        Compression_zip.compress(filename, zip_name);
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
