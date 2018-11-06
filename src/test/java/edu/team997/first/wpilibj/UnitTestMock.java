package edu.team997.first.wpilibj;

import edu.wpi.first.wpilibj.*;

import java.nio.ByteBuffer;
import org.mockito.stubbing.*;
import org.mockito.invocation.*;
import static org.mockito.Mockito.*;

public class UnitTestMock {
    public static Answer<Void> WITH_VALID_TCS34725_ID() {
        return new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                ByteBuffer rawByte = (ByteBuffer) invocation.getArguments()[2];
                rawByte.put((byte)0x44);
                rawByte.rewind();
                return null;
            }
        };
    }

    public static Answer<Void> WITH_INVALID_TCS34725_ID() {
        return new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                ByteBuffer rawByte = (ByteBuffer) invocation.getArguments()[2];
                rawByte.put((byte)0x4D);
                rawByte.rewind();
                return null;
            }
        };
    }

    public static Boolean READ_TCS34725_ID(I2C i2cMock) {
        return i2cMock.read(eq(TCS34725_I2C.TCS34725_COMMAND_BIT | TCS34725_I2C.TCS34725_ID), eq(1), any(ByteBuffer.class));
    }
}