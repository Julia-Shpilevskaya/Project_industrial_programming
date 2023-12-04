//import java.io.IOException;
//
//class FileDecoratorEnc extends DataSourceDecorator {
//    private static final int KEY = 0xFF;
//
//    public FileDecoratorEnc(DataSourceDecorator decoratedFile) {
//        super(decoratedFile);
//    }
//
//    @Override
//    public void write(String content) throws IOException {
//        decoratedFile.write(encrypt(content));
//    }
//
//    @Override
//    public String read() throws IOException {
//        return decrypt(decoratedFile.read());
//    }
//
//    private String encrypt(String content) {
//        byte[] bytes = content.getBytes();
//        for (int i = 0; i < bytes.length; i++) {
//            bytes[i] ^= KEY;
//        }
//        return new String(bytes);
//    }
//
//    private String decrypt(String content) {
//        return encrypt(content);  // XOR encryption is symmetric
//    }
//}