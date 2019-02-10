/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.irontigers.subsystems;

import java.time.Duration;

import com.irontigers.PeriodicExecutor;
import com.irontigers.RobotMap;
import com.irontigers.RobotMap.XBoxController;
import com.irontigers.RollingAverage;
import com.irontigers.commands.EnableDrivingCamera;
import com.irontigers.commands.ResetRobotToDefaults;
import com.irontigers.commands.ToggleDumpTruck;
import com.irontigers.commands.ToggleInvertedControl;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Basic Joystick for the robot. While technically this is not a Subsystem of
 * the Robot it is a piece of hardware that can be considered a Subsystem. It
 * provides sensor readings that can be used by the robot's command system (eg.
 * desired speed and rotation).
 *
 * This class, along with all Subsystems, uses the Singleton pattern to ensure
 * only a single instance of this Subsystem exists during the lifetime of the
 * Robot program. The instance will be created statically (the first time this
 * class is used) and can be accessed via the static
 * {@link XBoxController#instance()} method from anywhere in the program. The
 * use of this coding pattern is useful in keeping the class self-contained and
 * easy to use.
 */

public class DriverController extends Subsystem {

  private static DriverController instance = new DriverController();
  public static DriverController instance(){
    return instance;
  }

  private final double FORWARD_DEADZONE = .2;
  private final double STRAFE_DEADZONE = .2;
  private final double ROTATION_DEADZONE = .2;
  private final double SCALING_FACTOR_STANDARD = 1;
  private final int AVERAGING_WINDOW_SIZE = 5;
  private RollingAverage forwardAverager = new RollingAverage(AVERAGING_WINDOW_SIZE);
  private RollingAverage strafeAverager = new RollingAverage(AVERAGING_WINDOW_SIZE);
  private RollingAverage leftRotationAverager = new RollingAverage(AVERAGING_WINDOW_SIZE);
  private RollingAverage rightRotationAverager = new RollingAverage(AVERAGING_WINDOW_SIZE);

  // These values are written by the periodic timer thread and read on the main
  // thread. By marking them volatile we ensure a write is seen by the read.
  private volatile double forwardLatest = 0.0;
  private volatile double strafeLatest = 0.0;
  private volatile double rotationLatest = 0.0;

  private Joystick controller;
  private JoystickButton invertControlButton;
  private JoystickButton toggleDumptruckButton;
  private JoystickButton resetRobotToDefaultsButton;
  private JoystickButton rumbleButton;
  private JoystickButton driverCameraButton;

  // Write elevator info every 5 milliseconds
  private PeriodicExecutor periodicExecutor = new PeriodicExecutor("driver_controller", Duration.ofMillis(5), () -> {
    readPeriodicControls();
  });
  
  private DriverController() {
    controller = new Joystick(RobotMap.XBoxController.DRIVER_ID);

    invertControlButton = new JoystickButton(controller, RobotMap.XBoxController.START);
    toggleDumptruckButton = new JoystickButton(controller, RobotMap.XBoxController.X_BUTTON);
    resetRobotToDefaultsButton = new JoystickButton(controller, RobotMap.XBoxController.BACK);
    rumbleButton = new JoystickButton(controller, RobotMap.XBoxController.Y_BUTTON);
    driverCameraButton = new JoystickButton(controller, RobotMap.XBoxController.A_BUTTON);

    invertControlButton.whenReleased(new ToggleInvertedControl());
    toggleDumptruckButton.whenReleased(new ToggleDumpTruck());
    resetRobotToDefaultsButton.whenReleased(new ResetRobotToDefaults());
    driverCameraButton.whenReleased(new EnableDrivingCamera());

    rumbleButton.whenPressed(new Command(){
      public void execute(){
        controller.setRumble(RumbleType.kLeftRumble, 1);
      }
      protected boolean isFinished() {
        return true;
      }
    });
    
    rumbleButton.whenReleased(new Command(){
      public void execute(){
        controller.setRumble(RumbleType.kLeftRumble, 0);
      }
      protected boolean isFinished() {
        return true;
      }
    });

    periodicExecutor.start();
  }
  
  public double forwardSpeed() {
    return forwardLatest;
  }

  public double strafeSpeed() {
    return strafeLatest;
  }

  public double rotationSpeed() {
    return rotationLatest;
  }

  protected void readPeriodicControls(){
    double scalingFactor = scalingFactor();
    
    double forward = deadify(FORWARD_DEADZONE, controller.getRawAxis(RobotMap.XBoxController.LEFT_Y_AXIS));
    double strafe = deadify(STRAFE_DEADZONE, controller.getRawAxis(RobotMap.XBoxController.RIGHT_X_AXIS));
    double leftRotation = deadify(ROTATION_DEADZONE, -controller.getRawAxis(RobotMap.XBoxController.LEFT_TRIGGER));
    double rightRotation = deadify(ROTATION_DEADZONE, controller.getRawAxis(RobotMap.XBoxController.RIGHT_TRIGGER));

    double forwardAverage = scalingFactor * calculateAverage(forwardAverager, forward);
    double strafeAverage = scalingFactor * calculateAverage(strafeAverager, strafe);
    double leftRotationAverage = scalingFactor * calculateAverage(leftRotationAverager, leftRotation);
    double rightRotationAverage = scalingFactor * calculateAverage(rightRotationAverager, rightRotation);

    double normalizedRotation = leftRotationAverage + rightRotationAverage;

    // We calculate all then assign so there is as little time between assigning 
    // the y and z. Technically we could use a lock here but would gain little as
    // each of these are read independently.
    forwardLatest = forwardAverage;
    strafeLatest = strafeAverage;
    rotationLatest = normalizedRotation;
  }

  private double deadify(double zone, double input){
    return Math.abs(input) < zone ? 0 : input;
  }

  private double calculateAverage(RollingAverage averager, double value){
    averager.addValue(value);
    return averager.getAverage();
  }

  private double scalingFactor(){
    return SCALING_FACTOR_STANDARD;
  }

  @Override
  protected void initDefaultCommand() {
    // Nothing
  }
}
