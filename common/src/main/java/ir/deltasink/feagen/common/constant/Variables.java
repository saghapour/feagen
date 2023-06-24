package ir.deltasink.feagen.common.constant;

public class Variables {
    public final static String FEAGEN_DEPLOYMENT = "FEAGEN_DEPLOYMENT";

    public final static String TIMEZONE = "timezone";

    public final static String CONFIG_PROVIDER = "config.provider";

    public final static String DRIVERS_CONFIG_FILE = "drivers";
    public final static String DB_CONFIG_FILE = "db";
    public final static String SPARK_CONFIG_FILE = "spark";
    public final static String PIPELINE_CONFIG_FILE = "pipeline";

    public final static String DRIVER_CONNECTION_TEMPLATE = "connection.template";
    public final static String DRIVER_DRIVER_NAME = "driver.name";
    public final static String DRIVER_SUPPORT_BULK_INSERT = "bulk.insert";

    public final static String PIPELINE_MODE = "mode";
    public final static String PIPELINE_INPUT = "input";
    public final static String PIPELINE_OUTPUT = "output";
    public final static String PIPELINE_STEPS = "steps";
    public final static String PIPELINE_SPARK_CONFIG = "spark.config";
    public final static String PIPELINE_SPARK_CONTEXT_CONFIG = "sparkContext.config";
    public final static String PIPELINE_SPARK_CONTEXT_HADOOP_CONFIG = "sparkContext.hadoop.config";
    public final static String PIPELINE_SPARK_SQL_SET = "spark.sql.set.commands";

    public final static String SPARK_SESSION_CONFIG = "session.config";

    public final static String DATASET = "df";
    public final static String JOIN_LEFT_DATASET = "left";
    public final static String JOIN_RIGHT_DATASET = "right";
    public final static String JOIN_ON = "on";
    public final static String JOIN_TYPE = "how";
    public final static String JOIN_DROP_FROM_OUTPUT = "drop.columns";
    public final static String SAVE_AS_DATASET = "save.as";
    public final static String BACKFILL_PREFIX = "backfill";
    public final static String SQL_COLUMNS = "columns";
    public final static String SQL_WHERE = "where";

    public final static String SOURCE = "source";
    public final static String TARGET = "target";
    public final static String PARTITION_BY = "partition.by";
    public final static String DB_NAME = "db.name";
    public final static String TABLE_NAME = "table.name";
    public final static String WRITE_MODE = "mode";
    public final static String SOURCE_SQL = "sql";
    public final static String SOURCE_PATH = "path";
    public final static String SOURCE_FETCH_SIZE = "fetch.size";
    public final static String SPARK_OPTIONS = "spark.options";
    public final static String SPARK_OPTIONS_DB_TABLE = "dbtable";

    public final static String SOURCE_JDBC_PARTITION_COLUMN = "partitionColumn";
    public final static String SOURCE_JDBC_LOWER_BOUND = "lowerBound";
    public final static String SOURCE_JDBC_UPPER_BOUND = "upperBound";
    public final static String SOURCE_JDBC_NUM_PARTITIONS = "numPartitions";

    public final static String DELTA_OVERWRITE_CONDITION = "delta.overwrite.condition";
    public final static String DELTA_ORDER_COLUMNS = "delta.order.columns";
    public final static String DELTA_UPSERT_BOUNDARY = "delta.upsert.boundary";
    public final static String DELTA_UPSERT_MATCHED_CONDITION = "delta.upsert.matched.condition";
    public final static String DELTA_UPSERT_NOT_MATCHED_CONDITION = "delta.upsert.not.matched.condition";
    public final static String DELTA_UPSERT_NONE_VALUE = "none";
    public final static String DELTA_UPSERT_DELETE_VALUE = "delete";
    public final static String DELTA_UPSERT_UPDATE_VALUE = "update";
    public final static String DELTA_UPSERT_UPDATE_ALL_VALUE = "updateAll";
    public final static String DELTA_UPSERT_INSERT_VALUE = "insert";
    public final static String DELTA_UPSERT_INSERT_ALL_VALUE = "insertAll";

    public final static String SINK_GATHER_STATS = "gather.stats";
    public final static String SINK_ENABLE_BATCH_WRITE = "enable.batch";
    public final static String SINK_WRITE_BATCH_SIZE = "batch.size";
    public final static String SINK_TYPE_CONSOLE = "console";


    public final static String DB_ENGINE = "engine";
    public final static String DB_TYPE = "type";

    public final static String HIVE_ENGINE = "hive";
    public final static String HIVE_TABLE_MODE = "table.mode";
    public final static String HIVE_TABLE_OPTIONS = "table.options";
    public final static String HIVE_COMPRESSION = "compression";

    public final static String PARTITIONS = "partitions";
    public final static String SPARK_PUSHDOWN_PREDICATE = "pushDownPredicate";
    public final static String SPARK_PUSHDOWN_AGGREGATE = "pushDownAggregate";

    public final static String AGGREGATION_AGGS = "aggs";
    public final static String AGGREGATION_GROUP_BY = "group.by";

    public final static String SQL_QUERY = "query";
    public final static String SQL_QUERY_ON_DFS = "queryOnDfs";
    public final static String SQL_LIMIT = "limit";
    public final static String SQL_DISTINCT = "distinct";

    public final static String SQL_ASTERISK = "*";
}
