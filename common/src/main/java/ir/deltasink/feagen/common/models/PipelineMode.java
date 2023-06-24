package ir.deltasink.feagen.common.models;

import java.util.Locale;

public enum PipelineMode {
    BATCH,
    STREAMING;

    public static PipelineMode parse(String mode){
        return PipelineMode.valueOf(mode.toUpperCase(Locale.ROOT));
    }
}
