package org.usfirst.frc.team135.robot.subsystems;

import org.usfirst.frc.team135.robot.commands.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.*;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import edu.wpi.first.wpilibj.drive.*;


import edu.wpi.first.wpilibj.MotorSafetyHelper;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

/**
 *
 */
public class DriveTrain extends Subsystem implements RobotMap{

	private static DriveTrain instance;
	public WPI_TalonSRX frontRightTalon, frontLeftTalon, rearRightTalon, rearLeftTalon;
	private MecanumDrive chassis; 
	
	private static final int ENCODER_TICK_COUNT = 256;
	private static final int ENCODER_QUAD_COUNT = (ENCODER_TICK_COUNT * 4);
	private static final double MOTOR_SETPOINT_PER_100MS = 289; //NU/100 ms MAX SPEED for slowest motor
	
	protected MotorSafetyHelper m_safetyHelper = new MotorSafetyHelper(chassis); //watchdog
	
	private double RearRightkP;  //PID constants for each of the drive talons
	private double RearRightkI;
	private double RearRightkD;
	private double RearRightkF;

	private double RearLeftkP; 
	private double RearLeftkI;
	private double RearLeftkD;
	private double RearLeftkF;
	
	private double FrontRightkP; 
	private double FrontRightkI;
	private double FrontRightkD;
	private double FrontRightkF;
	
	private double FrontLeftkP; 
	private double FrontLeftkI;
	private double FrontLeftkD;
	private double FrontLeftkF;

	private String orientation;
				

	
	private DriveTrain()
	{
		
		frontRightTalon = new WPI_TalonSRX(FRONT_RIGHT_TALON_ID);
		frontLeftTalon = new WPI_TalonSRX(FRONT_LEFT_TALON_ID);
		rearRightTalon = new WPI_TalonSRX(REAR_RIGHT_TALON_ID);
		rearLeftTalon = new WPI_TalonSRX(REAR_LEFT_TALON_ID);		

		ConfigureTalons(frontRightTalon, FRONT_RIGHT_TALON_ID);
		ConfigureTalons(frontLeftTalon, FRONT_LEFT_TALON_ID);
		ConfigureTalons(rearRightTalon, REAR_RIGHT_TALON_ID);
		ConfigureTalons(rearLeftTalon, REAR_LEFT_TALON_ID);
		
		InitializeDriveTrain();
	
		orientation = Preferences.getInstance().getString("Orientation", "Robot");
	}
	
	public void ConfigureTalons(WPI_TalonSRX talon, int talon_id)
	{
		ConfigureEncoderDirection();
		
		talon.setNeutralMode(NeutralMode.Brake);
		talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		talon.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 10, 10);
		talon.setSelectedSensorPosition(0, 0, 10);
		
