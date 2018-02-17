package org.usfirst.frc.team135.robot.subsystems;

import org.usfirst.frc.team135.robot.util.NavX_wrapper;
import org.usfirst.frc.team135.robot.util.PIDOut;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.*;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.commands.teleop.*;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import edu.wpi.first.wpilibj.drive.*;


import edu.wpi.first.wpilibj.MotorSafetyHelper;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DriverStation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
/**
 *
 */
public class DriveTrain extends Subsystem implements RobotMap{

	private static DriveTrain instance;
	public WPI_TalonSRX frontRightTalon, frontLeftTalon, rearRightTalon, rearLeftTalon;
	private MecanumDrive chassis; 
	
	public ADXRS450_Gyro gyro;
	private NavX_wrapper navx;
	private static final int angleSetPoint = 0; //Was thinking about using this but didn't.
	
	//Leftover stuff that we don't use.
	private static final int ENCODER_TICK_COUNT = 256;
	private static final int ENCODER_QUAD_COUNT = (ENCODER_TICK_COUNT * 4);
	
	private static final double MOTOR_SETPOINT_PER_100MS = 280; //NU/100 ms MAX SPEED for slowest motor
	
	private MotorSafetyHelper m_safetyHelper = new MotorSafetyHelper(chassis); //watchdog
	
	private PIDController orientationHelper; //Orientation helper SHOULD helper you go straight
											//But it doesn't work right now
	
	private PIDOut buffer; //Stores the orientation helper's motor bias
	
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
	
	private double
		OrientationHelper_kP,
		OrientationHelper_kI,
		OrientationHelper_kD;

	private double
		FrontLeftSetpoint = 0.0,
		RearLeftSetpoint = 0.0,
		FrontRightSetpoint = 0.0,
		RearRightSetpoint = 0.0;
	
	private boolean isFieldOriented = false;
			
	private DriveTrain()
	{/*
		gyro = new ADXRS450_Gyro();
		gyro.reset();
		gyro.calibrate();
	*/	
		//Instantiate each of our talons
		frontRightTalon = new WPI_TalonSRX(DRIVETRAIN.FRONT_RIGHT_TALON_ID);
		frontLeftTalon = new WPI_TalonSRX(DRIVETRAIN.FRONT_LEFT_TALON_ID);
		rearRightTalon = new WPI_TalonSRX(DRIVETRAIN.REAR_RIGHT_TALON_ID);
		rearLeftTalon = new WPI_TalonSRX(DRIVETRAIN.REAR_LEFT_TALON_ID);		

		//Configure the talons.
		ConfigureTalons(frontRightTalon, DRIVETRAIN.FRONT_RIGHT_TALON_ID);
		ConfigureTalons(frontLeftTalon, DRIVETRAIN.FRONT_LEFT_TALON_ID);
		ConfigureTalons(rearRightTalon, DRIVETRAIN.REAR_RIGHT_TALON_ID);
		ConfigureTalons(rearLeftTalon, DRIVETRAIN.REAR_LEFT_TALON_ID);
		
		//Configure the orientation helper and it's output storage helper.
		buffer = new PIDOut();
		navx = new NavX_wrapper(Robot.navx);
		
		//Configure orientation helper.
		orientationHelper = new PIDController(.01, 0.0001, .1, navx, buffer);
		orientationHelper.setInputRange(0, 360);
		orientationHelper.setOutputRange(-.1, .1);
		orientationHelper.setAbsoluteTolerance(.2);
		orientationHelper.setContinuous();
		
		//Get PIDF constants.
		InitializeDriveTrain();
	
		isFieldOriented = Preferences.getInstance().getBoolean("Is Field Oriented?", false);
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
		//talon.configVoltageCompSaturation(10, 10);
		//talon.enableVoltageCompensation(true);
		
		//InitializeDriveTrain();
	}
	
	
	public void ConfigureEncoderDirection()
	{
		rearLeftTalon.setSensorPhase(false); //true
		rearRightTalon.setSensorPhase(false); 
		frontLeftTalon.setSensorPhase(false);
		frontRightTalon.setSensorPhase(false); //false
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
		
		OrientationHelper_kP = Preferences.getInstance().getDouble("OrientationHelper_kP", 0);
		OrientationHelper_kI = Preferences.getInstance().getDouble("OrientationHelper_kI", 0);
		OrientationHelper_kD = Preferences.getInstance().getDouble("OrientationHelper_kD", 0);
		
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
		double position = talon.getSelectedSensorPosition(0);
		if (talon.getDeviceID() == DRIVETRAIN.FRONT_RIGHT_TALON_ID || talon.getDeviceID() == DRIVETRAIN.REAR_RIGHT_TALON_ID)
		{
			position *= -1;
		}
		return (position);
	}
	
