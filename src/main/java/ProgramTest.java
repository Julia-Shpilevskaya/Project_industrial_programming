import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

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

        pr.read_from_file("file1","txt");

        Scanner in = new Scanner(System.in);

        String txt_in_file = in.nextLine();

        Assert.assertEquals(text,txt_in_file);

        in.close();
    }
}