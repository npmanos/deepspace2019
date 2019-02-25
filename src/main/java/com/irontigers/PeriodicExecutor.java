package com.irontigers;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PeriodicExecutor {

  public interface Action{
    public void execute();
  }

  private String name;
  private ScheduledExecutorService executor;
  private Duration periodDuration;
  private Action action;
  
  public PeriodicExecutor(String name, Duration periodDuration, Action action){
    this.name = name;
    executor = Executors.newSingleThreadScheduledExecutor();
    this.periodDuration = periodDuration;
    this.action = action;
  }

  public void start(){
    // create a single thread that will call execute every time it is executed and
    // schedule the thread to run periodically based on the periodDuration period
    // ie. If periodDuration is 200ms then the thread will run every 200ms
    executor.scheduleAtFixedRate(() -> safeExecute(), 0, periodDuration.toMillis(), TimeUnit.MILLISECONDS);
  }

  public void stop(){
    // stop the periodic execution
    if(!executor.isShutdown()){
      executor.shutdownNow();
    }
  }

  private void safeExecute(){
    try{
      action.execute();
    }
    catch(Throwable e){
      System.err.format("[%s] Caught Throwable but ignoring to protect periodic thread execution", name);
      e.printStackTrace(System.err);
    }
  }

}
