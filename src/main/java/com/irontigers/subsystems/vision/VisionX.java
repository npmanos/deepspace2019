package com.irontigers.subsystems.vision;

import java.time.Duration;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * Alignment system for the robot.
 *
 * This class, along with all Subsystems, uses the Singleton pattern to ensure
 * only a single instance of this Subsystem exists during the lifetime of the
 * Robot program. The instance will be created statically (the first time this
 * class is used) and can be accessed via the static
 * {@link VisionY#instance()} method from anywhere in the program. The
 * use of this coding pattern is useful in keeping the class self-contained and
 * easy to use.
 */
public class VisionX implements PIDSource {

  PIDSourceType m_pidSource = PIDSourceType.kDisplacement;

  edu.wpi.first.networktables.NetworkTable table;


  public VisionX(){
    table = NetworkTableInstance.getDefault().getTable("limelight");
  }

  public double getRawValue(){
    return table.getEntry("tx").getDouble(0.0);
  }

  @Override
  public void setPIDSourceType(PIDSourceType pidSourceType){
    m_pidSource = pidSourceType;
  }

  @Override
  public PIDSourceType getPIDSourceType() {
    return m_pidSource;
  }

  @Override
  public double pidGet(){
    return table.getEntry("tx").getDouble(0.0);
  }
}
