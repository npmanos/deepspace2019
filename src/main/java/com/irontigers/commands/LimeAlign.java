package com.irontigers.commands;

import java.util.Arrays;

import com.irontigers.subsystems.DashboardPublisher;
import com.irontigers.subsystems.DriveSystem;
import com.irontigers.subsystems.DriverController;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;

public class LimeAlign extends Command {
  private double x;
  private double dist;
  private double yaw;
  private double[] threeDeeOut;
  private double[] emptyArray;

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
    emptyArray = new double[6];
    Arrays.fill(emptyArray, 0.0);
    threeDeeOut = table.getEntry("camtran").getDoubleArray(emptyArray);
    x = threeDeeOut[0];
    dist = threeDeeOut[2];
    yaw = threeDeeOut[4];
    double a = table.getEntry("ta").getDouble(0.0);

    DashboardPublisher.instance().put("Limelight X", x);
    DashboardPublisher.instance().put("Limelight Distance", dist);
    DashboardPublisher.instance().put("Limelight Yaw", yaw);

    double forwardSpeed = 0;
    double strafeSpeed = 0;
    double rotateSpeed = 0;

    if(yaw > 2){
      rotateSpeed = -.3;
    }else if(yaw < 0){
      rotateSpeed = .3;
    }

    if(x < 0){
      strafeSpeed = .3;
    }
    else if(x > 2){
      strafeSpeed = -.3;
    }

    if(dist < -18.4){
      forwardSpeed = .3;
    }

    DriveSystem.instance().drive(forwardSpeed, strafeSpeed, 0);


    // DriveSystem.instance().drive(controller.forwardSpeed(),controller.strafeSpeed(), controller.rotationSpeed());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    // This is our standard default command so we're never going to be done
    return (x > 0 && x < 2 && dist > -18.6 && yaw < 2 && yaw > 0);
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
