package de.ungefroren.SerialCMD;

import de.ungefroren.SerialCMD.cli.CLI;
import de.ungefroren.SerialCMD.threads.InputThread;
import de.ungefroren.SerialCMD.threads.OutputThread;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    public static final BufferedReader SYS_IN = new BufferedReader(new InputStreamReader(System.in));
    private static Thread output,input;

    public static void main(String[] args) throws Exception {
        CLI CLI = new CLI(args);
        if (CLI.port.isPresent()) {
            Serial port = CLI.port.get();
            port.openConnection();
            output = new OutputThread(port.getIn());
            input = new InputThread(port.getOut());
            System.out.printf("Connected to %s at %d baud\n", port.getPortName(), port.getBaudRate());
            System.out.println("------------------------------------------------------------------------");
            input.start();
            output.start();
        }
    }

    public static Thread getInput() {
        return input;
    }

    public static Thread getOutput() {
        return output;
    }
}
