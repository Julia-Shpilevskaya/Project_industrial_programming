//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.zip.GZIPInputStream;
//import java.util.zip.GZIPOutputStream;
//
//public class FileDecoratorGzip extends DataSourceDecorator {
//    public FileDecoratorGzip(DataSourceDecorator decoratedFile) {
//        super(decoratedFile);
//    }
//
//    public FileDecoratorGzip (String filename) {
//        super(null);
//        this.filename = filename;
//    }
//
//    @Override
//    public void write(String content) throws IOException {
//        decoratedFile.write(content);
//        compress();
//    }
//
//    @Override
//    public String read() throws IOException {
//        decompress();
//        return decoratedFile.read();
//    }
//
//    public void compress() throws IOException {
//        String inputFilename = decoratedFile.filename;
//        String outputFilename = decoratedFile.filename + ".gz";
//
//        try (FileInputStream fis = new FileInputStream(inputFilename);
//             GZIPOutputStream gzos = new GZIPOutputStream(new FileOutputStream(outputFilename))) {
//
//            byte[] buffer = new byte[1024];
//            int len;
//            while ((len = fis.read(buffer)) != -1) {
//                gzos.write(buffer, 0, len);
//            }
//        }
//    }
//
//    public void decompress() throws IOException {
//        String inputFilename = decoratedFile.filename + ".gz";
//        String outputFilename = decoratedFile.filename;
//
//        try (FileInputStream fis = new FileInputStream(inputFilename);
//             GZIPInputStream gzis = new GZIPInputStream(fis);
//             FileOutputStream fos = new FileOutputStream(outputFilename)) {
//
//            byte[] buffer = new byte[1024];
//            int len;
//            System.out.print("From file after decompress: ");
//            while ((len = gzis.read(buffer)) != -1)
//            {
//                fos.write(buffer, 0, len);
//                System.out.print((char)len);
//            }
//        }
//    }
//}
