/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.irontigers.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * Add your docs here.
 */
public class ITRT_TalonSRX extends WPI_TalonSRX implements PIDSource {
    PIDSourceType m_pidSource = PIDSourceType.kDisplacement;
    public ITRT_TalonSRX(int deviceNumber){
        super(deviceNumber);
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource){
        m_pidSource = pidSource;
    }

    @Override
    public PIDSourceType getPIDSourceType(){
        return m_pidSource;
    }

    @Override
    public double pidGet(){
        double encOut = super.getSelectedSensorPosition();
        return encOut;
    }
}
