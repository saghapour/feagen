package ir.deltasink.feagen.common.constant;

import java.util.TimeZone;

public class Defaults {
    public final static String FEAGEN_DEPLOYMENT = "production";
    public final static String TIMEZONE = TimeZone.getDefault().getID();
    public final static String CONFIG_PROVIDER = "embedded";

    public final static Integer SOURCE_JDBC_NUM_PARTITIONS = 100;
    public final static String SPARK_CHECKPOINT_LOCATION = "/user/spark/feagen/checkpoints/";
    public final static String SPARK_KAFKA_STARTING_OFFSET = "earliest";
    public final static boolean SPARK_KAFKA_CONFLUENT_MESSAGE = false;
    public final static Integer SPARK_KAFKA_BATCH_INTERVAL_MS = 1000;
    public final static Integer SPARK_KAFKA_MAX_OFFSET_PER_TRIGGER = 100000;
    public final static String SOURCE_WRITE_MODE = "Append";
    public final static String HIVE_COMPRESSION = "snappy";

}
