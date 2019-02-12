/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.irontigers.subsystems.vision;

import edu.wpi.first.wpilibj.PIDOutput;

/**
 * Add your docs here.
 */
public class VisionDriveX implements PIDOutput { 

    public VisionDriveX(){}

    @Override
    public void pidWrite(double output){
        VisionDriving.instance().setX(output);
    }
}
