package ir.deltasink.feagen.common.db;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public interface ISink {
    void writeData(SparkSession spark, Dataset<Row> df);
}
