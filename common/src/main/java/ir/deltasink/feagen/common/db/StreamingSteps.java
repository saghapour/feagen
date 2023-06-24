package ir.deltasink.feagen.common.db;

import ir.deltasink.feagen.common.exception.MandatoryKeyException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

@FunctionalInterface
public interface StreamingSteps {
    Dataset<Row> applySteps(Dataset<Row> df) throws MandatoryKeyException;
}
