package de.ungefroren.SerialCMD.cli;

import java.util.Arrays;

public class Parameter {
    public final String name;
    public final char sh0rt;

    public Parameter(String name, char sh0rt) {
        this.name = name;
        this.sh0rt = sh0rt;
    }

    public boolean isSet(String[] args) {
        return Arrays.stream(args)
                .anyMatch(arg -> arg.equalsIgnoreCase("--" + name)
                        || arg.equalsIgnoreCase("-" + sh0rt));
    }
}
