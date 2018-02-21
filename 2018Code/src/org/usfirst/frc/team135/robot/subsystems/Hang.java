package org.usfirst.frc.team135.robot.subsystems;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Solenoid;
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
	
	Solenoid hangSolenoid; 
	
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
		InitializeHangSolenoid();
	}
	public void InitializeHangMotors()
	{
		int id = (Preferences.getInstance().getBoolean("Is Competition Bot?", true) ? COMPETITION.HANG.HANG_1_VICTOR_ID : PRACTICE.HANG.HANG_1_VICTOR_ID);
		
		hangMotor1 = new WPI_VictorSPX(id);
		hangMotor1.setInverted(false);
		hangMotor1.setSafetyEnabled(false);
		
	}
	
	public void InitializeHangSolenoid()
	{
		int port = Preferences.getInstance().getBoolean("Is Competition Bot?", true) ? COMPETITION.HANG.SOLENOID_PORT : PRACTICE.HANG.SOLENOID_PORT;
		hangSolenoid = new Solenoid(port);
		hangSolenoid.set(false);
	}
	
	public void RunHangMotor(double power)
	{
		hangMotor1.set(power);	
	}
	
	public void setRelease(boolean isReleased)
	{
		hangSolenoid.set(isReleased);
	}
    public void initDefaultCommand() {
    	
    }
}

