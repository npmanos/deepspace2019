package com.irontigers.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.irontigers.Robot;
import com.irontigers.RobotMap;
import com.irontigers.commands.TeleopDrive;
import com.irontigers.itlib.subsystems.InvertibleSystem;
import com.irontigers.itlib.subsystems.drive.MecanumSystem;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
public class DriveSystem extends MecanumSystem {

  private static DriveSystem instance = new DriveSystem();
  public static DriveSystem getInstance(){
    return instance;
  }

  private DriveSystem(){
    super(new WPI_TalonSRX(RobotMap.DriveTrain.LEFT_FRONT), new WPI_TalonSRX(RobotMap.DriveTrain.LEFT_BACK), 
          new WPI_TalonSRX(RobotMap.DriveTrain.RIGHT_FRONT), new WPI_TalonSRX(RobotMap.DriveTrain.RIGHT_BACK));
    Robot.getInstance().setSubsystemInvertible(getInstance());
  }

  @Override
  public void initDefaultCommand(){
    // If there is no other ACTIVE command claiming a requirement on the DriveTrain then we will always
    // default to TelopDrive command

    setDefaultCommand(new TeleopDrive());
  }

}
