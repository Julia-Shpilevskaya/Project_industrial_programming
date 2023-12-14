import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Compression_zip {
    public static void decompress(String filename_out,String zip_name)
    {
        try(ZipInputStream zin = new ZipInputStream(new FileInputStream(zip_name+".zip")))
        {
            ZipEntry entry;
            String name;

            while((entry=zin.getNextEntry())!=null)
            {
                FileOutputStream fout = new FileOutputStream(filename_out);
                for (int c = zin.read(); c != -1; c = zin.read())
                {
                    fout.write(c);
                }

                try(FileInputStream fin2=new FileInputStream(filename_out))
                {
                    int i;
                    System.out.print("From file after decompress: ");

                    while((i=fin2.read())!=-1)
                    {
                        System.out.print((char)i);
                    }
                }
                catch(IOException ex)
                {
                    System.out.println(ex.getMessage());
                }

                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }


    public static void compress(String filename_in, String zip_name)
    {
        try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zip_name+".zip"));
            FileInputStream fis= new FileInputStream(filename_in);)
        {
            ZipEntry entry1=new ZipEntry(filename_in);
            zout.putNextEntry(entry1);

            byte[] buffer2 = new byte[fis.available()];
            fis.read(buffer2);
            zout.write(buffer2);
            zout.closeEntry();
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}

