package decorator;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileDecoratorWriteRead  implements DataSource {
    private String name;

    @Override
    public void writeData(String filename, String text) {
        try(FileWriter writer = new FileWriter(filename, false))
        {
            // запись всей строки
            writer.write(text);
            //writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public String readData(String filename) {
        String text = "";
        try(FileReader reader = new FileReader(filename))
        {
            int c;
            while((c=reader.read())!=-1){
                text += (char)c;
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        return text;
    }
}

