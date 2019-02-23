package com.irontigers.commands;

import com.irontigers.subsystems.DashboardPublisher;
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
    double minPosition = (goalPosition + ElevatorSystem.instance().getOffSet()) * (1 - leeway);
    double maxPosition = (goalPosition + ElevatorSystem.instance().getOffSet()) * (1 + leeway);
    boolean inRange = (currentPosition > minPosition) && (currentPosition < maxPosition);
    return (inRange || ElevatorSystem.instance().wrongWay());
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    // TODO: if needs an end command, code it here
    ElevatorSystem.instance().stop();
    DashboardPublisher.instance().putDriver("Elevator Level", 1);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}