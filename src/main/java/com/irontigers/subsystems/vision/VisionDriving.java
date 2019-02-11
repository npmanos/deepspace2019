/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.irontigers.subsystems.vision;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.irontigers.RobotMap;

import edu.wpi.first.wpilibj.drive.MecanumDrive;

/**
 * Add your docs here.
 */
public class VisionDriving extends MecanumDrive{
    private static VisionDriving instance = new VisionDriving();
    public static VisionDriving instance(){
        return instance;
    }

    private volatile double x = 0;
    private volatile double y = 0;

    private VisionDriving(){
        super(new WPI_TalonSRX(RobotMap.DriveTrain.LEFT_FRONT), new WPI_TalonSRX(RobotMap.DriveTrain.LEFT_BACK), new WPI_TalonSRX(RobotMap.DriveTrain.RIGHT_FRONT), new WPI_TalonSRX(RobotMap.DriveTrain.RIGHT_BACK));
    }

    public void setX(double xVal){
        x = xVal;
    }

    public void setY(double yVal){
        y = yVal;
    }

    public void driving(){
        super.driveCartesian(y, x, 0);
    }


}
