import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

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

    @Test
    void write_to_fail() {
        File file1 = new File("file10.txt");
        String text="Test write_from_file";

        Program pr = new Program();

        pr.write_to_fail(text,"file10.txt");

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
    }

    @Test
    void read_from_file() {
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

        Program pr = new Program();

        String txt_in_file = pr.read_from_file("file1","txt");

        Assert.assertEquals(text,txt_in_file);
    }
}