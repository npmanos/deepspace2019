package com.irontigers.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.irontigers.subsystems.ElevatorSystem;

public class ElevatorLevel1Pickup extends Command {

  public ElevatorLevel1Pickup(){
    double positionPickup = 123;
    // TODO: add all systems this command will use
    // requires(ElevatorSystem.instance());
  }

  @Override
  protected void execute() {
    if(ElevatorSystem.instance().getRawPosition() > 1.05 * positionPickup){
        ElevatorSystem.instance().move(-.5);
    }
    else if(ElevatorSystem.instance().getRawPosition() < .95 * positionPickup){
       ElevatorSystem.instance().move(.5);
    }
    else{
      ElevatorSystem.instance().stop();
    }
  }

  @Override
  protected boolean isFinished() {
    if(.95 * positionPickup < ElevatorSystem.instance().getRaw && 1.05 * positionPickup > ElevatorSystem.instance().getRaw){
        return true;
    }
    else{return false};
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    // TODO: if needs an end command, code it here
    ElevatorSystem.instance().stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}