/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.irontigers.subsystems;

import java.time.Duration;
import java.nio.ByteBuffer;
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
  private volatile boolean startNewRead = true;

  private LidarSystem() {
    // read the lidar location every 50 milliseconds
    // TODO: change based on lidar required delay
    super(Duration.ofMillis(1000));

    i2c = new I2C(RobotMap.Lidar.PORT, RobotMap.Lidar.ADDRESS);

    i2c.write(0x04, 0x08 | 32);
    i2c.write(0x11, 0xff);
    i2c.write(0x00, 0x04);

    // Start the periodic reading of the lidar
    start();
  }

  protected void execute(){
    // System.out.println("I executed");
    // i2c.write(0, (byte)0x04);

    // delay(20);

    // byte[] waitBuf = new byte[1];
    // do{
    //   System.out.println("I'm endless");
    //   i2c.read((byte)0x01, 1, waitBuf);
    // }while((waitBuf[0] & 1) > 0);

    byte[] valueBytes = new byte[2];
    i2c.read(0x8f, 2, valueBytes);

    ByteBuffer valueBuffer = ByteBuffer.wrap(valueBytes);
    short distanceCentimeters = valueBuffer.getShort();

    System.out.println("Native: " + distanceCentimeters);
    // System.out.println("=============================");

    // delay(10);
  }

  private void printBytes(byte[] bytes){
    for(byte b : bytes){
      System.out.println(b + " " + (int)b + " " + (int)b);
    }
  }

  private void delay(int milliseconds){
    try{
      Thread.sleep(milliseconds);
    }catch(InterruptedException ignored){
      System.out.println("error during delay");
      // we don't care about this
    }
  }

  private int getBit(byte[] byteArray, int pos){
    int posByte = pos/8;
    int posBit = pos%8;
    byte valByte = byteArray[posByte];
    int valInt = valByte >> (posBit + 1) & 1;
    return valInt;
  }
}



