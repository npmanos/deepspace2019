/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.irontigers.subsystems;

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

public class DriverJoystick {

  private static DriverJoystick instance = new DriverJoystick();

  public static DriverJoystick instance() {
    return instance;
  }

  private final double SCALING_FACTOR_STANDARD = 1;

  private RollingAverage yAverage = new RollingAverage(3);
  private RollingAverage xAverage = new RollingAverage(3);
  private RollingAverage zAverage = new RollingAverage(3);

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
    joystick = new Joystick(RobotMap.Joystick.ID);
    // aButton = new JoystickButton(joystick, RobotMap.Joystick.BUTTON_A);
    // bButton = new JoystickButton(joystick, RobotMap.Joystick.BUTTON_B);

    // rightBumber = new JoystickButton(joystick, RobotMap.Joystick.RIGHT_BUMPER);
    // rightBumber.whenActive(new MoveElevator(.55));

  }

  private double scalingFactor(){
    return SCALING_FACTOR_STANDARD;
  }

  private double yInputSpeed() {
    yAverage.addValue(joystick.getY());
    return yAverage.getAverage();
  }

  public double yScaledSpeed() {
    return yInputSpeed() * scalingFactor();
  }

  private double xInputSpeed() {
    xAverage.addValue(joystick.getX());
    return xAverage.getAverage();
  }

  public double xScaledSpeed() {
    return xInputSpeed() * scalingFactor();
  }

  private double zInputRotation() {
    zAverage.addValue(joystick.getZ());
    return zAverage.getAverage();
  }

  public double zScaledRotation() {
    return zInputRotation() * scalingFactor();
  }

}
