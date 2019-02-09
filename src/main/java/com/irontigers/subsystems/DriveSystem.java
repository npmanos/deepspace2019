package com.irontigers.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.irontigers.RobotMap;
import com.irontigers.commands.TeleopDrive;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
public class DriveSystem extends InvertibleSystem {

  public interface DriverMethod{
    public void drive(double ySpeed, double xSpeed, double rotation);
  }

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

  private DriverMethod driveMethod;

  private DriveSystem(){

    // We're using WPI_TalonSRX because it is a wrapper around the CTRE provided TalonSRX
    // and implements the SpeedController interface. This allows us to create a TalonSRX
    // object and pass it directly into the MecanumDrive class.

    leftFront = new WPI_TalonSRX(RobotMap.DriveTrain.LEFT_FRONT);
    leftBack = new WPI_TalonSRX(RobotMap.DriveTrain.LEFT_BACK);
    rightFront = new WPI_TalonSRX(RobotMap.DriveTrain.RIGHT_FRONT);
    rightBack = new WPI_TalonSRX(RobotMap.DriveTrain.RIGHT_BACK);

    drive = new MecanumDrive(leftFront, leftBack, rightFront, rightBack);
    setStandardControl();
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
   * @param forwardSpeed speed for forward/backward movement
   * @param strafeSpeed speed for left/right movement
   * @param rotationSpeed rotation around the Z axis
   */
  public void drive(double forwardSpeed, double strafeSpeed, double rotationSpeed){

    if(InvertedState.INVERTED == invertedState){
      forwardSpeed *= -1.0;
      strafeSpeed *= -1.0;
      rotationSpeed *= -1.0;
    }

    DashboardPublisher.instance().put("Forward", forwardSpeed);
    DashboardPublisher.instance().put("Strafe", strafeSpeed);
    DashboardPublisher.instance().put("Rotation", rotationSpeed);

    driveMethod.drive(strafeSpeed, forwardSpeed, rotationSpeed);
  }

  /**
   * Complete stop driving
   */
  public void stop(){
    drive(0,0,0);
  }

  @Override
  public void enableStandardControl() {
    driveMethod = (ySpeed, xSpeed, rotation) -> drive.driveCartesian(ySpeed, xSpeed, rotation);    
  }

  @Override
  public void enableInvertedControl() {
    driveMethod = (ySpeed, xSpeed, rotation) -> drive.driveCartesian(-ySpeed, -xSpeed, -rotation);
  }

}
