import archive.Compression_zip;
import encryption.AESUtil;
import expression.MatchParser;
import expression.RegularExpressions;
import file_types.JSON;
import file_types.TXT;
import file_types.XML;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;


public class Program {

    public static void main(String[] args) throws InvalidAlgorithmParameterException, NoSuchPaddingException,
            IllegalBlockSizeException, IOException, NoSuchAlgorithmException,
            BadPaddingException, InvalidKeyException {
        Scanner in = new Scanner(System.in);
        System.out.print("Input filename in ('input', 'file'): ");
        String filename = in.nextLine();

        System.out.print("Input type of the file in (txt, xml, json): ");
        String file_type = in.nextLine();

        String text = "";

        if (file_type.equals("txt")) {
            TXT txt = new TXT();
            text = txt.Read(filename + "." + file_type);
        } else if (file_type.equals("xml")) {
            XML xml = new XML();
            text = xml.Read(filename + "." + file_type);
        } else if (file_type.equals("json")) {
            JSON json = new JSON();
            text = json.Read(filename + "." + file_type);
        } else {
            System.out.print("ERROR.You can try again");
            return;
        }

        System.out.print("Math expression in file (" + filename + "." + file_type + ") : " + text);

        String num = "";

        System.out.print("\nWant use Decorator\nEnter 1\n Don't want use  \nEnter 2\n");
        num = in.nextLine();

        String n = "";

        if (num.equals("1")) {

        } else if (num.equals("2")) {
            System.out.print("Is your file archived?)\nEnter 1, if \"YES\"\nEnter 2, if \"NO\" ");
            n = in.nextLine();

            if (n.equals("1")) {
                System.out.print("\nIf zip, enter 10\n ");
                n = in.nextLine();

                if (n.equals("10")) {
                    System.out.print("\nInput  file name out from zip ('input.txt', 'fil.xml'): ");
                    String file_out_name = in.next();

                    Decompress_File_from_zip(file_out_name);
                } else {
                    System.out.print("\nERROR. Program can only zip. Your file isn't zip.");
                    return;
                }
            }

            System.out.print("\nIs your text encrypted?)\nEnter 1, if \"YES\"\nEnter 2, if \"NO\"");
            n = in.nextLine();

            if (n.equals("1")) {
                text = Decrypt_text(text);
            }
        }

        System.out.println("\nWant use regular\nEnter 1\n Want without regular\nEnter 2 : ");
        n = in.nextLine();

        if (n.equals("1")) {
            String result = evaluate_reg(text);
        } else if (n.equals("2")) {
            String result = evaluate(text);
        }

        if(num.equals("1")) {

        } else if(num.equals("2")) {
            System.out.print("\nDo you want to encrypt your text?\nYES - enter 1, NO - enter 2 ");
            n = in.nextLine();

            if (n.equals("1")) {
                text = Encrypt_text((text));
            }

            System.out.print("\nDo you want to archive your file? \nYES - enter 1, NO - enter 2 ");
            n = in.nextLine();

            if (n.equals("1")) {
                System.out.print("\nIf you want zip, enter 10\n  ");
                n = in.nextLine();

                if (n.equals("10")) {
                    System.out.print("Input (existing file) file name in zip ('input.txt', 'enter.json') : ");
                    String file_name = in.next();

                    Compress_File_to_zip(file_name);
                }
            }
        }

        System.out.print("\nDone");
    }

    public static String evaluate(String text) {
        String[] text2 = new String[1];
        text2[0] = text;
        String r = "";
        MatchParser p = new MatchParser();

        for (int i = 0; i < text2.length; i++) {
            try {
                System.out.println(text2[i] + "=" + p.Parse(text2[i]));
                r += text2[i] + "=" + p.Parse(text2[i]);
            } catch (Exception e) {
                System.err.println("Error while parsing '" + text2[i] + "' with message: " + e.getMessage());
            }
        }
        return r;
    }


    public static String evaluate_reg(String text) {
        RegularExpressions reg_expression = new RegularExpressions();
        String answer = reg_expression.EvaluateExpression(text);
        return answer;
    }


    public static void Compress_File_to_zip(String filename) {
        Scanner in = new Scanner(System.in);
        System.out.print("\nInput zip file name in ('input') : ");

        String zip_name = in.nextLine();
        Compression_zip.compress(filename, zip_name);
    }


    public static void Decompress_File_from_zip(String file_out_name) {
        Scanner in = new Scanner(System.in);
        System.out.print("\nInput  file name zip ('input'): ");

        String zip_name = in.nextLine();
        Compression_zip.decompress(file_out_name, zip_name);
    }


    public static String Encrypt_text(String text) {
        Scanner in = new Scanner(System.in);
        System.out.print("\nEnter name of encrypted file (j.txt) : ");

        String file_name_enc = in.nextLine();
        System.out.print("\nAfter encryption: ");

        String text_enc = AESUtil.encrypt(text, file_name_enc);
        System.out.print(text_enc);
        return text_enc;
    }


    public static String Decrypt_text(String text_enc) {
        System.out.print("\nAfter decryption: ");

        String text_dec = AESUtil.decrypt(text_enc);
        System.out.print(text_dec);
        return text_dec;
    }
}

