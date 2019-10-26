package de.ungefroren.SerialCMD.threads;

import com.fazecast.jSerialComm.SerialPortTimeoutException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class OutputThread extends Thread {

    private InputStreamReader input;

    public OutputThread(InputStream input) {
        super("Output Thread");
        this.input = new InputStreamReader(input, StandardCharsets.US_ASCII);
    }

    @Override
    public void run() {
        while (!interrupted()) {
            try {
                System.out.print((char) input.read());
            } catch (SerialPortTimeoutException ignored) {
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }
}
