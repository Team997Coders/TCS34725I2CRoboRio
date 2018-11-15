package edu.team997.first.wpilibj;

import com.codeaffine.junit.ignore.*;
import com.codeaffine.junit.ignore.ConditionalIgnoreRule.*;
import static org.hamcrest.Matchers.*;
import edu.team997.first.wpilibj.BusPirate.BusPirateCommPortClosedException;
import edu.team997.first.wpilibj.BusPirate.NoBusPirateFoundException;
import edu.team997.first.wpilibj.BusPirateI2C.I2CModeNotSupported;
import edu.team997.first.wpilibj.BusPirateI2C.I2CModeProtocolException;
import edu.team997.first.wpilibj.TCS34725_I2C.TransferAbortedException;
import edu.wpi.first.wpilibj.*;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TCS34725_I2CIntegrationTest {
    I2C i2cMock = null;

    @Rule
    public ConditionalIgnoreRule rule = new ConditionalIgnoreRule();

    @BeforeClass
    public static void openDevice() throws NoBusPirateFoundException, I2CModeNotSupported, BusPirateCommPortClosedException, I2CModeProtocolException {
        IntegrationTestMock.init();
    }

    @AfterClass
    public static void closeDevice() throws BusPirateCommPortClosedException, I2CModeProtocolException {
        IntegrationTestMock.destroy();
    }

    @Before
    public void wireUpBusPirate() {
        i2cMock = mock(I2C.class);

        // Shunt all I2C reads through the BP
        when(IntegrationTestMock.WPILIB_I2C_READ(i2cMock))
        .thenAnswer(IntegrationTestMock.BUS_PIRATE_READ());
        // Shunt all I2C writes through the BP
        when(IntegrationTestMock.WPILIB_I2C_WRITE(i2cMock))
        .thenAnswer(IntegrationTestMock.BUS_PIRATE_WRITE());
    }

    @Test 
    @ConditionalIgnore( condition = NoBusPirate.class )
    public void itShouldInitializeIfATCS34725() {
        // Assemble
        Throwable e = null;

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
    public void itShouldEnableTCS34725WhenCreated() throws TransferAbortedException {
        // Assemble
        TCS34725_I2C classUnderTest = new TCS34725_I2C(i2cMock, true);

        // Act
        boolean itEnabled = classUnderTest.isEnabled();

        // Assert
        assertNotEquals(null, classUnderTest);
        assertTrue(itEnabled);
    }

    @Test 
    @ConditionalIgnore( condition = NoBusPirate.class )
    public void itShouldDisableTCS34725() throws TransferAbortedException, InterruptedException {
        // Assemble
        TCS34725_I2C classUnderTest = new TCS34725_I2C(i2cMock, true);

        // Act
        classUnderTest.disable();
        boolean itDisabled = !classUnderTest.isEnabled();

        // Assert
        assertNotEquals(null, classUnderTest);
        assertTrue(itDisabled);
    }

    @Test 
    @ConditionalIgnore( condition = NoBusPirate.class )
    public void itCanDetectTheColorRed() throws TransferAbortedException, InterruptedException {
        // Assemble
        TCS34725_I2C classUnderTest = new TCS34725_I2C(i2cMock, true);

        // Act
        TCS34725_I2C.TCSColor color = classUnderTest.getRawData();

        // Assert
        assertThat("Show me something red.  I see blue!", color.getR(), greaterThan(color.getB()));
        assertThat("Show me something red.  I see green!", color.getR(), greaterThan(color.getG()));
    }

    @Test 
    @ConditionalIgnore( condition = NoBusPirate.class )
    public void itCanDetectTheColorBlue() throws TransferAbortedException, InterruptedException {
        // Assemble
        TCS34725_I2C classUnderTest = new TCS34725_I2C(i2cMock, true);

        // Act
        TCS34725_I2C.TCSColor color = classUnderTest.getRawData();

        // Assert
        assertThat("Show me something blue.  I see red!", color.getB(), greaterThan(color.getR()));
        assertThat("Show me something blue.  I see green!", color.getB(), greaterThan(color.getG()));
    }

    @Test 
    @ConditionalIgnore( condition = NoBusPirate.class )
    public void itCanDetectTheColorGreen() throws TransferAbortedException, InterruptedException {
        // Assemble
        TCS34725_I2C classUnderTest = new TCS34725_I2C(i2cMock, true);

        // Act
        TCS34725_I2C.TCSColor color = classUnderTest.getRawData();

        // Assert
        assertThat("Show me something green.  I see red!", color.getG(), greaterThan(color.getR()));
        assertThat("Show me something green.  I see blue!", color.getG(), greaterThan(color.getB()));
    }
}