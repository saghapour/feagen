package ir.deltasink.feagen.common.db;

import ir.deltasink.feagen.common.config.Configurable;
import ir.deltasink.feagen.common.config.IConfig;
import ir.deltasink.feagen.common.utils.DatasetUtils;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public abstract class Repartitioning extends Configurable {
    protected Repartitioning(IConfig config, boolean isBackfillModel) {
        super(config, isBackfillModel);
    }

    protected Dataset<Row> repartition(Dataset<Row> df, int partitions) {
        return DatasetUtils.repartition(df, partitions);
    }

}
