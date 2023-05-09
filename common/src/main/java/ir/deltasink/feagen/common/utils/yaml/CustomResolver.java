package ir.deltasink.feagen.common.utils.yaml;

import org.yaml.snakeyaml.resolver.Resolver;

/**
 * A class that extends the Resolver class from SnakeYaml library and customizes the implicit resolvers.
 * The custom resolver removes the 'o' character from the yamlImplicitResolvers map, which means that values like "on" or "off" will not be resolved as booleans.
 */
public class CustomResolver extends Resolver {
    /**
     * Constructs a new CustomResolver object by calling the super constructor and removing the 'o' character from the yamlImplicitResolvers map.
     */
    public CustomResolver(){
        super();
        super.yamlImplicitResolvers.remove('o');
    }
//    @Override
//    protected void addImplicitResolvers() {
//        // Because of `on` in `join` processor, we should override BOOL pattern of SnakeYaml,
//        // The original pattern is: ^(?:yes|Yes|YES|no|No|NO|true|True|TRUE|false|False|FALSE|on|On|ON|off|Off|OFF)$
//        // and original first is: yYnNtTfFoO
//        this.addImplicitResolver(Tag.BOOL, Pattern.compile("^(?:yes|Yes|YES|no|No|NO|true|True|TRUE|false|False|FALSE)$"), "yYnNtTfF", 10);
//        this.addImplicitResolver(Tag.INT, INT, "-+0123456789");
//        this.addImplicitResolver(Tag.FLOAT, FLOAT, "-+0123456789.");
//        this.addImplicitResolver(Tag.MERGE, MERGE, "<", 10);
//        this.addImplicitResolver(Tag.NULL, NULL, "~nN\u0000", 10);
//        this.addImplicitResolver(Tag.NULL, EMPTY, (String)null, 10);
//        this.addImplicitResolver(Tag.TIMESTAMP, TIMESTAMP, "0123456789", 50);
//        this.addImplicitResolver(Tag.YAML, YAML, "!&*", 10);
//    }
}
