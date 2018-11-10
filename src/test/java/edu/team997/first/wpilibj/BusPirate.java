package edu.team997.first.wpilibj;

import purejavacomm.*;
import java.util.*;
import java.io.*;

public class BusPirate {
    protected SerialPort port = null;
    protected InputStream in = null;
    protected OutputStream out = null;
    private static CommPortIdentifier busPiratePortIdentifier = null;

    public BusPirate() throws NoBusPirateFoundException {
        // Find a bus pirate plugged into a comm port
        Boolean bbReady = false;

        if (busPiratePortIdentifier == null) {
            busPiratePortIdentifier = findBusPiratePort();
            if (busPiratePortIdentifier != null) {
                bbReady = true;
            }
        } else {
            System.out.println("Scanning port: " + busPiratePortIdentifier.getName());
            try {
                openPort(busPiratePortIdentifier);
                if (bitBangMode()) {
                    bbReady = true;
                    System.out.println("BusPirate found, port: " + busPiratePortIdentifier.getName());
                } else {
                    closePort();
                    busPiratePortIdentifier = findBusPiratePort();
                    if (busPiratePortIdentifier != null) {
                        bbReady = true;
                    }
                }
            } catch(PortInUseException ex) {
                System.err.println("Port already in use: " + ex);
            } catch(IOException ex) {
                System.err.println("IO exception testing port: " + ex);
            } catch(InterruptedException ex) {
                System.err.println("Interrupted exception testing port: " + ex);
            } catch(UnsupportedCommOperationException ex) {
                System.err.println("Unsupported comm operation testing port: " + ex);
            }
        }
        if (!bbReady) {
            throw new NoBusPirateFoundException();
        }
    }

    private CommPortIdentifier findBusPiratePort() {
        Enumeration<CommPortIdentifier> e = CommPortIdentifier.getPortIdentifiers();
        while(e.hasMoreElements()) {
            CommPortIdentifier commPortIdentifier = e.nextElement();
            System.out.println("Scanning port: " + commPortIdentifier.getName());
            try {
                openPort(commPortIdentifier);
                if (bitBangMode()) {
                    System.out.println("BusPirate found, port: " + commPortIdentifier.getName());
                    return commPortIdentifier;
                } else {
                    closePort();
                }
            } catch(PortInUseException ex) {
                System.err.println("Port already in use: " + ex);
            } catch(IOException ex) {
                System.err.println("IO exception testing port: " + ex);
            } catch(InterruptedException ex) {
                System.err.println("Interrupted exception testing port: " + ex);
            } catch(UnsupportedCommOperationException ex) {
                System.err.println("Unsupported comm operation testing port: " + ex);
            }
        }
        return null;
    }

    protected void openPort(CommPortIdentifier commPortIdentifier) throws PortInUseException, UnsupportedCommOperationException, IOException, InterruptedException {
        port = (SerialPort) commPortIdentifier.open(
            "BusPirate",    // Name of the application asking for the port 
            2000            // Wait max. 2 sec. to acquire port
        );
        port.setSerialPortParams(115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        out = port.getOutputStream();
        in = port.getInputStream();
        drain();
    }

    protected void closePort() {
		if (port != null) {
			try {
                out.flush();
				port.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally {
                port = null;
                in = null;
                out = null;
			}
		}
    }
    
    protected void drain() throws InterruptedException, IOException {
		Thread.sleep(10);
		int n;
		while ((n = in.available()) > 0) {
			for (int i = 0; i < n; ++i)
				in.read();
			Thread.sleep(10);
		}
    }

    protected Boolean bitBangMode() {
        Boolean done = false;
        int tries = 0;
        byte[] sent = new byte[100];
        byte[] rcvd = new byte[100];

        try {
            if (port == null) {
                return false;
            } else {
                port.enableReceiveTimeout(100);
                while(!done) {
                    sent[0] = 0x00;
                    out.write(sent, 0, 1);
                    tries++;
                    Thread.sleep(10);
                    int count = in.read(rcvd, 0, 5);
                    if (count != 5 && tries > 20) {
                        return false;
                    } else if ((new String(rcvd, 0, 5, "US-ASCII")).contains("BBIO1")) {
                        done = true;
                    }

                    if (tries > 25) {
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
        return true;
    }

    public static class NoBusPirateFoundException extends Exception {
		public NoBusPirateFoundException(String message) {
			super(message);
		}
		public NoBusPirateFoundException() {
			super();
		}
	}
}