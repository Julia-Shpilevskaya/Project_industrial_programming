package decorator;

public class DataSourceDECorator implements DataSource {
    private DataSource wrappee;

    DataSourceDECorator(DataSource source) {
        this.wrappee = source;
    }

    @Override
    public String readData(String filename) {
        return wrappee.readData(filename);
    }

    @Override
    public void writeData(String filename, String text) {
        wrappee.writeData(filename, text);
    }
}