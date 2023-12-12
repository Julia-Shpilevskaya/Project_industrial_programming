package file_types;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class TXT {
    public void Write(String text,String filename) {
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


    public  String Read(String filename) {
        String txt_in_file = "";
        try(FileReader reader = new FileReader(filename))//чтение из файла
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
}
