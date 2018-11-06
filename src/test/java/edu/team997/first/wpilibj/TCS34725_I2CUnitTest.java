package edu.team997.first.wpilibj;

import static com.google.common.truth.Truth.*;
import edu.wpi.first.wpilibj.*;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TCS34725_I2CUnitTest {
    @Test 
    public void itShouldNotInitializeIfNotATCS34725() {
        Throwable e = null;

        // Assemble
        I2C i2cMock = mock(I2C.class);
        when(UnitTestMock.READ_TCS34725_ID(i2cMock))
        .thenAnswer(UnitTestMock.WITH_INVALID_TCS34725_ID())
        .thenReturn(false);

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
    public void itShouldInitializeIfATCS34725() {
        Throwable e = null;

        // Assemble
        I2C i2cMock = mock(I2C.class);
        when(UnitTestMock.READ_TCS34725_ID(i2cMock))
        .thenAnswer(UnitTestMock.WITH_VALID_TCS34725_ID())
        .thenReturn(false);

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