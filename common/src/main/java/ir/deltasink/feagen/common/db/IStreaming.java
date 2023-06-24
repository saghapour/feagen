package ir.deltasink.feagen.common.db;

import org.apache.spark.sql.SparkSession;

public interface IStreaming {
    void start(SparkSession spark, StreamingSteps applySteps);
}
