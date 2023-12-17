

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Compression_zipTest {

    /*@BeforeEach
    void setUp() {
    }
*/
    @Test
    void decompress() throws IOException {
        String zipFilename = "archive1.zip";
        String file_n = "file3.txt";

        File file3 = new File(file_n);

        try(BufferedWriter writer = new BufferedWriter((new FileWriter((file3)))))
        {
            writer.write("Test file 1");
        }

        try(ZipOutputStream zos = new ZipOutputStream((new FileOutputStream(zipFilename)));
        FileInputStream fis = new FileInputStream(file3);)
            {
                ZipEntry entry1 = new ZipEntry(file_n);
                zos.putNextEntry(entry1);

                byte[] buffer = new byte[1024];
                int length;
                while((length=fis.read(buffer))>0)
                {
                    zos.write(buffer,0,length);
                }
            }

        Compression_zip comp2 = new Compression_zip();
        file_n = "file4.txt";

        comp2.decompress(file_n,"archive1");

       // File exf = new File("file4"+File.separator+"file11.txt");
        File e = new File(file_n);

        assertTrue(e.exists());

        String c1 ="";
        try(FileReader reader = new FileReader(file_n))//чтение из файла
        {
            int c;
            while((c =reader.read())!=-1)
            {
                c1+=(char)c;
            }
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
            System.out.println("this place");
        }

        Assert.assertEquals("Test file 1",c1);

        e.delete();
        file3.delete();
    }

    @Test
    void compress() throws IOException {
        File file1 = new File("archive.txt");

        Compression_zip comp = new Compression_zip();

        String zipFilename = "file1.zip";

        comp.compress("archive.txt","file1");

        File zipFile = new File(zipFilename);

        assertTrue(zipFile.exists());

        System.out.println("Test successful");

        file1.delete();
        zipFile.delete();
    }
}