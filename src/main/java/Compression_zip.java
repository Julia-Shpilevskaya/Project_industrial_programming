
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Compression_zip// extends DataSourceDecorator
{
    //private int compLevel = 6;


    //public  CompressionDecorator(DataSource source){
      //  super(source);
    //}

    //public int getCompressionLevel(){
      //  return compLevel;
    //}

    //public void setCompressionLevel(int value){
      //  compLevel=value;
    //}

    //@Override
    //public void writeData(String data){
      //  super.writeData(compress(data));
    //}

    //@Override
    //public String readData(){
      //  return decompress(super.readData());
    //}

    public static void decompress(String filename_out,String zip_name)
    {
        try(ZipInputStream zin = new ZipInputStream(new FileInputStream(zip_name+".zip")))
        {
            ZipEntry entry;
            String name;
            while((entry=zin.getNextEntry())!=null)
            {
                // распаковка
                FileOutputStream fout = new FileOutputStream(filename_out);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }

                try(FileInputStream fin2=new FileInputStream(filename_out))
                {
                    int i;
                    System.out.print("From file after decompress: ");
                    while((i=fin2.read())!=-1){

                        System.out.print((char)i);
                    }
                }
                catch(IOException ex){

                    System.out.println(ex.getMessage());
                }

                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
    }


    public static void compress(String filename_in, String zip_name,String file_type)
    {
        try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zip_name+".zip"));
            FileInputStream fis= new FileInputStream(filename_in+"."+file_type);)
        {
            ZipEntry entry1=new ZipEntry(filename_in+"."+file_type);
            zout.putNextEntry(entry1);
            // считываем содержимое файла в массив byte
            byte[] buffer2 = new byte[fis.available()];
            fis.read(buffer2);
            // добавляем содержимое к архиву
            zout.write(buffer2);
            // закрываем текущую запись для новой записи
            zout.closeEntry();
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
    }

}

