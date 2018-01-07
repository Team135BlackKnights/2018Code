package org.usfirst.frc.team135.robot.subsystems;

import org.usfirst.frc.team135.robot.RobotMap.motors;
import org.usfirst.frc.team135.robot.RobotMap.utilities;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem 
{
	private static DriveTrain instance
	
	private static final double DEADBAND = utilities.clamp(SmartDashboard.getNumber("Joystick Deadband", .05), 0, .15);
	private static final double PERCENT_POWER = utilities.clamp(SmartDashboard.getNumber("Drivetrain Motor Drive Percent Power", 1.0), 0, 1);
	private static final double POWER_CAP = utilities.clamp(SmartDashboard.getNumber("Drivetrain Motor Drive Power Cap", 1.0), .2, 1);
	
	private static final double FORWARD_TANK_TUNING = utilities.clamp(SmartDashboard.getNumber("Foward Tank Tuning Constant", 1.0), 0, 1.0);
	private static final double STRAFE_TANK_TUNING = utilities.clamp(SmartDashboard.getNumber("Strafe Tank Tuning Constant", 1.0), 0, 1.0);
	private static final double TURNING_TANK_TUNING = utilities.clamp(SmartDashboard.getNumber("Turn Tank Tuning Constant", 1.0), 0, 1.0);
	
	private SpeedControllerGroup leftSide, rightSide;
	
	private MecanumDrive chassis;
	private WPI_TalonSRX[] talons = new WPI_TalonSRX[6];
	
	private DriveTrain()
	{		
		for (int i = 0; i < 6; i++)
		{
			talons[i] = new WPI_TalonSRX(i);
		}
		
		leftSide = new SpeedControllerGroup(talons[motors.MID_LEFT], talons[motors.REAR_LEFT]);
		rightSide = new SpeedControllerGroup(talons[motors.MID_RIGHT], talons[motors.REAR_RIGHT]);
		
		chassis = new MecanumDrive(
				talons[motors.MID_LEFT], 
				talons[motors.REAR_LEFT], 
				talons[motors.MID_RIGHT], 
				talons[motors.REAR_RIGHT]);
		
		chassis.setDeadband(DEADBAND);
	}
	
	public static DriveTrain getInstance()
	{
		if (instance == null)
		{
			instance = new DriveTrain();
		}
		
		return instance;
	}
	
	public void TankDrive(double left, double right)
	{
		double forward_factor = (left + right) / 2;
		double turning_factor = (left - right) / 2;
		
		double l_setValue = forward_factor * FORWARD_TANK_TUNING + turning_factor + TURNING_TANK_TUNING;
		double r_setValue = forward_factor * FORWARD_TANK_TUNING - turning_factor + TURNING_TANK_TUNING;
		
		leftSide.set(l_setValue);
		rightSide.set(r_setValue);
		
		
	}
	
	public void CartesianDrive(double y, double x, double rotationalRate)
	{
		chassis.driveCartesian(CalcPower(y), CalcPower(x), rotationalRate);
	}
	
	public void PolarDrive(double magnitude, double angle, double rotationalRate)
	{
		chassis.drivePolar(CalcPower(magnitude), CalcPower(angle), rotationalRate);
	}
	
	public void Rotate(double rate)
	{
		chassis.drivePolar(0, 0, CalcPower(rate));
	}
	
	private double CalcPower(double input)
	{
		return Math.min(input * PERCENT_POWER, POWER_CAP);
	}
	

    public void initDefaultCommand() 
    {

    }
}

