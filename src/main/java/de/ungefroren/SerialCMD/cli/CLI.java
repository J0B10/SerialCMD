package de.ungefroren.SerialCMD.cli;

import de.ungefroren.SerialCMD.Main;
import de.ungefroren.SerialCMD.Serial;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class CLI {

    private static final Parameter
            HELP = new Parameter("help", 'h'),
            LIST = new Parameter("list", 'l');
    private static final ParameterValue
            PORT = new ParameterValue("port", 'p'),
            BAUDRATE = new ParameterValue("baud", 'b');

    public final Optional<Serial> port;

    public CLI(String[] args) throws IOException {
        if (HELP.isSet(args)) {
            System.out.println(
                    "SerialCMD 1.0\n" +
                            "Usage: serial [options]\n" +
                            "  -h, --help\n" +
                            "                show this help message\n" +
                            "  -l, --list\n" +
                            "                list connected serial devices\n" +
                            "  -p, --port <value>\n" +
                            "                open monitor for a specific port\n" +
                            "  -b, --baud\n" +
                            "                set the baud rate for the connection (default 9600)");
            port = Optional.empty();
            return;
        }
        if (LIST.isSet(args)) {
            Serial.listCOMPorts().stream().sorted()
                    .forEachOrdered(port -> System.out.println(port.getPortName() + " - " + port.getPortDescription()));
            port = Optional.empty();
            return;
        }
        if (PORT.isSet(args)) {
            String find = PORT.getValue(args).get();
            port = Serial.listCOMPorts().parallelStream().filter(p -> p.getPortName().equalsIgnoreCase(find)).findAny();
            if (!port.isPresent()) {
                System.out.println("No serial device connected to " + find + "!");
                return;
            }
        } else {
            List<Serial> ports = Serial.listCOMPorts();
            if (ports.isEmpty()) {
                System.out.println("No serial devices detected!");
                port = Optional.empty();
                return;
            } else if (ports.size() == 1) {
                port = Optional.of(ports.get(0));
            } else {
                System.out.println("Please select a port by typing a number:\n");
                ports.sort(Serial::compareTo);
                for (int i = 1; i <= ports.size(); i++) {
                    Serial p = ports.get(i - 1);
                    System.out.printf("(%d): %s - %s\n", i, p.getPortName(), p.getPortDescription());
                }
                Serial p;
                while (true) {
                    String line = Main.SYS_IN.readLine();
                    try {
                        p = ports.get(Integer.parseInt(line) - 1);
                        break;
                    } catch (NumberFormatException | IndexOutOfBoundsException ignored) {
                    }
                }
                port = Optional.of(p);
            }
        }
        if (BAUDRATE.isSet(args)) {
            String rate = BAUDRATE.getValue(args).get();
            try {
                port.get().setBaudRate(Integer.parseInt(rate));
            } catch (NumberFormatException e) {
                System.out.println("Invalid baud rate!");
                return;
            }
        } else {
            System.out.println("Please enter baud rate (default 9600):");
            while (true) {
                String line = Main.SYS_IN.readLine();
                if (line.isEmpty()) break;
                else {
                    try {
                        port.get().setBaudRate(Integer.parseInt(line));
                        break;
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        }
    }


}
