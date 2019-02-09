package com.irontigers.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

public interface InvertibleSystem {

  public void enableStandardControl();
  public void enableInvertedControl();

}