import java.io.IOException;

abstract class DataSourceDecorator {
    protected DataSourceDecorator decoratedFile;
    protected String filename;

    public DataSourceDecorator(DataSourceDecorator decoratedFile) {
        this.decoratedFile = decoratedFile;
    }

    public abstract String read() throws IOException;
    public abstract void write(String content) throws IOException;
}