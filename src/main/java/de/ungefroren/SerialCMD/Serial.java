package de.ungefroren.SerialCMD;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Serial implements Comparable {

    /**
     * @return a List of all Serial ports
     */
    public static List<Serial> listCOMPorts() {
        return Arrays.stream(SerialPort.getCommPorts())
                .map(Serial::new)
                .collect(Collectors.toList());
    }

    private final String portName;
    private final String portDescription;
    private PrintStream out = null;
    private InputStream in = null;
    private final SerialPort serialPort;
    private int baudRate = 9600;

    public Serial(SerialPort serialPort) {
        this.serialPort = serialPort;
        this.portName = serialPort.getSystemPortName();
        this.portDescription = serialPort.getPortDescription();
    }

    /**
     * @see SerialPort#getSystemPortName()
     * @return the system port name
     */
    public String getPortName() {
        return portName;
    }

    /**
     * @see SerialPort#getPortDescription()
     * @return the ports description
     */
    public String getPortDescription() {
        return portDescription;
    }

    public int getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
    }

    /**
     * Returns an output stream that can be used to write data to the serial port
     * @return print stream
     * @throws IllegalStateException if the connection is not opened
     */
    public PrintStream getOut() throws IllegalStateException {
        if (out == null) throw new IllegalStateException("connection must be opened first");
        return out;
    }

    /**
     * Returns an input stream that can be used to read incoming data from the serial port
     * @return input stream
     * @throws IllegalStateException if the connection is not opened
     */
    public InputStream getIn() throws IllegalStateException {
        if (in == null) throw new IllegalStateException("connection must be opened first");
        return in;
    }

    /**
     * Establishes a new connection with the provided COM port<br>
     * Must be called before any call to {@link #getIn()} or {@link #getOut()}
     */
    public void openConnection() throws IOException, InterruptedException {
        if (serialPort.openPort(500)) {
            serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 500, 500);
            in = serialPort.getInputStream();
            out = new PrintStream(serialPort.getOutputStream(), true, "US-ASCII");
        } else {
            throw new IOException("Failed to establish serial connection with " + portName);
        }
    }



    @Override
    public String toString() {
        return portName;
    }

    @Override
    public int compareTo(Object o) {
        return toString().compareToIgnoreCase(o.toString());
    }
}
