package com.irontigers.commands;

import java.util.ArrayList;
import java.util.List;

import com.irontigers.Robot;
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
  private double yaw;
  private boolean hasTargets;
  private double[] threeDeeOut;
  private double areaLeft;
  private double areaRight;
  private RollingAverage averageLeftArea = new RollingAverage(5);
  private RollingAverage averageRightArea = new RollingAverage(5);
  NetworkTable limelight;

  public LimeAlign() {
    requires(DriveSystem.instance());
  }

  @Override
  protected void initialize() {
    limelight = NetworkTableInstance.getDefault().getTable("limelight");
    limelight.getEntry("pipeline").setNumber(0);
    hasTargets = false;
    Robot.instance().enableStandardControl();
    DashboardPublisher.instance().putDriver("Driving with Vision", true);
    Shuffleboard.addEventMarker("Vision Alignment Enabled", EventImportance.kLow);
  }

  private List<Double> leftArea = new ArrayList<Double>();
  private List<Double> rightArea = new ArrayList<Double>();

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // threeDeeOut = table.getEntry("camtran").getDoubleArray(new double[6]);

    if(limelight.getEntry("tv").getDouble(0.0) == 1){
      hasTargets = true;
    }

    double forwardSpeed = 0;
    double strafeSpeed = 0;
    double rotateSpeed = 0;

    x = limelight.getEntry("tx").getDouble(0.0);
    y = limelight.getEntry("ty").getDouble(0.0);

    // areaLeft = averageLeftArea.getAverage();
    // areaRight = averageRightArea.getAverage();
    // yaw = threeDeeOut[4];
    // double a = table.getEntry("ta").getDouble(0.0);

    if(x < -.37){
      strafeSpeed = -.35;
    }
    else if(x > .37){
      strafeSpeed = .35;
    }
    if(y < -.37){
      forwardSpeed = -.35;
    }else if(y > .37){
      forwardSpeed = .35;
    }

    if(y < 1.5){
      forwardSpeed = forwardSpeed * .35 * y;
    }

    rotateSpeed = .005 * x;

    System.out.println("EXECUTING");

    DashboardPublisher.instance().putDebug("Limelight X", x);
    DashboardPublisher.instance().putDebug("Limelight Y", y);
    // DashboardPublisher.instance().put("Limelight Yaw", yaw);

    DriveSystem.instance().drive(forwardSpeed, strafeSpeed, rotateSpeed);


    // DriveSystem.instance().drive(controller.forwardSpeed(),controller.strafeSpeed(), controller.rotationSpeed());
  }

  private double deadify(double zone, double input){
    return Math.abs(input) < zone ? 0 : input;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    // This is our standard default command so we're never going to be done
    System.out.println(limelight.getEntry("camMode").getDouble(1.0));
    return (x > -.4 && x < .4 && y > -.4 && y < .4 && hasTargets);
    // return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    DriveSystem.instance().stop();
    limelight.getEntry("pipeline").setNumber(0);
    DashboardPublisher.instance().putDriver("Driving with Vision", false);
    Shuffleboard.addEventMarker("Vision Alignment Completed", EventImportance.kLow);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Shuffleboard.addEventMarker("Vision Alignment Interrupted", EventImportance.kHigh);
    end();
  }

}
