package ir.deltasink.feagen.common.db;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public interface ISource {
    Dataset<Row> loadData(SparkSession spark);
}
