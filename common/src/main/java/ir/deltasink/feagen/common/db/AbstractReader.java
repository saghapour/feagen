package ir.deltasink.feagen.common.db;

import ir.deltasink.feagen.common.config.IConfig;
import ir.deltasink.feagen.common.constant.Variables;
import ir.deltasink.feagen.common.exception.MandatoryKeyException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractReader extends Repartitioning implements ISource {
    protected final boolean pushDownPredicate;
    protected final boolean pushDownAggregate;
    protected final Integer numberOfPartitions;
    protected final Map<String, String> sparkOptions = new HashMap<>();

    public AbstractReader(IConfig inputConfig, boolean isBackfillMode) throws MandatoryKeyException {
        super(inputConfig, isBackfillMode);
        this.pushDownPredicate = inputConfig.getAs(Variables.SPARK_PUSHDOWN_PREDICATE, true);
        this.pushDownAggregate = inputConfig.getAs(Variables.SPARK_PUSHDOWN_AGGREGATE, false);
        this.numberOfPartitions = inputConfig.getAs(Variables.PARTITIONS, 0);
        this.setSparkOptions();
    }

    private void setSparkOptions(){
        if (config.containsKey(Variables.SPARK_OPTIONS)) {
            Map<String, Object> sparkOptions = config.getConfig(Variables.SPARK_OPTIONS).getDict();
            for (String key: sparkOptions.keySet()){
                // It's not allowed to specify dbtable in spark_options.
                if (key.equals(Variables.SPARK_OPTIONS_DB_TABLE))
                    continue;

                this.sparkOptions.put(key, sparkOptions.get(key).toString());
            }
        }
    }

    public abstract Dataset<Row> loadData(SparkSession spark);

//    public Map<String, String> getSparkOptions() {
//        return sparkOptions;
//    }
}
