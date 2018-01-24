package org.usfirst.frc.team135.robot.subsystems;

import org.usfirst.frc.team135.robot.commands.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.*;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class DriveTrain extends Subsystem implements RobotMap{

	private static DriveTrain instance;
	public WPI_TalonSRX frontRightTalon, frontLeftTalon, rearRightTalon, rearLeftTalon;
	private MecanumDrive chassis;
	
	private static final int ENCODER_TICK_COUNT = 1024;
	private static final int ENCODER_QUAD_COUNT = (ENCODER_TICK_COUNT * 4);
	private static final double MOTOR_SETPOINT = 1000; //revs per minute
	private static final double MOTOR_SETPOINT_PER_100MS = (MOTOR_SETPOINT/60/10*ENCODER_QUAD_COUNT); 
															/*measures encoder ticks per 100ms
															60 seconds per min  
														    10 "100ms" per second
														    4096 NU (native units) per revolution */
	private double kP = 0; //proportionate constant
	private double kI = 0; //integral constant
	private double kD = 0; //derivative constant
	private double kF = 0; //feed-forward constant
	

	
	private String orientation;
															
	private DriveTrain()
	{
		ConfigureTalons(frontRightTalon, FRONT_RIGHT_TALON_ID);
		ConfigureTalons(frontLeftTalon, FRONT_LEFT_TALON_ID);
		ConfigureTalons(rearRightTalon, REAR_RIGHT_TALON_ID);
		ConfigureTalons(rearLeftTalon, REAR_LEFT_TALON_ID);
		
		kP= SmartDashboard.getNumber("P", 0);  //gets PID values from dashboard, 0 is default
		kI= SmartDashboard.getNumber("I", 0); 
		kD= SmartDashboard.getNumber("D", 0);
		kF= SmartDashboard.getNumber("F", 0);
		
		
		orientation = SmartDashboard.getString("Orientation (Robot/Field)", "Field");

	}
	
	public void ConfigureTalons(WPI_TalonSRX talon, int talon_id)
	{
		talon = new WPI_TalonSRX(talon_id);
		talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		talon.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 10, 10);
		talon.setSelectedSensorPosition(0, 0, 10);
		talon.setSensorPhase(false); //make true is velocity readings do not correlate with spinning direction
		
		talon.configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_100Ms, 10);
		talon.configVelocityMeasurementWindow(64, 0);
	
		talon.config_kP(0, kP, 10); //slot 0, value, timeoutMS
		talon.config_kI(0, kI, 10);
		talon.config_kD(0, kD, 10);
		talon.config_kF(0, kF, 10);

		InitializeDriveTrain();
	}
	
	public void InitializeDriveTrain()
	{
		chassis = new MecanumDrive(frontLeftTalon, rearLeftTalon,
								  frontRightTalon, rearRightTalon);
		chassis.setDeadband(.15);
		chassis.setSafetyEnabled(false);
	}
	public static DriveTrain getInstance()
	{
		if (instance == null)
		{
			instance = new DriveTrain();
		}
		return instance;
	}
	
	public double getEncoderCounts(WPI_TalonSRX talon)
	{
		return ((double)talon.getSelectedSensorPosition(0)) ;
	}
	
	public double getEncoderSpeed(WPI_TalonSRX talon)
	{
		return ((double)talon.getSelectedSensorVelocity(0));
	}
	
	public void driveFieldOriented(double y, double x, double rotationalRate, double fieldOrientation)
	{
		chassis.driveCartesian(x, -y, rotationalRate, fieldOrientation);
		
	}
	
	public void driveRobotOriented(double x, double y, double rotationalRate)
	{
		chassis.driveCartesian(x, -y, rotationalRate);
	}

	
	public void drivePolar(double magnitude, double angle, double rotationalRate)
	{
		chassis.drivePolar(magnitude, angle, rotationalRate);
	}
	
	public void driveSingleMotorPower(int id)
	{
		if (id == REAR_LEFT_TALON_ID)
		{
			rearLeftTalon.set(Robot.oi.GetManipY());
		}
		if (id == REAR_RIGHT_TALON_ID)
		{
			rearRightTalon.set(Robot.oi.GetManipY());
		}
		if (id == FRONT_LEFT_TALON_ID)
		{
			frontLeftTalon.set(Robot.oi.GetManipY());
		}
		if (id == FRONT_RIGHT_TALON_ID)
		{
			frontRightTalon.set(Robot.oi.GetManipY());
		}
		
	}
	
	public void driveSingleMotorVelocity(int id)
	{
		if (id == REAR_LEFT_TALON_ID)
		{
			rearLeftTalon.set(ControlMode.Velocity, Robot.oi.GetManipY()*MOTOR_SETPOINT_PER_100MS); 
			//full throttle joystick so it returns value of 1 * setpoint = setpoint
		}
		if (id == REAR_RIGHT_TALON_ID)
		{
			rearRightTalon.set(ControlMode.Velocity, Robot.oi.GetManipY()*MOTOR_SETPOINT_PER_100MS);
		}
		if (id == FRONT_LEFT_TALON_ID)
		{
			frontLeftTalon.set(ControlMode.Velocity, Robot.oi.GetManipY()*MOTOR_SETPOINT_PER_100MS);
		}
		if (id == FRONT_RIGHT_TALON_ID)
		{
			frontRightTalon.set(ControlMode.Velocity, Robot.oi.GetManipY()*MOTOR_SETPOINT_PER_100MS);
		}
	}
	
	public double getTalonVoltage(WPI_TalonSRX talon)
	{
		return talon.getMotorOutputVoltage();
	}

    public void initDefaultCommand() 
    {
    	setDefaultCommand(new DriveJ(orientation));
    }
   
}