/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.irontigers.subsystems.vision;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.irontigers.RobotMap;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

/**
 * Add your docs here.
 */
public class VisionDriving extends MecanumDrive{
    private static VisionDriving instance = new VisionDriving();
    public static VisionDriving instance(){
        return instance;
    }
    
    private PIDController driveAlignX;
    private PIDController driveAlignY;
    private VisionX seeX;
    private VisionY seeY;
    private VisionDriveX driveX;
    private VisionDriveY driveY;

    private volatile double x = 0;
    private volatile double y = 0;

    private VisionDriving(){
        super(new WPI_TalonSRX(RobotMap.DriveTrain.LEFT_FRONT), new WPI_TalonSRX(RobotMap.DriveTrain.LEFT_BACK), new WPI_TalonSRX(RobotMap.DriveTrain.RIGHT_FRONT), new WPI_TalonSRX(RobotMap.DriveTrain.RIGHT_BACK));
        
        driveAlignX = new PIDController(1, 0, 0, seeX, driveX);
        driveAlignY = new PIDController(1, 0, 0, seeY, driveY);
    }

    protected void setX(double xVal){
        x = xVal;
    }

    protected void setY(double yVal){
        y = yVal;
    }

    public void initialize(double setpoint) {
        driveAlignX.reset();
        driveAlignY.reset();
        driveAlignX.setContinuous(false);
        driveAlignY.setContinuous(false);
        driveAlignX.setOutputRange(-.35, .35);
        driveAlignY.setOutputRange(-.35, .35);
        driveAlignX.setPercentTolerance(1);
        driveAlignY.setPercentTolerance(1);
        driveAlignX.setSetpoint(setpoint);
        driveAlignY.setSetpoint(setpoint);
    }

    public void enable() {
        driveAlignX.enable();
        driveAlignY.enable();
    }

    public void driving(){
        super.driveCartesian(y, x, 0);
    }

    private void stop(){
        super.driveCartesian(0, 0, 0);
    }

    public boolean isComplete(){
        return driveAlignX.onTarget() && driveAlignY.onTarget();
    }

    public void disable(){
        driveAlignX.disable();
        driveAlignY.disable();
        stop();
    }

}
