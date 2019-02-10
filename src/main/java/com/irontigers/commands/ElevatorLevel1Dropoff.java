package com.irontigers.commands;

import com.irontigers.subsystems.ElevatorSystem;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorLevel1Dropoff extends Command {

  private int goalPosition = 2500;

  public ElevatorLevel1Dropoff(){
    requires(ElevatorSystem.instance());
  }

  @Override
  protected void execute() {
    double currentPosition = Math.abs(ElevatorSystem.instance().getRawPosition());
    
    if(currentPosition > goalPosition * 1.05){
      ElevatorSystem.instance().move(-.5);
    }
    else if(currentPosition < goalPosition * .95){
      ElevatorSystem.instance().move(.5);
    }
    else{
      ElevatorSystem.instance().stop();
    }
  }

  @Override
  protected boolean isFinished() {
    double currentPosition = Math.abs(ElevatorSystem.instance().getRawPosition());
    return (currentPosition > goalPosition * .95) && (currentPosition < goalPosition * 1.05);
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