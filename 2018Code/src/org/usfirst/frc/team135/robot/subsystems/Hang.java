package org.usfirst.frc.team135.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
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
	private boolean _hangState = false;
	
	private WPI_VictorSPX _hangMotor1;
	private WPI_TalonSRX _testHangMotor;
	
	private Solenoid _hangSolenoid; 
	
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
		int hangID = (Preferences.getInstance().getBoolean("Is Competition Bot?", true) ? COMPETITION.HANG.HANG_1_VICTOR_ID : PRACTICE.HANG.HANG_1_VICTOR_ID);
		int testID = (Preferences.getInstance().getBoolean("Is Competition Bot?", true) ? COMPETITION.HANG.TEST_TALON_ID : PRACTICE.HANG.TEST_TALON_ID);
		
		this._hangMotor1 = new WPI_VictorSPX(hangID);
		this._hangMotor1.setInverted(true);
		this._hangMotor1.setSafetyEnabled(false);
	
		
		this._testHangMotor = new WPI_TalonSRX(testID);
		
	}
	
	public void InitializeHangSolenoid()
	{
		int port = Preferences.getInstance().getBoolean("Is Competition Bot?", true) ? COMPETITION.HANG.SOLENOID_PORT : PRACTICE.HANG.SOLENOID_PORT;
		this._hangSolenoid = new Solenoid(port);
		this._hangSolenoid.set(false);
	}
			
	public void runHangMotor(double power)
	{
		this._hangMotor1.set(-power);	
	}
	
	public void runTestMotor(double power)
	{
		this._testHangMotor.set(power);
	}
	
	public void toggle()
	{
		/*if (DriverStation.getInstance().getMatchTime() <= 30)
		{

		}*/
		this._hangState = !_hangState;
		this._hangSolenoid.set(_hangState);

	}
    public void initDefaultCommand() {
    	
    }
   
}

