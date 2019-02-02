/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.irontigers.subsystems;

import java.time.Duration;

import com.irontigers.RobotMap;
import com.irontigers.RollingAverage;

import edu.wpi.first.wpilibj.I2C;

public class LidarSystem extends PeriodicSystem {

  private static LidarSystem instance = new LidarSystem();
  public static LidarSystem instance() {
    return instance;
  }

  private I2C i2c;
  
  private final int AVERAGING_WINDOW_SIZE = 5;
  private RollingAverage distanceAverager = new RollingAverage(AVERAGING_WINDOW_SIZE);

  // These values are written by the periodic timer thread and read on the main
  // thread. By marking them volatile we ensure a write is seen by the read.
  private volatile double distanceLatest = 0.0;

  private LidarSystem() {
    // read the lidar location every 50 milliseconds
    // TODO: change based on lidar required delay
    super(Duration.ofMillis(50));

    i2c = new I2C(RobotMap.Lidar.PORT, RobotMap.Lidar.ADDRESS);

    // Start the periodic reading of the lidar
    start();
  }

  protected void execute(){

    // 0x04 is the value to tell the LIDAR we are about to read from it
    i2c.write(RobotMap.Lidar.Register.CONFIG, 0x04);
    delay(20);
    byte[] distanceBytes = new byte[2];
    i2c.read(RobotMap.Lidar.Register.DISTANCE, distanceBytes.length, distanceBytes);

    double nativeValue = (distanceBytes[0] << 8) + distanceBytes[1];

    System.out.println(String.format("LIDAR Native: %d", nativeValue));
  }

  private void delay(int milliseconds){
    try{
      Thread.sleep(milliseconds);
    }catch(InterruptedException ignored){
      // we don't care about this
    }
  }
}
