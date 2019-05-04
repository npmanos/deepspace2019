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
import com.irontigers.commands.LimeAlign;
import com.irontigers.commands.TeleopDrive;
import com.irontigers.commands.DecreaseScaleFactor;
import com.irontigers.commands.IncreaseScaleFactor;
import com.irontigers.commands.ToggleInvertedControl;
import com.irontigers.itlib.controllers.XboxAxis;
import com.irontigers.itlib.controllers.XboxButton;
import com.irontigers.itlib.controllers.XboxController;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
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
 * {@link XBoxController#getInstance()} method from anywhere in the program. The
 * use of this coding pattern is useful in keeping the class self-contained and
 * easy to use.
 */

public class DriverController extends XboxController {

  private static DriverController instance = new DriverController();
  public static DriverController getInstance(){
    return instance;
  }

  private int scalingFactorMode = 2;

  private JoystickButton invertControlButton;
  private JoystickButton visionAlignButton;
  private JoystickButton cancelVision;
  private JoystickButton decreaseScaleButton;
  private JoystickButton increaseScaleButton;
  
  private DriverController() {
    super(RobotMap.XBoxController.DRIVER_ID);
    setScalingFactor();

    invertControlButton = getButton(XboxButton.Y);
    visionAlignButton = getButton(XboxButton.START);
    cancelVision = getButton(XboxButton.BACK);

    increaseScaleButton = getButton(XboxButton.RIGHT_BUMPER);
    decreaseScaleButton = getButton(XboxButton.LEFT_BUMPER);

    invertControlButton.whenReleased(new ToggleInvertedControl());
    visionAlignButton.whenPressed(new LimeAlign());
    cancelVision.whenPressed(new TeleopDrive());
    increaseScaleButton.whenPressed(new IncreaseScaleFactor());
    decreaseScaleButton.whenPressed(new DecreaseScaleFactor());
  }
  
  public double forwardSpeed() {
    return getAxis(XboxAxis.LEFT_Y);
  }

  public double strafeSpeed() {
    return getAxis(XboxAxis.RIGHT_X);
  }

  public double rotationSpeed() {
    return getAxis(XboxAxis.TRIGGERS);
  }

  public void increaseScalingFactor(){
    if(scalingFactorMode < 3){
      scalingFactorMode ++;
      setScalingFactor();
    }
  }

  public void decreaseScalingFactor(){
    if(scalingFactorMode > 1){
      scalingFactorMode --;
      setScalingFactor();
    }
  }

  private void setScalingFactor(){
    switch (scalingFactorMode){
      case 1: DashboardPublisher.instance().putDriver("Scaling Factor", .5);
              setScale(.5);
      case 2: DashboardPublisher.instance().putDriver("Scaling Factor", .75);
              setScale(.75);
      case 3: DashboardPublisher.instance().putDriver("Scaling Factor", 1);
              setScale(1);
      default: DashboardPublisher.instance().putDriver("Scaling Factor", .75);
               setScale(.75);
    }
  }
}
