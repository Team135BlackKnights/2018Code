package org.usfirst.frc.team135.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.commands.teleop.*;

/**
 *
 */
public class Hang extends Subsystem implements RobotMap {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	static private Hang instance;
	
	WPI_VictorSPX hangMotor1;
	WPI_VictorSPX hangMotor2;
	
	public static Hang getInstance()
	{
		if (instance == null)
		{
			instance = new Hang();
		}
		return instance;
	}
	
	private Hang()
	{
		InitializeHangMotors();
	}
	public void InitializeHangMotors()
	{
		hangMotor1 = new WPI_VictorSPX(HANG.HANG_1_VICTOR_ID);
		hangMotor1.setInverted(false);
		hangMotor1.setSafetyEnabled(false);
		
		hangMotor2 = new WPI_VictorSPX(HANG.HANG_2_VICTOR_ID);
		hangMotor2.setInverted(true);
		hangMotor2.setSafetyEnabled(false);
	}
	
	public void RunHangMotor(double power)
	{
		hangMotor1.set(power);
		hangMotor2.set(power);
	}
    public void initDefaultCommand() {
    	
    }
}

