package de.ungefroren.SerialCMD.threads;

import com.fazecast.jSerialComm.SerialPortTimeoutException;
import de.ungefroren.SerialCMD.Main;

import java.io.BufferedReader;
import java.io.PrintStream;

public class InputThread extends Thread {

    private PrintStream output;
    private BufferedReader input = Main.SYS_IN;

    public InputThread(PrintStream output) {
        super("InputThread");
        this.output = output;
    }

    @Override
    public void run() {
        while (!interrupted()) {
            try {
                String s = input.readLine();
                if (s == null) return;
                if (s.equalsIgnoreCase("exit")) {
                    interrupt();
                    Main.getOutput().interrupt();
                } else {
                    output.print(s + "\n");
                }
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }
}
