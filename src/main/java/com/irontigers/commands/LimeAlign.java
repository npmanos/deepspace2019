package com.irontigers.commands;

import java.util.ArrayList;
import java.util.List;

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
  private double[] threeDeeOut;
  private double areaLeft;
  private double areaRight;
  private RollingAverage averageLeftArea = new RollingAverage(5);
  private RollingAverage averageRightArea = new RollingAverage(5);

  public LimeAlign() {
    requires(DriveSystem.instance());
  }

  @Override
  protected void initialize() {
  }

  private List<Double> leftArea = new ArrayList<Double>();
  private List<Double> rightArea = new ArrayList<Double>();

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    // threeDeeOut = table.getEntry("camtran").getDoubleArray(new double[6]);

    

    double forwardSpeed = 0;
    double strafeSpeed = 0;
    double rotateSpeed = 0;

    x = table.getEntry("tx").getDouble(0.0);
    y = table.getEntry("ty").getDouble(0.0);
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
    return (x > -.37 && x < .37 && y > -.37 && y < .37);
    // return false;
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
