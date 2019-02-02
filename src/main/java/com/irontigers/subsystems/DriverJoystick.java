/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.irontigers.subsystems;

import java.time.Duration;

import com.irontigers.DashboardPublisher;
import com.irontigers.RobotMap;
import com.irontigers.RollingAverage;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

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
 * {@link DriverJoystick#instance()} method from anywhere in the program. The
 * use of this coding pattern is useful in keeping the class self-contained and
 * easy to use.
 */

public class DriverJoystick extends PeriodicSystem {

  private static DriverJoystick instance = new DriverJoystick();

  public static DriverJoystick instance() {
    return instance;
  }

  private final double SCALING_FACTOR_STANDARD = 1;
  private final int AVERAGING_WINDOW_SIZE = 5;

  private RollingAverage yAverager = new RollingAverage(AVERAGING_WINDOW_SIZE);
  private RollingAverage xAverager = new RollingAverage(AVERAGING_WINDOW_SIZE);
  private RollingAverage zAverager = new RollingAverage(AVERAGING_WINDOW_SIZE);

  // These values are written by the periodic timer thread and read on the main
  // thread. By marking them volatile we ensure a write is seen by the read.
  private volatile double yLatest = 0.0;
  private volatile double xLatest = 0.0;
  private volatile double zLatest = 0.0;

  private Joystick joystick;
  private Button aButton; // speed up
  private Button bButton; // slow down

  // Elevator up (fast & slow)
  private Button rightBumber;
  private Button rightTrigger;

  // Elevator down (fast & slow)
  private Button leftBumber;
  private Button leftTrigger;


  private DriverJoystick() {
    // read the joystick location every 5 milliseconds
    super(Duration.ofMillis(5));

    joystick = new Joystick(RobotMap.Joystick.ID);
    // aButton = new JoystickButton(joystick, RobotMap.Joystick.BUTTON_A);
    // bButton = new JoystickButton(joystick, RobotMap.Joystick.BUTTON_B);

    // rightBumber = new JoystickButton(joystick, RobotMap.Joystick.RIGHT_BUMPER);
    // rightBumber.whenActive(new MoveElevator(.55));

    // Start the periodic reading of the joystick
    start();
  }

  protected void execute(){
    double scalingFactor = scalingFactor();
    double y = scalingFactor * calculateAverage(yAverager, joystick.getY());
    double x = scalingFactor * calculateAverage(xAverager, joystick.getX());
    double z = scalingFactor * calculateAverage(zAverager, joystick.getZ());

    // We calculate all then assign so there is as little time between assigning 
    // the y and z. Technically we could use a lock here but would gain little as
    // each of these are read independently.
    yLatest = y;
    xLatest = x;
    zLatest = z;
  }

  private double calculateAverage(RollingAverage averager, double value){
    averager.addValue(value);
    return averager.getAverage();
  }

  private double scalingFactor(){
    return SCALING_FACTOR_STANDARD;
  }

  public double yScaledSpeed() {
    return yLatest;
  }

  public double xScaledSpeed() {
    return xLatest;
  }

  public double zScaledRotation() {
    return zLatest;
  }
}
