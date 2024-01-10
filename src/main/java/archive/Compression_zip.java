package archive;

import file_types.JSON;
import file_types.TXT;
import file_types.XML;

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
                    String text="";

                    if(filename_out.endsWith(".txt")) {
                        TXT txt = new TXT();
                        text = txt.Read(filename_out);
                    }
                    else if(filename_out.endsWith(".xml")) {
                        XML xml = new XML();
                        text = xml.Read(filename_out);
                    }
                    else if(filename_out.endsWith(".json")) {
                        JSON json = new JSON();
                        text = json.Read(filename_out);
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
        try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zip_name+".zip"))) {
            String text="";

            if(filename_in.endsWith(".txt")) {
                TXT txt = new TXT();
                text = txt.Read(filename_in);
            }
            else if(filename_in.endsWith(".xml")) {
                XML xml = new XML();
                text = xml.Read(filename_in);
            }
            else if(filename_in.endsWith(".json")) {
                JSON json = new JSON();
                text = json.Read(filename_in);
            }

            ZipEntry entry1=new ZipEntry(filename_in);
            zout.putNextEntry(entry1);

            //byte[] buffer2 = new byte[text.getBytes()];

            zout.write(text.length());
            zout.closeEntry();
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}


