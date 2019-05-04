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
import com.irontigers.commands.BottomOutElevator;
import com.irontigers.commands.ElevatorDownPID;
import com.irontigers.commands.ElevatorLevel1PID;
import com.irontigers.commands.ElevatorLevel2PID;
import com.irontigers.commands.ElevatorLevel3;
import com.irontigers.commands.ElevatorLevel3PID;
import com.irontigers.commands.ReturnNavigatorControl;
import com.irontigers.commands.SpearIn;
import com.irontigers.commands.SpearOutAndDrop;
import com.irontigers.commands.ZeroEncoders;
import com.irontigers.itlib.controllers.XboxAxis;
import com.irontigers.itlib.controllers.XboxButton;
import com.irontigers.itlib.controllers.XboxController;
import com.irontigers.commands.ToggleDumpTruck;

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

public class NavigatorController extends XboxController {

  private static NavigatorController instance = new NavigatorController();
  public static NavigatorController instance(){
    return instance;
  }

  private Joystick controller;
  private JoystickButton spearInButton;
  private JoystickButton spearOutAndDropButton;
  private JoystickButton elevatorLevel1Button;
  private JoystickButton elevatorLevel2Button;
  private JoystickButton elevatorLevel3Button;
  private JoystickButton zeroEncoderButton;
  private JoystickButton toggleDumpTruckButton;
  private Double ELEVATOR_DEADZONE = .1;
  private JoystickButton bottomOutElevator;
  private JoystickButton cancelCommandsButton;
  
  private NavigatorController() {
    super(RobotMap.XBoxController.NAVIGATOR_ID);

    elevatorLevel1Button = getButton(XboxButton.A);
    elevatorLevel2Button = getButton(XboxButton.B);
    spearInButton = getButton(XboxButton.LEFT_BUMPER);
    spearOutAndDropButton = getButton(XboxButton.RIGHT_BUMPER);
    zeroEncoderButton = getButton(XboxButton.START);
    toggleDumpTruckButton = getButton(XboxButton.LEFT_AXIS);
    bottomOutElevator = getButton(XboxButton.X);
    cancelCommandsButton = getButton(XboxButton.BACK);
    // While held down
    spearInButton.whenPressed(new SpearIn());
    spearOutAndDropButton.whenPressed(new SpearOutAndDrop());
    toggleDumpTruckButton.whenReleased(new ToggleDumpTruck());
    bottomOutElevator.whenReleased(new BottomOutElevator());
    // Singular press
    elevatorLevel1Button.whenReleased(new ElevatorLevel1PID());
    elevatorLevel2Button.whenReleased(new ElevatorLevel2PID());
    elevatorLevel3Button.whenReleased(new ElevatorLevel3());
    zeroEncoderButton.whenReleased(new ZeroEncoders());
    cancelCommandsButton.whenPressed(new ReturnNavigatorControl());
  }

  public double elevatorSpeed(){ 
      return .6 * getAxis(XboxAxis.TRIGGERS);
  }
}
