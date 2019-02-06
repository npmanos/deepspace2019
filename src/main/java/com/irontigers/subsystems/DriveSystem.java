package com.irontigers.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.irontigers.RobotMap;
import com.irontigers.commands.TeleopDrive;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

/**
 * Basic DriveTrain for the robot. Provides standard {@link DriveSystem#drive} method
 * allowing for forward/backward, left/right strafing, and rotation. Also uses a standard
 * wheel encoder to track travelled distance. The encoder can be reset via the
 * {@link DriveSystem#resetEncoder()} method.
 *
 * This class, along with all Subsystems, uses the Singleton pattern to ensure only a single
 * instance of this Subsystem exists during the lifetime of the Robot program. The instance
 * will be created statically (the first time this class is used) and can be accessed via
 * the static {@link DriveSystem#instance()} method from anywhere in the program. The use of
 * this coding pattern is useful in keeping the class self-contained and easy to use.
 */
public class DriveSystem extends Subsystem {

  private static DriveSystem instance = new DriveSystem();
  public static DriveSystem instance(){
    return instance;
  }

  // Drive and controllers
  private MecanumDrive drive;
  
  private WPI_TalonSRX leftFront;
  private WPI_TalonSRX leftBack;
  private WPI_TalonSRX rightFront;
  private WPI_TalonSRX rightBack;

  private DriveSystem(){

    // We're using WPI_TalonSRX because it is a wrapper around the CTRE provided TalonSRX
    // and implements the SpeedController interface. This allows us to create a TalonSRX
    // object and pass it directly into the MecanumDrive class.

    leftFront = new WPI_TalonSRX(RobotMap.DriveTrain.LEFT_FRONT);
    leftBack = new WPI_TalonSRX(RobotMap.DriveTrain.LEFT_BACK);
    rightFront = new WPI_TalonSRX(RobotMap.DriveTrain.RIGHT_FRONT);
    rightBack = new WPI_TalonSRX(RobotMap.DriveTrain.RIGHT_BACK);

    drive = new MecanumDrive(leftFront, leftBack, rightFront, rightBack);
  }

  @Override
  public void initDefaultCommand(){
    // If there is no other ACTIVE command claiming a requirement on the DriveTrain then we will always
    // default to TelopDrive command

    setDefaultCommand(new TeleopDrive());
  }

  public double leftFrontCurrent() {
    return leftFront.getOutputCurrent();
  }
  public double leftBackCurrent() {
    return leftBack.getOutputCurrent();
  }

  public double rightFrontCurrent() {
    return rightFront.getOutputCurrent();
  }
  public double rightBackCurrent() {
    return rightBack.getOutputCurrent();
  }

  /**
   * Execute a drive action on the driveTrain.
   * @param ySpeed speed for forward/backward movement
   * @param xSpeed speed for left/right movement
   * @param rotation rotation around the Z axis
   */
  public void drive(double ySpeed, double xSpeed, double rotation){
    System.out.println("driving " + ySpeed + ", " + xSpeed + ", " + rotation);
    drive.driveCartesian(xSpeed, ySpeed, rotation);
  }

  /**
   * Complete stop driving
   */
  public void stop(){
    drive(0,0,0);
  }

}