		talon.configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_100Ms, 10);
		talon.configVelocityMeasurementWindow(64, 0);
		
		//InitializeDriveTrain();
	}
	
	public void ConfigureEncoderDirection()
	{
		rearLeftTalon.setSensorPhase(false); //true
		rearRightTalon.setSensorPhase(true); 
		frontLeftTalon.setSensorPhase(false);
		frontRightTalon.setSensorPhase(true); //false
	}
	
	public double returnVelocity()
	{
		double value = frontRightTalon.getSelectedSensorVelocity(0);
		return value;
	}
	
	public void InitializeDriveTrain()
	{
		RearRightkP = Preferences.getInstance().getDouble("RearRightP", 0); //get PID constants from Dashboard
		RearRightkI = Preferences.getInstance().getDouble("RearRightI", 0);
		RearRightkD = Preferences.getInstance().getDouble("RearRightD", 0);
		RearRightkF = Preferences.getInstance().getDouble("RearRightF", 0);
		
		RearLeftkP = Preferences.getInstance().getDouble("RearLeftP", 0);
		RearLeftkI = Preferences.getInstance().getDouble("RearLeftI", 0);
		RearLeftkD = Preferences.getInstance().getDouble("RearLeftD", 0);
		RearLeftkF = Preferences.getInstance().getDouble("RearLeftF", 0);
		
		FrontRightkP = Preferences.getInstance().getDouble("FrontRightP", 0);
		FrontRightkI = Preferences.getInstance().getDouble("FrontRightI", 0);
		FrontRightkD = Preferences.getInstance().getDouble("FrontRightD", 0);
		FrontRightkF = Preferences.getInstance().getDouble("FrontRightF", 0);
		
		FrontLeftkP = Preferences.getInstance().getDouble("FrontLeftP", 0);
		FrontLeftkI = Preferences.getInstance().getDouble("FrontLeftI", 0);
		FrontLeftkD = Preferences.getInstance().getDouble("FrontLeftD", 0);
		FrontLeftkF = Preferences.getInstance().getDouble("FrontLeftF", 0);
		
		rearRightTalon.config_kP(0, RearRightkP, 10); //configure talons with PID constants
		rearRightTalon.config_kI(0, RearRightkI, 10);
		rearRightTalon.config_kD(0, RearRightkD, 10);
		rearRightTalon.config_kF(0, RearRightkF, 10);
		
		frontRightTalon.config_kP(0, FrontRightkP, 10);
		frontRightTalon.config_kI(0, FrontRightkI, 10);
		frontRightTalon.config_kD(0, FrontRightkD, 10);
		frontRightTalon.config_kF(0, FrontRightkF, 10);
		
		rearLeftTalon.config_kP(0, RearLeftkP, 10);
		rearLeftTalon.config_kI(0, RearLeftkI, 10);
		rearLeftTalon.config_kD(0, RearLeftkD, 10);
		rearLeftTalon.config_kF(0, RearLeftkF, 10);
		
		frontLeftTalon.config_kP(0, FrontLeftkP, 10);
		frontLeftTalon.config_kI(0, FrontLeftkI, 10);
		frontLeftTalon.config_kD(0, FrontLeftkD, 10);
		frontLeftTalon.config_kF(0, FrontLeftkF, 10);
		
		chassis = new MecanumDrive(frontLeftTalon, rearLeftTalon,
								  frontRightTalon, rearRightTalon); //not really needed since we are doing speed-control

		chassis.setDeadband(.10);
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
		//chassis.driveCartesian(x, -y, rotationalRate, fieldOrientation);	
	}
	
	public void driveRobotOriented(double x, double y, double rotationalRate)
	{
		Vector2d input = new Vector2d(x, -y);
		
		double rearLeftSpeed, rearRightSpeed, frontLeftSpeed, frontRightSpeed, maxRightSpeed, maxLeftSpeed, maxSpeed;
		
		rearLeftSpeed = (-input.x +input.y + rotationalRate)*MOTOR_SETPOINT_PER_100MS;
		rearRightSpeed = (-input.x -input.y +rotationalRate)*MOTOR_SETPOINT_PER_100MS;
		frontLeftSpeed = (input.x +input.y + rotationalRate)*MOTOR_SETPOINT_PER_100MS;
		frontRightSpeed = (input.x -input.y +rotationalRate)*MOTOR_SETPOINT_PER_100MS;
		
		//maxLeftSpeed = java.lang.Math.max(rearLeftSpeed, rearRightSpeed);
		//maxRightSpeed = java.lang.Math.max(rearRightSpeed, frontRightSpeed);
		
		//maxSpeed = java.lang.Math.max(maxLeftSpeed, maxRightSpeed); 
		
		/*rearLeftSpeed = (rearLeftSpeed/maxLeftSpeed)*MOTOR_SETPOINT_PER_100MS;
		rearRightSpeed = (rearRightSpeed/maxRightSpeed)*MOTOR_SETPOINT_PER_100MS;
		
		frontLeftSpeed = (frontLeftSpeed/maxLeftSpeed)*MOTOR_SETPOINT_PER_100MS;
		frontRightSpeed = (frontRightSpeed/maxRightSpeed)*MOTOR_SETPOINT_PER_100MS;*/
		
		rearLeftTalon.set(ControlMode.Velocity, rearLeftSpeed);
		rearRightTalon.set(ControlMode.Velocity, rearRightSpeed);
		frontLeftTalon.set(ControlMode.Velocity, frontLeftSpeed);
		frontRightTalon.set(ControlMode.Velocity, frontRightSpeed);
		
	    m_safetyHelper.feed(); //"watchdog.feed()"
	}
	
	public void driveSingleMotorPower(int id)
	{
		if (id == REAR_LEFT_TALON_ID)
		{
			rearLeftTalon.set(Robot.oi.GetManipY());
		}
		if (id == REAR_RIGHT_TALON_ID)
		{
			rearRightTalon.set(-Robot.oi.GetManipY());
		}
		if (id == FRONT_LEFT_TALON_ID)
		{
			frontLeftTalon.set(Robot.oi.GetManipY());
		}
		if (id == FRONT_RIGHT_TALON_ID)
		{
			frontRightTalon.set(-Robot.oi.GetManipY());
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

		
    public void initDefaultCommand() 
    {
    	setDefaultCommand(new DriveJ(orientation));
    } 
}