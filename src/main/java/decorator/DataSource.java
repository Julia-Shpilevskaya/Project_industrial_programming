package decorator;

public interface DataSource {
    void writeData(String filename, String text);
    String readData(String filename);
}
