package com.irontigers.commands;

import com.irontigers.subsystems.ElevatorSystem;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorLevel1 extends Command {

  private int goalPosition = 1879;
  private double leeway = .02;

  public ElevatorLevel1(){
    requires(ElevatorSystem.instance());
  }

  @Override
  protected void execute() {
    double currentPosition = Math.abs(ElevatorSystem.instance().getRawPosition());
    
    if(currentPosition > (goalPosition + ElevatorSystem.instance().getOffSet()) * (1 + leeway)){
      ElevatorSystem.instance().move(-.7);
    }
    else if(currentPosition < (goalPosition + ElevatorSystem.instance().getOffSet()) * (1 - leeway)){
      ElevatorSystem.instance().move(.7);
    }
    else{
      ElevatorSystem.instance().stop();
    }
  }

  @Override
  protected boolean isFinished() {
    double currentPosition = Math.abs(ElevatorSystem.instance().getRawPosition());
    return (currentPosition > (goalPosition + ElevatorSystem.instance().getOffSet()) * (1 - leeway)) && (currentPosition < (goalPosition + ElevatorSystem.instance().getOffSet()) * (1 + leeway));
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