	public double getEncoderSpeed(WPI_TalonSRX talon)
	{
		double velocity = talon.getSelectedSensorVelocity(0);
		if (talon.getDeviceID() == DRIVETRAIN.FRONT_RIGHT_TALON_ID || talon.getDeviceID() == DRIVETRAIN.REAR_RIGHT_TALON_ID)
		{
			velocity *= -1;
		}
		return (velocity);
	}
	
	public double getRepresenativeEncoderSpeed()
	{
		return ((frontRightTalon.getSelectedSensorVelocity(0) + rearLeftTalon.getSelectedSensorVelocity(0)) / 2);
	}
	
	public double getEncoderSetpoint(WPI_TalonSRX talon)
	{
		if (talon.getDeviceID() == DRIVETRAIN.FRONT_LEFT_TALON_ID)
		{
			return FrontLeftSetpoint;
		}
		else if (talon.getDeviceID() == DRIVETRAIN.REAR_LEFT_TALON_ID)
		{
			return RearLeftSetpoint;
		}
		else if (talon.getDeviceID() == DRIVETRAIN.FRONT_RIGHT_TALON_ID)
		{
			return FrontRightSetpoint;
		}
		else if (talon.getDeviceID() == DRIVETRAIN.REAR_RIGHT_TALON_ID)
		{
			return RearRightSetpoint;
		}
		else
		{
			return 0;
		}
		
	}
	
	public void driveCartesian(double x, double y, double rotationalRate)
	{
		driveCartesian(x, y, rotationalRate, Robot.navx.getFusedAngle());
	}
	
	public void driveCartesian(double x, double y, double rotationalRate, double orientation)
	{
		
		Vector2d input = new Vector2d(x, y);
		
		input.rotate(-orientation);
		
		//System.out.println(orientation);
		
		Double rearLeftSpeed, rearRightSpeed, frontLeftSpeed, frontRightSpeed, maxRightSpeed, maxLeftSpeed, maxSpeed;
		

		if (Preferences.getInstance().getBoolean("Enable Orientation Helper", false) && !DriverStation.getInstance().isAutonomous())
		{
			
			if (Math.abs(rotationalRate) == 0 && !orientationHelper.isEnabled()) {
				// PID controller will bias motors accordingly
				orientationHelper.enable();
				orientationHelper.setSetpoint(Robot.navx.getFusedAngle()); // See about using a navx in the future
			} 
			else if (Math.abs(rotationalRate) != 0 && orientationHelper.isEnabled()) {
				orientationHelper.disable();
				buffer.output = 0;
			}
			
		}
		
		//Left get's dialed back on positive error and right get's dialed up
		
		
		rearLeftSpeed = (input.x + input.y + rotationalRate) + buffer.output;
		rearRightSpeed = (input.x - input.y + rotationalRate) + buffer.output;
		frontLeftSpeed = (-input.x + input.y + rotationalRate) + buffer.output;
		frontRightSpeed = (-input.x -input.y + rotationalRate) + buffer.output;
				
		normalize(frontLeftSpeed, rearRightSpeed, frontRightSpeed, rearRightSpeed);
		
		/*rearLeftTalon.set(ControlMode.Velocity, rearLeftSpeed);
		rearRightTalon.set(ControlMode.Velocity, rearRightSpeed);
		frontLeftTalon.set(ControlMode.Velocity, frontLeftSpeed);
		frontRightTalon.set(ControlMode.Velocity, frontRightSpeed);*/
	
		RearLeftSetpoint = rearLeftSpeed;
		RearRightSetpoint = rearRightSpeed;
		FrontLeftSetpoint = frontLeftSpeed;
		FrontRightSetpoint = frontRightSpeed;
		
		rearLeftTalon.set(ControlMode.Velocity, rearLeftSpeed * MOTOR_SETPOINT_PER_100MS);
		rearRightTalon.set(ControlMode.Velocity, rearRightSpeed * MOTOR_SETPOINT_PER_100MS);
		frontLeftTalon.set(ControlMode.Velocity, frontLeftSpeed * MOTOR_SETPOINT_PER_100MS);
		frontRightTalon.set(ControlMode.Velocity, frontRightSpeed * MOTOR_SETPOINT_PER_100MS);
		
	    m_safetyHelper.feed(); //"watchdog.feed()"
	}
	
