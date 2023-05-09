package ir.deltasink.feagen.common.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class FileConfig implements Serializable {
    private String type;
    private String path;

}
