package file_types;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

class TXTTest {

    @Test
    void Write() {
        File file1 = new File("file10.txt");
        String text="Test write_from_file";

        TXT txt = new TXT();

        txt.Write(text,"file10.txt");

        String txt_in_file = "";

        try(FileReader reader = new FileReader("file10.txt"))//чтение из файла
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

        Assert.assertEquals(text,txt_in_file);

        file1.delete();
    }

    @Test
    void Read() {
        File file1 = new File("file1.txt");
        String text="Test read_from_file";

        try(FileOutputStream fos=new FileOutputStream(file1))//запись в файл
        {
            byte[] buffer = text.getBytes();
            fos.write(buffer, 0, buffer.length);
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }

        TXT txt = new TXT();

        String txt_in_file = txt.Read("file1.txt");

        Assert.assertEquals(text,txt_in_file);

        file1.delete();
    }
}