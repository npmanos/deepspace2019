package com.irontigers.commands;

import com.irontigers.itlib.commands.MecanumTeleopCommand;
import com.irontigers.subsystems.DriveSystem;
import com.irontigers.subsystems.DriverController;

public class TeleopDrive extends MecanumTeleopCommand {

  public TeleopDrive() {
    setDriveSubsystem(DriveSystem.getInstance());
    setController(DriverController.getInstance());
  }

}
