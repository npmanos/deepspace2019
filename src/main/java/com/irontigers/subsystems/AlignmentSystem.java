package com.irontigers.subsystems;

import java.time.Duration;

/**
 * Alignment system for the robot.
 *
 * This class, along with all Subsystems, uses the Singleton pattern to ensure
 * only a single instance of this Subsystem exists during the lifetime of the
 * Robot program. The instance will be created statically (the first time this
 * class is used) and can be accessed via the static
 * {@link AlignmentSystem#instance()} method from anywhere in the program. The
 * use of this coding pattern is useful in keeping the class self-contained and
 * easy to use.
 */
public class AlignmentSystem extends PeriodicSystem {

  private static AlignmentSystem instance = new AlignmentSystem();
  public static AlignmentSystem instance(){
    return instance;
  }

  private volatile boolean hasLeftLine = false;
  private volatile boolean hasRightLine = false;
  private volatile boolean hasBothLines = false;
  private volatile boolean isUsable = false;
  private volatile double scaledDistanceToCenter = Double.NaN;

  private AlignmentSystem(){
    // calculate alignment every 5 milliseconds
    super(Duration.ofMillis(5));

    // Start the periodic reading of the joystick
    start();
  }

  protected void execute(){
    // TODO: Read values off the NetworkTable and calculate our current alignment
  }

  public boolean isUsable(){
    return isUsable;
  }

  public double scaledDistanceToCenter(){
    return scaledDistanceToCenter;
  }
}
