package com.irontigers.commands;

import java.util.ArrayList;
import java.util.List;

import com.irontigers.Robot;
import com.irontigers.RobotMap;
import com.irontigers.RollingAverage;
import com.irontigers.subsystems.DashboardPublisher;
import com.irontigers.subsystems.DriveSystem;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.EventImportance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class LimeAlign extends Command {
  private double x;
  private double y;
  private boolean hasTargets;
  private boolean stillHasTargets;
  NetworkTable limelight;

  public LimeAlign() {
    requires(DriveSystem.instance());
  }

  @Override
  protected void initialize() {
    hasTargets = false;
    stillHasTargets = false;
    Robot.instance().enableStandardControl();
    DashboardPublisher.instance().putDriver("Driving with Vision", true);
    Shuffleboard.addEventMarker("Vision Alignment Started", RobotMap.Dashboard.LOW);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(limelight.getEntry("tv").getDouble(0.0) == 1){
      hasTargets = true;
      stillHasTargets = true;
    }else{
      stillHasTargets = false;
    }

    double forwardSpeed = 0;
    double strafeSpeed = 0;
    double rotateSpeed = 0;

    x = limelight.getEntry("tx").getDouble(0.0);
    y = limelight.getEntry("ty").getDouble(0.0);

    if(x < -.37){
      strafeSpeed = -.35;
    }
    else if(x > .37){
      strafeSpeed = .35;
    }
    if(y < 1.5 && y > .37){
      forwardSpeed = .167 * y;
    }else if(y < -.37){
      forwardSpeed = -.35;
    }else if(y > .37){
      forwardSpeed = .35;
    }

    rotateSpeed = .005 * x;

    System.out.println("EXECUTING");

    DashboardPublisher.instance().putDebug("Limelight X", x);
    DashboardPublisher.instance().putDebug("Limelight Y", y);
    DashboardPublisher.instance().putDebug("Limelight has target", stillHasTargets);

    DriveSystem.instance().drive(forwardSpeed, strafeSpeed, rotateSpeed);


    // DriveSystem.instance().drive(controller.forwardSpeed(),controller.strafeSpeed(), controller.rotationSpeed());
  }

  private double deadify(double zone, double input){
    return Math.abs(input) < zone ? 0 : input;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (x > -.4 && x < .4 && y > -.4 && y < .4 && hasTargets);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    DriveSystem.instance().stop();
    DashboardPublisher.instance().putDriver("Driving with Vision", false);
    Shuffleboard.addEventMarker("Vision Alignment Ended", RobotMap.Dashboard.LOW);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Shuffleboard.addEventMarker("Vision Alignment Interrupted", RobotMap.Dashboard.HIGH);
    end();
  }

}
