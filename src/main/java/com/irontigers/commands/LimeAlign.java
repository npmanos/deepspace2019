package com.irontigers.commands;

import com.irontigers.Robot;
import com.irontigers.RobotMap;
import com.irontigers.subsystems.DashboardPublisher;
import com.irontigers.subsystems.DriveSystem;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class LimeAlign extends Command {
  private double x;
  private double y;
  private double yaw;
  private boolean hasTargets;
  private double[] threeDeeOut;
  private double areaLeft;
  private double areaRight;
  NetworkTable limelight;

  public LimeAlign() {
    requires(DriveSystem.instance());
  }

  @Override
  protected void initialize() {
    limelight = NetworkTableInstance.getDefault().getTable("limelight");
    hasTargets = false;
    Robot.getInstance().enableStandardControl();
    DashboardPublisher.instance().putDriver("Driving with Vision", true);
    Shuffleboard.addEventMarker("Vision Alignment Started", RobotMap.Dashboard.LOW);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // threeDeeOut = table.getEntry("camtran").getDoubleArray(new double[6]);

    try {
      if (limelight.getEntry("tv").getDouble(0.0) == 1) {
        hasTargets = true;
      }
    } catch (NullPointerException e) {
      e.printStackTrace();
    }

    double forwardSpeed = 0;
    double strafeSpeed = 0;
    double rotateSpeed = 0;
    try {
      x = limelight.getEntry("tx").getDouble(0.0);
      y = limelight.getEntry("ty").getDouble(0.0);
    } catch (NullPointerException e) {
      e.printStackTrace();
    }

    // areaLeft = averageLeftArea.getAverage();
    // areaRight = averageRightArea.getAverage();
    // yaw = threeDeeOut[4];
    // double a = table.getEntry("ta").getDouble(0.0);

    if (x < -.37) {
      strafeSpeed = -.35;
    } else if (x > .37) {
      strafeSpeed = .35;
    }
    if (y < -.37) {
      forwardSpeed = -.35;
    } else if (y > .37) {
      forwardSpeed = .35;
    }

    if (y < 1.5) {
      forwardSpeed = forwardSpeed * .35 * y;
    }

    rotateSpeed = .005 * x;

    System.out.println("EXECUTING");

    DashboardPublisher.instance().putDebug("Limelight X", x);
    DashboardPublisher.instance().putDebug("Limelight Y", y);
    // DashboardPublisher.instance().put("Limelight Yaw", yaw);

    DriveSystem.instance().drive(forwardSpeed, strafeSpeed, rotateSpeed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    // This is our standard default command so we're never going to be done
    try{
      System.out.println(limelight.getEntry("camMode").getDouble(1.0));
    }catch(NullPointerException e){
      e.printStackTrace();
    }
    
    return (x > -.4 && x < .4 && y > -.4 && y < .4 && hasTargets);
    // return false;
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
