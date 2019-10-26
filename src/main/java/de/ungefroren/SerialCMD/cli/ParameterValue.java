package de.ungefroren.SerialCMD.cli;

import java.util.Optional;

public class ParameterValue extends Parameter {

    public ParameterValue(String name, char sh0rt) {
        super(name, sh0rt);
    }

    @Override
    public boolean isSet(String[] args) {
        return getValue(args).isPresent();
    }

    public Optional<String> getValue(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("--" + name)
                    || args[i].equalsIgnoreCase("-" + sh0rt)) {
                if (i + 1 < args.length && !args[i+1].startsWith("-")) {
                    return Optional.of(args[i+1]);
                }
            }
        }
        return Optional.empty();
    }
}
