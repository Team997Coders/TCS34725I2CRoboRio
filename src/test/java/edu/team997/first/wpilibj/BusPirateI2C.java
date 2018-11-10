package edu.team997.first.wpilibj;

public class BusPirateI2C extends BusPirate {
    public final static byte I2C_BITBANG = 0b00000000;
    public final static byte I2C_MODE = 0b00000010;
    public final static byte I2C_MODE_VERSION = 0b000000001;
	public final static byte I2C_START_BIT = 0b00000010;
	public final static byte I2C_STOP_BIT = 0b00000011;
	public final static byte I2C_READ_BYTE = 0b00000100;
	public final static byte I2C_ACK_BIT = 0b00000110;
    public final static byte I2C_NACK_BIT = 0b00000111;
    public final static byte I2C_BULK_I2C_WRITE_XXXX = 0b00010000;
    public final static byte I2C_CONFIGURE_PERIPHERALS = 0b01000000;
    public final static byte I2C_POWER_BIT = 0b00001000;
    public final static byte I2C_PULLUP_BIT = 0b00000100;
    public final static byte I2C_AUX_BIT = 0b00000010;
    public final static byte I2C_CS_BIT = 0b00000001;
    public final static byte I2C_WRITE_THEN_READ = 0b00001000;

    public BusPirateI2C() throws NoBusPirateFoundException, I2CModeNotSupported {
        super();
        if (setI2CMode() == false) {
            throw new I2CModeNotSupported();
        }
    }

