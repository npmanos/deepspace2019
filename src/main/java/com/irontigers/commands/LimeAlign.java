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

    leftArea.add(deadify(.1,table.getEntry("ta0").getDouble(0.0)));
    rightArea.add(deadify(.1, table.getEntry("ta1").getDouble(0.0)));

    if(leftArea.size() > 3){ leftArea.remove(3); }
    if(rightArea.size() > 3){ rightArea.remove(3); }

    boolean leftSeen = leftArea.stream().allMatch(item -> item > 0);
    boolean rightSeen = rightArea.stream().allMatch(item -> item > 0);

    double forwardSpeed = 0;
    double strafeSpeed = 0;
    double rotateSpeed = 0;

    if(leftSeen && rightSeen){
      int[] directions = new int[]{0,0,0};
      for(int idx = 0; idx < leftArea.size(); ++idx){
        directions[idx] = (leftArea.get(idx) < rightArea.get(idx)) ? -1 : (leftArea.get(idx) > rightArea.get(idx)) ? 1 : 0;
      }

      if(directions[0] == directions[1] && directions[0] == directions[2]){
        if(directions[0] < 0){
          rotateSpeed = .25;
        }
        else if(directions[0] > 0){
          rotateSpeed = -.25;
        }
      }
    }
    else if(leftSeen){
      rotateSpeed = .25;
    }
    else if(rightSeen){
      rotateSpeed = -.25;
    }

    // x = table.getEntry("tx").getDouble(0.0);
    // y = table.getEntry("ty").getDouble(0.0);
    // areaLeft = averageLeftArea.getAverage();
    // areaRight = averageRightArea.getAverage();
    // yaw = threeDeeOut[4];
    // double a = table.getEntry("ta").getDouble(0.0);

    // DashboardPublisher.instance().put("Limelight X", x);
    // DashboardPublisher.instance().put("Limelight Distance", y);
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
    // return (x > -.37 && x < .37 && y > -.37 && y < .37);
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
