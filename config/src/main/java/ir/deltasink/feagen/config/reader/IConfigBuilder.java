package ir.deltasink.feagen.config.reader;

public interface IConfigBuilder {
    IConfigBuilder set(String key, Object value);
    IConfig getOrCreate();
}
