# TCS34725 I2C Driver for RoboRio/WPILib - Java

This is an I2C driver for a TCS34725 RGB color sensor.  It was developed against [this Adafruit product](https://www.adafruit.com/product/1334)
but should work against any breakout board with the reference sensor.  Connect sensor to roboRio I2C port and instantiate class. Component will automatically feed values to the smartdashboard keys "RGBColorSensor/[R,G,B,C]".

This project contains integration tests that will test the driver without a roboRio. Instead, a [bus pirate](https://www.sparkfun.com/products/12942) is used.
The bus pirate is a hardware communication "swiss army knife" that enables you to communicate over USB to target hardware interfaced with I2C, SPI, UART,
1 wire, 2 wire, and 3 wire protocols. CAN would be a boon, but it is currently not supported.

To make this work, connect the bus pirate to the TCS34725 like so (a useful cable such as [this](https://www.sparkfun.com/products/9556) would be helpful.):

BP      TCS34725
MOSI    SDA
CLK     SCL
GND     GND
+3.3V   Vin