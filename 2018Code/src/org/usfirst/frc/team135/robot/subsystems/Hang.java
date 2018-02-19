package org.usfirst.frc.team135.robot.subsystems;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	
	WPI_VictorSPX hangMotor;
	
	Solenoid hangSolenoid; 
	
	public boolean state = true;
	
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
		
		hangMotor = new WPI_VictorSPX(id);
		hangMotor.setInverted(false);
		hangMotor.setSafetyEnabled(false);

	}
	
	public void InitializeHangSolenoid()
	{
		hangSolenoid = new Solenoid(4);
		hangSolenoid.set(false);
	}
	
	public void RunHangMotor(double power)
	{
		hangMotor.set(power);
	}
	
	public void toggleState()
	{
		System.out.println(state);
		state = !state;
		hangSolenoid.set(state);
		
	}
    public void initDefaultCommand() {
    	
    }
}

