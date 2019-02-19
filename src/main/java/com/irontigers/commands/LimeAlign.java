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
    limelight.getEntry("camMode").setNumber(0);
    limelight.getEntry("ledMode").setNumber(0);
    hasTargets = false;
    Robot.instance().enableStandardControl();
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
      strafeSpeed = .3;
    }
    else if(x > .37){
      strafeSpeed = -.3;
    }

    if(y < -.37){
      forwardSpeed = -.3;
    }else if(y > .37){
      forwardSpeed = .3;
    }

    System.out.println("EXECUTING");

    DashboardPublisher.instance().put("Limelight X", x);
    DashboardPublisher.instance().put("Limelight Distance", y);
    // DashboardPublisher.instance().put("Limelight Yaw", yaw);

    DriveSystem.instance().drive(forwardSpeed, strafeSpeed, 0);


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
    return (x > -.37 && x < .37 && y > -.37 && y < .37 && hasTargets);
    // return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    DriveSystem.instance().stop();
    limelight.getEntry("camMode").setNumber(1);
    limelight.getEntry("ledMode").setNumber(1);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }

}
