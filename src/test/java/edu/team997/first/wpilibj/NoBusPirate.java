package edu.team997.first.wpilibj;

import com.codeaffine.junit.ignore.ConditionalIgnoreRule.*;
import gnu.io.*;

public class NoBusPirate implements IgnoreCondition {
    public boolean isSatisfied() {
//      return !System.getProperty( "os.name" ).startsWith( "Windows" );
        for(String s:NRSerialPort.getAvailableSerialPorts()){
            System.out.println("Availible port: "+s);
        }
        return true;
    }
  }
  