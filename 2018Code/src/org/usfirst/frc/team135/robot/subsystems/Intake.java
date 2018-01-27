package org.usfirst.frc.team135.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team135.robot.RobotMap;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;


/**
 *
 */
public class Intake extends Subsystem implements RobotMap{

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private static WPI_VictorSPX rightWheel, leftWheel;
	private static DoubleSolenoid claw;
	private static DoubleSolenoid retraction;
	private static Compressor compressor;
	
	boolean mandiblesRetracted;
	boolean clawClosed;
	boolean rightWheelInverted = true;
	boolean leftWheelInverted = false;
	
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
		rightWheel = new WPI_VictorSPX(RIGHT_WHEEL_VICTOR_ID);
		leftWheel = new WPI_VictorSPX(LEFT_WHEEL_VICTOR_ID);
		
		rightWheel.setInverted(rightWheelInverted);
		leftWheel.setInverted(leftWheelInverted);
		rightWheel.setSafetyEnabled(false);
		leftWheel.setSafetyEnabled(false);
		
		claw = new DoubleSolenoid(MANDIBLE_OPEN_CHANNEL, MANDIBLE_CLOSE_CHANNEL);
		retraction = new DoubleSolenoid(RETRACT_IN_CHANNEL, RETRACT_OUT_CHANNEL);
		
		compressor = new Compressor(0);
		compressor.setClosedLoopControl(true);
		
		clawClosed = false;
		mandiblesRetracted = true;
		claw.set(DoubleSolenoid.Value.kOff);
		retraction.set(DoubleSolenoid.Value.kOff);
	}

	public void ActivateClaw()
	{
		if (clawClosed)
		{
			claw.set(DoubleSolenoid.Value.kForward);
			clawClosed = false;
		}
		else
		{
			claw.set(DoubleSolenoid.Value.kReverse);
			clawClosed = true;
		}
	}
	
	public void DriveWheels(double power)
	{
		rightWheel.set(power);
		leftWheel.set(power);
	}	
	public void RetractMandibles()
	{
		if (mandiblesRetracted)
		{
			retraction.set(DoubleSolenoid.Value.kForward);
			mandiblesRetracted = false;
		}
		else
		{
			retraction.set(DoubleSolenoid.Value.kReverse);
			mandiblesRetracted = true;
		}
	}


    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

