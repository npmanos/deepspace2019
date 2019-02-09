package com.irontigers.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class InvertibleSystem extends Subsystem {

  public enum InvertedState {
    STANDARD,
    INVERTED
  }

  protected InvertedState invertedState = InvertedState.STANDARD;

  public void defaultState(){
    setStandardControl();
  }

  public void invert(){
    switch(invertedState){
      case STANDARD:
        setInvertedControl();
        break;
      case INVERTED:
      default:
        setStandardControl();
        break;
    }
  }

  public void setStandardControl(){
    invertedState = InvertedState.STANDARD;
    enableStandardControl();
  }

  public void setInvertedControl(){
    invertedState = InvertedState.INVERTED;
    enableInvertedControl();
  }

  abstract protected void enableStandardControl();
  abstract protected void enableInvertedControl();

}