package edu.team997.first.wpilibj;

import com.codeaffine.junit.ignore.*;
import com.codeaffine.junit.ignore.ConditionalIgnoreRule.*;

import edu.team997.first.wpilibj.BusPirate.NoBusPirateFoundException;
import edu.team997.first.wpilibj.BusPirateI2C.I2CModeNotSupported;
import edu.team997.first.wpilibj.TCS34725_I2C.TransferAbortedException;
import edu.wpi.first.wpilibj.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TCS34725_I2CIntegrationTest {
    @Rule
    public ConditionalIgnoreRule rule = new ConditionalIgnoreRule();

    @BeforeClass
    public static void openDevice() throws NoBusPirateFoundException, I2CModeNotSupported {
        IntegrationTestMock.init();
    }

    @AfterClass
    public static void closeDevice() {
        IntegrationTestMock.destroy();
    }

    @Test 
    @ConditionalIgnore( condition = NoBusPirate.class )
    public void itShouldInitializeIfATCS34725() {
        Throwable e = null;

        // Assemble
        I2C i2cMock = mock(I2C.class);
        when(IntegrationTestMock.WPILIB_I2C_READ(i2cMock))
        .thenAnswer(IntegrationTestMock.BUS_PIRATE_READ())
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

    @Test 
    @ConditionalIgnore( condition = NoBusPirate.class )
    public void itShouldEnableTCS34725() throws TransferAbortedException {
        // Assemble
        I2C i2cMock = mock(I2C.class);
        // Enable all reads to go to the device via BP
        when(IntegrationTestMock.WPILIB_I2C_READ(i2cMock))
        .thenAnswer(IntegrationTestMock.BUS_PIRATE_READ());
        // Shunt all writes to device via BP
        when(IntegrationTestMock.WPILIB_I2C_WRITE(i2cMock))
        .thenAnswer(IntegrationTestMock.BUS_PIRATE_WRITE());

        // Act
        TCS34725_I2C classUnderTest = new TCS34725_I2C(i2cMock, true);
        
        // Assert
        assertNotEquals(null, classUnderTest);
        assertTrue(classUnderTest.isEnabled());
    }
}