package org.usfirst.frc.team135.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.commands.*;

import edu.wpi.first.wpilibj.MotorSafetyHelper;
import edu.wpi.first.wpilibj.Solenoid;

/**
 *
 */
public class Hang extends Subsystem implements RobotMap {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private static Hang instance;
	
	private static WPI_VictorSPX hangMotor;
	private static Solenoid hangSolenoid;
	private MotorSafetyHelper m_safetyHelper;
	
	private Hang()
	{
		InitializeHang();
		m_safetyHelper = new MotorSafetyHelper(hangMotor);
	}
	
	public static Hang getInstance()
	{
		if (instance == null)
		{
			instance = new Hang();
		}
		return instance;
	}
	
	public void InitializeHang()
	{
		hangMotor = new WPI_VictorSPX(HANG_VICTOR_ID);
		hangMotor.setInverted(false);
		hangMotor.setSafetyEnabled(true);
		
		hangSolenoid = new Solenoid(HANG_SOLENOID_CHANNEL);
		hangSolenoid.set(false);
		hangMotor.setSafetyEnabled(false);
	}
	
	public void DriveHangMotor(double power)
	{
		hangMotor.set(power);
	}
	
	public void ActivateElectricSolenoid(boolean value)
	{
		hangSolenoid.set(value);
		m_safetyHelper.feed();
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new DriveHangMotor());
    }
}

