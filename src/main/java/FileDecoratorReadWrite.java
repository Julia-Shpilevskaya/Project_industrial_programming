//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//
//public class FileDecoratorReadWrite extends DataSourceDecorator {
//       public FileDecoratorReadWrite (String filename) {
//        super(null);
//        this.filename = filename;
//    }
//
//    @Override
//    public void write(String content) throws IOException {
//        try (FileWriter writer = new FileWriter(filename)) {
//            writer.write(content);
//        }
//    }
//
//    @Override
//    public String read() throws IOException {
//        StringBuilder content = new StringBuilder();
//        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                content.append(line);
//                content.append(System.lineSeparator());
//            }
//        }
//        return content.toString();
//    }
//}
