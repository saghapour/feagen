package ir.deltasink.feagen.common.utils;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class DatasetUtils {
    public static Dataset<Row> repartition(Dataset<Row> df, int partitions){
        if (partitions > 0)
            df = partitions > df.rdd().getNumPartitions() ?
                    df.repartition(partitions) :
                    df.coalesce(partitions);

        return df;
    }
}
