[![Release](https://jitpack.io/v/Team997Coders/TCS34725I2CRoboRio.svg)](https://jitpack.io/#Team997Coders/TCS34725I2CRoboRio)

# TCS34725 I2C Driver for RoboRio/WPILib - Java

This is an I2C driver for a TCS34725 RGB color sensor.  It was developed against [this Adafruit product](https://www.adafruit.com/product/1334)
but should work against any breakout board with the reference sensor.  Connect sensor to roboRio I2C port and instantiate class. Component will automatically feed values to the smartdashboard keys "RGBColorSensor/[R,G,B,C]".

This project contains integration tests that will test the driver without a roboRio. Instead, a [bus pirate](https://www.sparkfun.com/products/12942) is used.
The bus pirate is a hardware communication "swiss army knife" that enables you to communicate over USB to target hardware interfaced with I2C, SPI, UART,
1 wire, 2 wire, and 3 wire protocols. CAN would be a boon, but it is currently not supported.

To make this work, connect the bus pirate to the TCS34725 like so (a useful cable such as [this](https://www.sparkfun.com/products/9556) would be helpful.):

|BP     | TCS34725  |
| ----- | --------- |
| MOSI  | SDA       |
| CLK   | SCL       |
| GND   | GND       |
| +3.3V | Vin       |

With the bus pirate connected to your workstation (only tested with Windows), run `./gradlew test`. The integration test will enumerate each COM port to find the bus pirate.
Once found, integration tests will run. Note that there are some tests which require you to point red, blue, and green objects at the sensor to pass.

## Usage

In your build.gradle file:
```
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
dependencies {
    compile 'com.github.Team997Coders:TCS34725I2CRoboRio:0.1.0'
}
```

Next, import like so:
```Java
import edu.team997.first.wpilibj.*;
```

Finally, instantiate:
```Java
TCS34725_I2C tcs34725 = new TCS34725_I2C();
```

If you pass `true` to the constructor, verbose messages will print to the driver station console.