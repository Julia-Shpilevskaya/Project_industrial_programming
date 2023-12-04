package decorator;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileDecoratorZip extends DataSourceDECorator
{
    private int compLevel = 6;

    public FileDecoratorZip(DataSource source) {
        super(source);
    }

    public int getCompressionLevel() {
        return compLevel;
    }

    public void setCompressionLevel(int value) {
        compLevel = value;
    }

    @Override
    public void writeData(String filename, String text) {
        super.writeData(filename, text);
        try{
            compress(filename);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
    @Override
    public String readData(String filename) {
        try {
            decompress(filename);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return super.readData(filename);
    }
    public static void compress(String filename) throws IOException {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(filename));
            File fileToZip = new File(filename);
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry entry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(entry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            zipOut.close();
            fis.close();
            fos.close();
        }
        catch(Exception ex) {

            System.out.println(ex.getMessage());
        }
    }


    public static void decompress(String filename) throws IOException {
        File destDir = new File("./");
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(filename));
        ZipEntry entry = zis.getNextEntry();
        while(entry!=null){
            File newFile = newFile(destDir, entry);
            if (!newFile.isDirectory() && !newFile.mkdirs()) {
                throw new IOException("Failed to create directory " + newFile);
            }
            else {
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }

                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            entry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }

    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

}