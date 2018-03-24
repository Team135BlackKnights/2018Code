package org.usfirst.frc.team135.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.commands.teleop.*;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Preferences;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;


/**
 *
 */
public class Intake extends Subsystem implements RobotMap{

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private static WPI_VictorSPX rightWheel, leftWheel;
	private static DoubleSolenoid claw;
	public static DoubleSolenoid retraction;
	private static Compressor compressor;
	
	private boolean compressorState = true;
	
	boolean rightWheelInverted = false;
	boolean leftWheelInverted = true;
	
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

		int rightWheel_ID = (Preferences.getInstance().getBoolean("Is Competition Bot?", true) ? COMPETITION.INTAKE.RIGHT_WHEEL_VICTOR_ID : PRACTICE.INTAKE.RIGHT_WHEEL_VICTOR_ID);
		int leftWheel_ID = (Preferences.getInstance().getBoolean("Is Competition Bot?", true) ? COMPETITION.INTAKE.LEFT_WHEEL_VICTOR_ID : PRACTICE.INTAKE.LEFT_WHEEL_VICTOR_ID);
		
		rightWheel = new WPI_VictorSPX(rightWheel_ID);
		leftWheel = new WPI_VictorSPX(leftWheel_ID);
		
		rightWheel.setInverted(rightWheelInverted);
		leftWheel.setInverted(leftWheelInverted);
		rightWheel.setSafetyEnabled(false);
		leftWheel.setSafetyEnabled(false);
	}
	public void InitializePneumatics()
	{
		int intake_open = (Preferences.getInstance().getBoolean("Is Competition Bot?", true) ? COMPETITION.INTAKE.MANDIBLE_OPEN_CHANNEL : PRACTICE.INTAKE.MANDIBLE_OPEN_CHANNEL);
		int intake_close = (Preferences.getInstance().getBoolean("Is Competition Bot?", true) ? COMPETITION.INTAKE.MANDIBLE_CLOSE_CHANNEL : PRACTICE.INTAKE.MANDIBLE_CLOSE_CHANNEL);

		int retract_open = (Preferences.getInstance().getBoolean("Is Competition Bot?", true) ? COMPETITION.INTAKE.RETRACT_IN_CHANNEL : PRACTICE.INTAKE.RETRACT_IN_CHANNEL);
		int retract_close = (Preferences.getInstance().getBoolean("Is Competition Bot?", true) ? COMPETITION.INTAKE.RETRACT_OUT_CHANNEL : PRACTICE.INTAKE.RETRACT_OUT_CHANNEL);
		
		claw = new DoubleSolenoid(intake_open, intake_close);
		retraction = new DoubleSolenoid(retract_open, retract_close);
		
		compressor = new Compressor(0);
		compressor.setClosedLoopControl(true);

		claw.set(DoubleSolenoid.Value.kOff);
		retraction.set(DoubleSolenoid.Value.kOff);
	}
	
	public void ToggleCompressor()
	{
		compressorState = !compressorState;
		compressor.setClosedLoopControl(compressorState);
	}

	public void ActivateClaw(DoubleSolenoid.Value value)
	{
		claw.set(value);
	}
	
	public void DriveWheels(double power)
	{
		rightWheel.set(-power);
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