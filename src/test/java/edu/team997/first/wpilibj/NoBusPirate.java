package edu.team997.first.wpilibj;

import com.codeaffine.junit.ignore.ConditionalIgnoreRule.*;

public class NoBusPirate implements IgnoreCondition {
    public boolean isSatisfied() {
        return !(IntegrationTestMock.busPirateFound());
    }
  }
  