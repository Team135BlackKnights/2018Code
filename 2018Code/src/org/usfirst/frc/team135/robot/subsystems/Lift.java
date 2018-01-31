package org.usfirst.frc.team135.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.RobotMap.*;

/**
 *
 */
public class Lift extends Subsystem implements RobotMap
{
	private static Lift instance;
	
	private VictorSPX liftMotor;
	
	private Lift()
	{
		liftMotor = new VictorSPX(LIFT.LIFT_MOTOR);
		liftMotor.setInverted(true);
	}
	
	public static Lift getInstance()
	{
		if (instance == null)
		{
			instance = new Lift();
		}
		
		return instance;
	}
	
	public void set(double speed)
	{
		liftMotor.set(ControlMode.PercentOutput, speed);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

