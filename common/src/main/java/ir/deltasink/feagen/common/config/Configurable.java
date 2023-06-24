package ir.deltasink.feagen.common.config;

import ir.deltasink.feagen.common.constant.Variables;

public abstract class Configurable {
    protected final IConfig config;
    protected final boolean isBackfillMode;

    protected Configurable(IConfig config, boolean isBackfillModel){
        this.config = config;
        this.isBackfillMode = isBackfillModel;
    }

    public <T> T getConfigValue(String configKey) {
        return getConfigValue(configKey, null);
    }

    public <T> T getConfigValue(String configKey, T defaultValue) {
        if (this.isBackfillMode &&
                config.containsKey(String.format("%s.%s", Variables.BACKFILL_PREFIX, configKey)))
            return config.getAs(String.format("%s.%s", Variables.BACKFILL_PREFIX, configKey));
        else if (config.containsKey(configKey))
            return config.getAs(configKey);
        else
            return defaultValue;
    }
}
