package com.irontigers.commands;

import com.irontigers.subsystems.DashboardPublisher;
import com.irontigers.subsystems.ElevatorSystem;

import edu.wpi.first.wpilibj.command.Command;

public class SpearDrop extends Command {
  public double i2;

  private int dropAmount = 2393;
  private double leeway = .005;
  double startPos;

  public SpearDrop(){
    requires(ElevatorSystem.instance());
  }

  @Override
  protected void initialize() {
    DashboardPublisher.instance().putDriver("Moving to position", true);
    startPos = Math.abs(ElevatorSystem.instance().getRawPosition());
  }

  @Override
  protected void execute() {
    ElevatorSystem.instance().move(-.5);
    
  }

  @Override
  protected boolean isFinished() {
    double currentPosition = Math.abs(ElevatorSystem.instance().getRawPosition());
    double endPosition = (startPos - dropAmount) * (1 + leeway);
    if(ElevatorSystem.instance().isLowerLimitSwitch() || ElevatorSystem.instance().wrongWay()){
        return true;
    }
    else if(currentPosition > endPosition){
        return false;
    }else{
      return true;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    ElevatorSystem.instance().stop();
    DashboardPublisher.instance().putDriver("Moving to position", false);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}