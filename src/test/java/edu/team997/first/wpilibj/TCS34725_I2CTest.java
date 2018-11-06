package edu.team997.first.wpilibj;

import static com.google.common.truth.Truth.*;
import edu.wpi.first.wpilibj.*;
import java.nio.ByteBuffer;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.invocation.*;
import org.mockito.stubbing.*;
import static org.mockito.Mockito.*;

public class TCS34725_I2CTest {
    @Test 
    public void itShouldNotIdentifyAsATCS34725() {
        Throwable e = null;

        // Assemble
        I2C i2cMock = mock(I2C.class);

        // Act
        try {
            TCS34725_I2C classUnderTest = new TCS34725_I2C(i2cMock);
        } catch (Throwable ex) {
            e = ex;
        }
        
        // Assert
        assertTrue(e instanceof RuntimeException);
        assertThat(e).hasMessageThat().contains("Device is not a TCS34721/TCS34725");
    }

    @Test 
    public void itShouldIdentifyAsATCS34725AndInit() {
        Throwable e = null;

        // Assemble
        I2C i2cMock = mock(I2C.class);
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                ByteBuffer rawByte = (ByteBuffer) invocation.getArguments()[2];
                rawByte.put((byte)0x44);
                rawByte.rewind();
                return null;
            }
        }).when(i2cMock).read(eq(TCS34725_I2C.TCS34725_COMMAND_BIT | TCS34725_I2C.TCS34725_ID), eq(1), any(ByteBuffer.class));

        // Act
        try {
            TCS34725_I2C classUnderTest = new TCS34725_I2C(i2cMock, true);
        } catch (Throwable ex) {
            e = ex;
        }
        
        // Assert
        assertEquals(null, e);
    }
}