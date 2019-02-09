package com.irontigers.commands;

import com.irontigers.subsystems.DriveSystem;
import com.irontigers.subsystems.XBoxController;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Teleop command for Joystick controlled driving of the robot. Every call to
 * {@link TeleopDrive#execute()} will read the desired x, y, and rotate speeds
 * from the joystick, apply their respective scales, and pass those off to the
 * {@link DriveSystem}.
 *
 * {@link TeleopDrive#isFinished()} will always return false as this is our
 * Robot's default command.
 *
 * {@link TeleopDrive#end()} and {@link TeleopDrive#interrupted()} will result
 * in the {@link DriveSystem} being stopped but it will not cause this command
 * to consider itself finished.
 */

public class TeleopDrive extends Command {

  public TeleopDrive() {
    requires(DriveSystem.instance());
  }

  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    XBoxController controller = XBoxController.instance();
    DriveSystem.instance().drive(controller.forwardSpeed(),controller.strafeSpeed(), controller.rotationSpeed());
    if(controller.)
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    // This is our standard default command so we're never going to be done
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
