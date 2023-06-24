package ir.deltasink.feagen.common.db;

import ir.deltasink.feagen.common.config.Configurable;
import ir.deltasink.feagen.common.config.IConfig;
import ir.deltasink.feagen.common.constant.Defaults;
import ir.deltasink.feagen.common.constant.Variables;
import ir.deltasink.feagen.common.utils.DatasetUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

@Slf4j
public abstract class AbstractWriter extends Repartitioning implements ISink {
    protected final String mode;
    protected final int partitions;

    public AbstractWriter(IConfig outputConfig, boolean isBackfillMode) {
        super(outputConfig, isBackfillMode);
        this.mode = outputConfig.getAs(Variables.WRITE_MODE, Defaults.SOURCE_WRITE_MODE);
        this.partitions = outputConfig.getAs(Variables.PARTITIONS, 0);
        log.info("Write to output is{} in backfill mode", isBackfillMode ? "" : " not");
        log.info("Write mode is {}", this.mode);
    }

    public abstract void writeData(SparkSession spark, Dataset<Row> df);
}

