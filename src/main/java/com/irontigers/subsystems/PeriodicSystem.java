package com.irontigers.subsystems;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class PeriodicSystem extends Subsystem {

  private ScheduledExecutorService periodicExecutor;
  protected Duration periodDuration;
  
  public PeriodicSystem(Duration periodDuration){
    periodicExecutor = Executors.newSingleThreadScheduledExecutor();
    this.periodDuration = periodDuration;
  }

  protected abstract void execute();

  public void start(){
    // create a single thread that will call execute every time it is executed and
    // schedule the thread to run periodically based on the periodDuration period
    // ie. If periodDuration is 200ms then the thread will run every 200ms
    periodicExecutor.scheduleAtFixedRate(() -> safeExecute(), 0, periodDuration.toMillis(), TimeUnit.MILLISECONDS);
  }

  public void stop(){
    // stop the periodic execution
    if(!periodicExecutor.isShutdown()){
      periodicExecutor.shutdownNow();
    }
  }

  private void safeExecute(){
    try{
      execute();
    }
    catch(Throwable e){
      System.err.format("[%s] Caught Throwable but ignoring to protect periodic thread execution", this.getClass().getSimpleName());
      e.printStackTrace(System.err);
    }
  }

}
