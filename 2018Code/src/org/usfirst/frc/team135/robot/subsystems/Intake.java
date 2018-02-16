package org.usfirst.frc.team135.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.commands.teleop.*;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;


/**
 * 
 *
 */
public class Intake extends Subsystem implements RobotMap{

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private static WPI_VictorSPX rightWheel, leftWheel;
	private static DoubleSolenoid claw;
	public static DoubleSolenoid retraction;
	private static Compressor compressor;
	
	boolean rightWheelInverted = false;
	boolean leftWheelInverted = true;
	
	private boolean isPositionInitialized = false;
	
	static private Intake instance;
	
	public static Intake GetInstance()
	{
		if (instance == null)
		{
			instance = new Intake();
		}
		return instance;
	}
	
	private Intake()
	{
		InitializeWheelMotors();
		InitializePneumatics();

	}
	
	public void InitializeWheelMotors()
	{
		rightWheel = new WPI_VictorSPX(INTAKE.RIGHT_WHEEL_VICTOR_ID);
		leftWheel = new WPI_VictorSPX(INTAKE.LEFT_WHEEL_VICTOR_ID);
		
		rightWheel.setInverted(rightWheelInverted);
		leftWheel.setInverted(leftWheelInverted);
		rightWheel.setSafetyEnabled(false);
		leftWheel.setSafetyEnabled(false);
	}
	public void InitializePneumatics()
	{
		claw = new DoubleSolenoid(INTAKE.MANDIBLE_OPEN_CHANNEL, INTAKE.MANDIBLE_CLOSE_CHANNEL);
		retraction = new DoubleSolenoid(INTAKE.RETRACT_IN_CHANNEL, INTAKE.RETRACT_OUT_CHANNEL);
		
		claw.set(DoubleSolenoid.Value.kReverse);
		retraction.set(DoubleSolenoid.Value.kReverse);
		
		compressor = new Compressor(0);
		compressor.setClosedLoopControl(true);
	}

	public void ActivateClaw(DoubleSolenoid.Value value)
	{
		claw.set(value);
	}
	
	public void DriveWheels(double power)
	{
		rightWheel.set(power);
		leftWheel.set(power);
	}	
	public void MoveMandibles(DoubleSolenoid.Value value)
	{
		retraction.set(value);
	}
	public DoubleSolenoid.Value GetSolenoidPosition(DoubleSolenoid solenoid)
	{
		return solenoid.get();
	}


    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}