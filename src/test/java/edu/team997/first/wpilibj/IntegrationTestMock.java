package edu.team997.first.wpilibj;

import edu.team997.first.wpilibj.BusPirate.BusPirateCommPortClosedException;
import edu.team997.first.wpilibj.BusPirate.NoBusPirateFoundException;
import edu.team997.first.wpilibj.BusPirateI2C.I2CModeNotSupported;
import edu.team997.first.wpilibj.BusPirateI2C.I2CModeProtocolException;
import edu.wpi.first.wpilibj.*;

import java.nio.ByteBuffer;
import org.mockito.stubbing.*;
import org.mockito.invocation.*;
import static org.mockito.Mockito.*;

public class IntegrationTestMock {
    private static BusPirateI2CTCS34725 busPirateI2CTCS34725 = null;

    public static void init() throws I2CModeNotSupported, BusPirateCommPortClosedException, I2CModeProtocolException {
        try {
            busPirateI2CTCS34725 = new BusPirateI2CTCS34725();
        } catch(NoBusPirateFoundException e) {
            System.out.println("No bus pirate found. Integration tests will be skipped.");
        }
    }

    public static boolean busPirateFound() {
        return busPirateI2CTCS34725 != null;
    }

    public static void destroy() throws BusPirateCommPortClosedException, I2CModeProtocolException {
        if (busPirateI2CTCS34725 != null) {
            busPirateI2CTCS34725.setPeripheral(false, BusPirateI2C.I2C_POWER_BIT);
            busPirateI2CTCS34725.reset();
            busPirateI2CTCS34725.closePort();
            busPirateI2CTCS34725 = null;
        }
    }

    public static Answer<Boolean> BUS_PIRATE_READ() {
        return new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocation) {
                ByteBuffer rawByte = (ByteBuffer) invocation.getArgument(2);
                byte[] bytesRead = new byte[(Integer)invocation.getArgument(1)];
                boolean response = false;
                try {
                    response = busPirateI2CTCS34725.readCombined((byte)TCS34725_I2C.TCS34725_ADDRESS, (byte)((int)invocation.getArgument(0)), bytesRead, invocation.getArgument(1));
                } catch (Exception e) {
                    System.err.println("BUS_PIRATE_READ Mock Error" + e);
                }
                rawByte.put(bytesRead);
                rawByte.rewind();
                return !response;
            }
        };
    }

    public static Answer<Boolean> BUS_PIRATE_WRITE() {
        return new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocation) {
                boolean response = false;
                try {
                    response = busPirateI2CTCS34725.write((byte)TCS34725_I2C.TCS34725_ADDRESS, (byte)((int)invocation.getArgument(0)), new byte[] {(byte)((int)invocation.getArgument(1))});
                } catch (Exception e) {
                    System.err.println("BUS_PIRATE_WRITE Mock Error" + e);
                }
                return !response;
            }
        };
    }

    public static Boolean WPILIB_I2C_READ(I2C i2cMock) {
        return i2cMock.read(any(Integer.class), any(Integer.class), any(ByteBuffer.class));
    }

    public static Boolean WPILIB_I2C_WRITE(I2C ic2Mock) {
        return ic2Mock.write(any(Integer.class), any(Integer.class));
    }
}