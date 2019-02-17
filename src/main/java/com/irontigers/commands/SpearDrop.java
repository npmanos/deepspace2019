package com.irontigers.commands;

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
    startPos = Math.abs(ElevatorSystem.instance().getRawPosition());
  }

  @Override
  protected void execute() {
    ElevatorSystem.instance().move(-.5);
    
  }

  @Override
  protected boolean isFinished() {
    if(Math.abs(ElevatorSystem.instance().getRawPosition()) > (startPos - dropAmount) * (1 + leeway)) {
        return false;
    }else{
        return true;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    ElevatorSystem.instance().stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}