    private boolean setI2CMode() {
        byte[] sent = new byte[100];

        try {
            if (port == null) {
                return false;
            } else {
                port.enableReceiveTimeout(1000);
                sent[0] = I2C_MODE;
                out.write(sent, 0, 1);
                Thread.sleep(10);
                return confirmI2CMode();
            }
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    private boolean confirmI2CMode() {
        byte[] sent = new byte[100];
        byte[] rcvd = new byte[100];

        try {
            if (port == null) {
                return false;
            } else {
                port.enableReceiveTimeout(1000);
                sent[0] = I2C_MODE_VERSION;
                out.write(sent, 0, 1);
                Thread.sleep(10);
                int count = in.read(rcvd, 0, 4);
                if (count == 4) {
                    if ((new String(rcvd, 0, 4, "US-ASCII")).contains("I2C1")) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    /**
     * Set the power state to the peripheral
     * 
     * @param powerOn   Set true to power on the device
     * @return          True if successful; false otherwise
     */
    protected boolean setPower(boolean powerOn) {
        // Send the message to the device and wait synchrounsly for the response
        try {
            if (port == null) {                                             // Port must be open
                return false;
            } else {
                // TODO: This should read peripherial bits and not trounce or perhaps be changed to set these bits simultaneously
                byte[] sendBuffer = {powerOn ? (byte)(I2C_CONFIGURE_PERIPHERALS | I2C_POWER_BIT) : I2C_CONFIGURE_PERIPHERALS};
                byte[] rcvdBuffer = new byte[100];
                port.enableReceiveTimeout(1000);
                out.write(sendBuffer, 0, sendBuffer.length);
                int count = in.read(rcvdBuffer);
                if (count == 1) {
                    if (rcvdBuffer[0] == 0x01) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    /**
     * Sends start bit to the device
     * 
     * @return          True if successful; false otherwise
     */
    protected boolean sendStartBit() {
        // Send the message to the device and wait synchrounsly for the response
        try {
            if (port == null) {                                             // Port must be open
                return false;
            } else {
                byte[] sendBuffer = {I2C_START_BIT};
                byte[] rcvdBuffer = new byte[100];
                port.enableReceiveTimeout(1000);
                out.write(sendBuffer, 0, sendBuffer.length);
                int count = in.read(rcvdBuffer);
                if (count == 1) {
                    if (rcvdBuffer[0] == 0x01) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    /**
     * Sends stop bit to the device
     * 
     * @return          True if successful; false otherwise
     */
    protected boolean sendStopBit() {
        // Send the message to the device and wait synchrounsly for the response
        try {
            if (port == null) {                                             // Port must be open
                return false;
            } else {
                byte[] sendBuffer = {I2C_STOP_BIT};
                byte[] rcvdBuffer = new byte[100];
                port.enableReceiveTimeout(1000);
                out.write(sendBuffer, 0, sendBuffer.length);
                int count = in.read(rcvdBuffer);
                if (count == 1) {
                    if (rcvdBuffer[0] == 0x01) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    /**
     * Sends ACK bit to the device
     * 
     * @return          True if successful; false otherwise
     */
    protected boolean sendACK() {
        // Send the message to the device and wait synchrounsly for the response
        try {
            if (port == null) {                                             // Port must be open
                return false;
            } else {
                byte[] sendBuffer = {I2C_ACK_BIT};
                byte[] rcvdBuffer = new byte[100];
                port.enableReceiveTimeout(1000);
                out.write(sendBuffer, 0, sendBuffer.length);
                int count = in.read(rcvdBuffer);
                if (count == 1) {
                    if (rcvdBuffer[0] == 0x01) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    /**
     * Read byte from the device
     * 
     * @return          The byte read
     */
    protected byte readByte() {
        // Send the message to the device and wait synchrounsly for the response
        try {
            if (port == null) {                                             // Port must be open
                return 0x00;
            } else {
                byte[] sendBuffer = {I2C_READ_BYTE};
                byte[] rcvdBuffer = new byte[100];
                port.enableReceiveTimeout(1000);
                out.write(sendBuffer, 0, sendBuffer.length);
                int count = in.read(rcvdBuffer);
                if (count == 1) {
                    return rcvdBuffer[0];
                } else {
                    return 0x00;
                }
            }
        } catch (Exception e) {
            System.err.println(e);
            return 0x00;
        }
    }

    /**
     * Sends NACK bit to the device
     * 
     * @return          True if successful; false otherwise
     */
    protected boolean sendNACK() {
        // Send the message to the device and wait synchrounsly for the response
        try {
            if (port == null) {                                             // Port must be open
                return false;
            } else {
                byte[] sendBuffer = {I2C_NACK_BIT};
                byte[] rcvdBuffer = new byte[100];
                port.enableReceiveTimeout(1000);
                out.write(sendBuffer, 0, sendBuffer.length);
                int count = in.read(rcvdBuffer);
                if (count == 1) {
                    if (rcvdBuffer[0] == 0x01) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    protected boolean writeBulk(byte[] bytesToWrite) {
        // Send the message to the device and wait synchrounsly for the response
        try {
            if (port == null) {                                             // Port must be open
                return false;
            } else {
                byte[] sendBuffer = {getBulkI2CWriteCommand(bytesToWrite.length)};  // Send the bulk write command to BP
                byte[] rcvdBuffer = new byte[100];
                port.enableReceiveTimeout(1000);
                out.write(sendBuffer, 0, sendBuffer.length);
                int count = in.read(rcvdBuffer);
                if (count == 1) {
                    if (rcvdBuffer[0] == 0x01) {
                        // BP bulk write command succedded...proceed with writing data
                        out.write(bytesToWrite, 0, bytesToWrite.length);
                        count = in.read(rcvdBuffer);
                        if (count == bytesToWrite.length) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    protected static byte getSlaveAddressRead(byte slaveAddress) {
        return (byte)((slaveAddress << 1) + 1);
    }

    protected static byte getSlaveAddressWrite(byte slaveAddress) {
        return (byte)((slaveAddress << 1));
    }

    protected static byte getBulkI2CWriteCommand(int count) {
        if (count > 16) {
            throw new IllegalArgumentException("Bulk write count cannot exceed 16 bytes.");
        }
        // The count appended to low nibble of the bulk write command is zero based
        byte countNibble = (byte)(count - 1);
        return (byte)(I2C_BULK_I2C_WRITE_XXXX | countNibble);
    }

    public static class I2CModeNotSupported extends Exception {
		public I2CModeNotSupported(String message) {
			super(message);
		}
		public I2CModeNotSupported() {
			super();
		}
	}
}