package edu.team997.first.wpilibj;

import com.codeaffine.junit.ignore.*;
import com.codeaffine.junit.ignore.ConditionalIgnoreRule.*;
import edu.wpi.first.wpilibj.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TCS34725_I2CIntegrationTest {
    @Rule
    public ConditionalIgnoreRule rule = new ConditionalIgnoreRule();

    @Test 
    @ConditionalIgnore( condition = NoBusPirate.class )
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