	public void driveSingleMotorPower(int id)
	{
		if (id == DRIVETRAIN.REAR_LEFT_TALON_ID)
		{
			rearLeftTalon.set(Robot.oi.GetManipY());
		}
		if (id == DRIVETRAIN.REAR_RIGHT_TALON_ID)
		{
			rearRightTalon.set(-Robot.oi.GetManipY());
		}
		if (id == DRIVETRAIN.FRONT_LEFT_TALON_ID)
		{
			frontLeftTalon.set(Robot.oi.GetManipY());
		}
		if (id == DRIVETRAIN.FRONT_RIGHT_TALON_ID)
		{
			frontRightTalon.set(-Robot.oi.GetManipY());
		}	
	}
	
	public void driveSingleMotorVelocity(int id)
	{
		if (id == DRIVETRAIN.REAR_LEFT_TALON_ID)
		{
			rearLeftTalon.set(ControlMode.Velocity, Robot.oi.GetManipY()*MOTOR_SETPOINT_PER_100MS);
			//full throttle joystick so it returns value of 1 * setpoint = setpoint
		}
		if (id == DRIVETRAIN.REAR_RIGHT_TALON_ID)
		{
			rearRightTalon.set(ControlMode.Velocity, Robot.oi.GetManipY()*MOTOR_SETPOINT_PER_100MS);
		}
		if (id == DRIVETRAIN.FRONT_LEFT_TALON_ID)
		{
			frontLeftTalon.set(ControlMode.Velocity, Robot.oi.GetManipY()*MOTOR_SETPOINT_PER_100MS);
		}
		if (id == DRIVETRAIN.FRONT_RIGHT_TALON_ID)
		{
			frontRightTalon.set(ControlMode.Velocity, Robot.oi.GetManipY()*MOTOR_SETPOINT_PER_100MS);
		}
	}
	
	public void stopMotors()
	{
		orientationHelper.setSetpoint(Robot.navx.getFusedAngle());
		orientationHelper.enable();
		Timer timer = new Timer();
		timer.start();
		while((Math.abs(getRepresenativeEncoderSpeed()) > 0 || orientationHelper.getError() > .2) && timer.get() < 1)
		{
			rearLeftTalon.set(ControlMode.Velocity, 0 + buffer.output);
			rearRightTalon.set(ControlMode.Velocity, 0 + buffer.output);
			frontLeftTalon.set(ControlMode.Velocity, 0 + buffer.output);
			frontRightTalon.set(ControlMode.Velocity, 0 + buffer.output);
		}

		orientationHelper.disable();
		
	}
	
	private void normalize(Double FL, Double BL, Double FR, Double BR) 
	{
		double 
		_FL = Math.abs(FL),
		_BL = Math.abs(BL),
		_FR = Math.abs(FR),
		_BR = Math.abs(BR);
	
		//Use java collections to find the max rather than a loop. 
		//Just pass a temporary arraylist for it to use
		double maxMagnitude = Collections.max(Arrays.asList(new Double[] {_FL, _BL, _FR, _BR}));
		
		//In a normal situation speeds are between -1.0 and 1.0
		if (maxMagnitude > 1.0) 
		{
			FL /= maxMagnitude;
			BL /= maxMagnitude;
			FR /= maxMagnitude;
			BR /= maxMagnitude;
		}
	}
	
		
    public void initDefaultCommand() 
    {
    	setDefaultCommand(new DriveJ(isFieldOriented));
    } 
}