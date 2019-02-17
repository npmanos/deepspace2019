package com.irontigers.commands;

import com.irontigers.subsystems.DashboardPublisher;
import com.irontigers.subsystems.DriveSystem;
import com.irontigers.subsystems.DriverController;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;

public class LimeAlign extends Command {

  public LimeAlign() {
    requires(DriveSystem.instance());
  }

  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    double x = table.getEntry("tx").getDouble(0.0);
    double y = table.getEntry("ty").getDouble(0.0);
    double a = table.getEntry("ta").getDouble(0.0);

    DashboardPublisher.instance().put("Limelight X", x);
    DashboardPublisher.instance().put("Limelight Y", y);
    DashboardPublisher.instance().put("Limelight A", a);

    double forwardSpeed = 0;
    double strafeSpeed = 0;
    double rotateSpeed = 0;

    if(x < -1){
      strafeSpeed = -.35;
    }
    else if(x > 1){
      strafeSpeed = .35;
    }

    if(y < -1){
      forwardSpeed = -.35;
    }
    else if(y > 1){
      forwardSpeed = .35;
    }

    DriveSystem.instance().drive(forwardSpeed, strafeSpeed, 0);


    // DriveSystem.instance().drive(controller.forwardSpeed(),controller.strafeSpeed(), controller.rotationSpeed());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    // This is our standard default command so we're never going to be done
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    DriveSystem.instance().stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }

}
