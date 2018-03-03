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

import java.util.Arrays;
import java.util.Collections;

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
	private static final int ENCODER_TICK_COUNT = 64;
	private static final int ENCODER_QUAD_COUNT = (ENCODER_TICK_COUNT * 4);
	
	private static final double MOTOR_SETPOINT_PER_100MS = 288; //NU/100 ms MAX SPEED for slowest motor
	
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
	
	public int
	FR_ID = (Preferences.getInstance().getBoolean("Is Competition Bot?", true) ? COMPETITION.DRIVETRAIN.FRONT_RIGHT_TALON_ID : PRACTICE.DRIVETRAIN.FRONT_RIGHT_TALON_ID),
	FL_ID =(Preferences.getInstance().getBoolean("Is Competition Bot?", true) ? COMPETITION.DRIVETRAIN.FRONT_LEFT_TALON_ID : PRACTICE.DRIVETRAIN.FRONT_LEFT_TALON_ID),
	RR_ID = (Preferences.getInstance().getBoolean("Is Competition Bot?", true) ? COMPETITION.DRIVETRAIN.REAR_RIGHT_TALON_ID : PRACTICE.DRIVETRAIN.REAR_RIGHT_TALON_ID),
	RL_ID = (Preferences.getInstance().getBoolean("Is Competition Bot?", true) ? COMPETITION.DRIVETRAIN.REAR_LEFT_TALON_ID : PRACTICE.DRIVETRAIN.REAR_LEFT_TALON_ID);

	
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
		frontRightTalon = new WPI_TalonSRX(FR_ID);
		frontLeftTalon = new WPI_TalonSRX(FL_ID);
		rearRightTalon = new WPI_TalonSRX(RR_ID);
		rearLeftTalon = new WPI_TalonSRX(RL_ID);		

		//Configure the talons.
		ConfigureTalons(frontRightTalon, FR_ID);
		ConfigureTalons(frontLeftTalon, FL_ID);
		ConfigureTalons(rearRightTalon, RR_ID);
		ConfigureTalons(rearLeftTalon, RL_ID);
		
		//Configure the orientation helper and it's output storage helper.
		buffer = new PIDOut();
		navx = new NavX_wrapper(Robot.navx);
		
		//Configure orientation helper.
		if (Preferences.getInstance().getBoolean("Is Competition Bot?", true))
		{
			orientationHelper = new PIDController(.06, .0005, .5, navx, buffer);
		}
		else
		{
			orientationHelper = new PIDController(.003, 0, .02, navx, buffer);
		}
		
		orientationHelper.setInputRange(0, 360);
		orientationHelper.setOutputRange(-.2, .2);
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
		//talon.configVoltageCompSaturation(12.0, 10);
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
		String competition;
		if (Preferences.getInstance().getBoolean("Is Competition Bot?", true))
		{
			competition = "Comp_";
		}
		else
		{
			competition = "";
		}
		RearRightkP = Preferences.getInstance().getDouble(competition + "RearRightP", 0); //get PID constants from Dashboard
		RearRightkI = Preferences.getInstance().getDouble(competition + "RearRightI", 0);
		RearRightkD = Preferences.getInstance().getDouble(competition + "RearRightD", 0);
		RearRightkF = Preferences.getInstance().getDouble(competition + "RearRightF", 0);
		
		RearLeftkP = Preferences.getInstance().getDouble(competition + "RearLeftP", 0);
		RearLeftkI = Preferences.getInstance().getDouble(competition + "RearLeftI", 0);
		RearLeftkD = Preferences.getInstance().getDouble(competition + "RearLeftD", 0);
		RearLeftkF = Preferences.getInstance().getDouble(competition + "RearLeftF", 0);
		
		FrontRightkP = Preferences.getInstance().getDouble(competition + "FrontRightP", 0);
		FrontRightkI = Preferences.getInstance().getDouble(competition + "FrontRightI", 0);
		FrontRightkD = Preferences.getInstance().getDouble(competition + "FrontRightD", 0);
		FrontRightkF = Preferences.getInstance().getDouble(competition + "FrontRightF", 0);
		
		FrontLeftkP = Preferences.getInstance().getDouble(competition + "FrontLeftP", 0);
		FrontLeftkI = Preferences.getInstance().getDouble(competition + "FrontLeftI", 0);
		FrontLeftkD = Preferences.getInstance().getDouble(competition + "FrontLeftD", 0);
		FrontLeftkF = Preferences.getInstance().getDouble(competition + "FrontLeftF", 0);
		
		OrientationHelper_kP = Preferences.getInstance().getDouble(competition + "OrientationHelper_kP", 0);
		OrientationHelper_kI = Preferences.getInstance().getDouble(competition + "OrientationHelper_kI", 0);
		OrientationHelper_kD = Preferences.getInstance().getDouble(competition + "OrientationHelper_kD", 0);

		
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
		if (talon.getDeviceID() == FR_ID || talon.getDeviceID() == RR_ID)
		{
			position *= -1;
		}
		return (position);
	}
	
	public double getEncoderSpeed(WPI_TalonSRX talon)
	{
		double velocity = talon.getSelectedSensorVelocity(0);
		if (talon.getDeviceID() == FR_ID || talon.getDeviceID() == RR_ID)
		{
			velocity *= -1;
		}
		return (velocity);
	}
	
	public double getEncoderAcceleration(WPI_TalonSRX talon)
	{
		double v1, v2;
		
		Timer timer = new Timer();
		
		timer.start();
		
		v1 = getEncoderSpeed(talon);
		while (timer.get() < .01)
		{
			
		}
		v2 = getEncoderSpeed(talon);
		timer.stop();
		
		return ((v2 - v1) / timer.get());		
	}

	public double getEncoderJerk(WPI_TalonSRX talon)
	{
		double a1, a2;
		
		Timer timer = new Timer();
		
		timer.start();
		
		a1 = getEncoderAcceleration(talon);
		while (timer.get() < .01)
		{
			
		}
		a2 = getEncoderAcceleration(talon);
		timer.stop();
		
		return (a2 - a1) / timer.get();		
	}
	
	public double getEncoderSetpoint(WPI_TalonSRX talon)
	{
		if (talon.getDeviceID() == FL_ID)
		{
			return FrontLeftSetpoint;
		}
		else if (talon.getDeviceID() == RL_ID)
		{
			return RearLeftSetpoint;
		}
		else if (talon.getDeviceID() == FR_ID)
		{
			return FrontRightSetpoint;
		}
		else if (talon.getDeviceID() == RR_ID)
		{
			return RearRightSetpoint;
		}
		else
		{
			return 0;
		}
		
	}
	
	public void driveTank(double left, double right)
	{
		double
			x = 0,
			y = (left + right) / 2,
			rotationZ = (left - right) / 2;
		
		this.driveCartesian(x, y, rotationZ, 0);
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
		
		double rearLeftSpeed, rearRightSpeed, frontLeftSpeed, frontRightSpeed, maxRightSpeed, maxLeftSpeed, maxSpeed;
		
		if (Preferences.getInstance().getBoolean("Enable Orientation Helper", false))
		{
			
			if (Math.abs(rotationalRate) == 0 && !orientationHelper.isEnabled()) {
				// PID controller will bias motors accordingly
				orientationHelper.enable();
				orientationHelper.setSetpoint(Robot.navx.getFusedAngle()); // See about using a navx in the future
			} 
			else if (Math.abs(rotationalRate) != 0 && orientationHelper.isEnabled()) {
				orientationHelper.disable();
			}
			
		}
		
		//Left get's dialed back on positive error and right get's dialed up
		
		
		rearLeftSpeed = (input.x + input.y + rotationalRate) + buffer.output;
		rearRightSpeed = (input.x - input.y + rotationalRate) + buffer.output;
		frontLeftSpeed = (-input.x + input.y + rotationalRate) + buffer.output;
		frontRightSpeed = (-input.x -input.y + rotationalRate) + buffer.output;
				
		double 
		_FL = Math.abs(frontLeftSpeed),
		_BL = Math.abs(rearLeftSpeed),
		_FR = Math.abs(frontRightSpeed),
		_BR = Math.abs(rearRightSpeed);
	
		//Use java collections to find the max rather than a loop. 
		//Just pass a temporary arraylist for it to use
		double maxMagnitude = Collections.max(Arrays.asList(new Double[] {_FL, _BL, _FR, _BR}));
		
		//In a normal situation speeds are between -1.0 and 1.0
		if (maxMagnitude > 1.0) 
		{
			frontLeftSpeed /= maxMagnitude;
			rearLeftSpeed /= maxMagnitude;
			frontRightSpeed /= maxMagnitude;
			rearRightSpeed /= maxMagnitude;
		}
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
		if (id == RL_ID)
		{
			rearLeftTalon.set(Robot.oi.GetManipY());
		}
		if (id == RR_ID)
		{
			rearRightTalon.set(-Robot.oi.GetManipY());
		}
		if (id == FL_ID)
		{
			frontLeftTalon.set(Robot.oi.GetManipY());
		}
		if (id == FR_ID)
		{
			frontRightTalon.set(-Robot.oi.GetManipY());
		}	
	}
	
	public void driveSingleMotorVelocity(int id)
	{
		if (id == RL_ID)
		{
			rearLeftTalon.set(ControlMode.Velocity, Robot.oi.GetManipY()*MOTOR_SETPOINT_PER_100MS);
			//full throttle joystick so it returns value of 1 * setpoint = setpoint
		}
		if (id == RR_ID)
		{
			rearRightTalon.set(ControlMode.Velocity, Robot.oi.GetManipY()*MOTOR_SETPOINT_PER_100MS);
		}
		if (id == FL_ID)
		{
			frontLeftTalon.set(ControlMode.Velocity, Robot.oi.GetManipY()*MOTOR_SETPOINT_PER_100MS);
		}
		if (id == FR_ID)
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
		while((Math.abs(getEncoderSpeed(frontLeftTalon)) > 0 || orientationHelper.getError() > .2) && timer.get() < 1)
		{
			rearLeftTalon.set(ControlMode.Velocity, 0 + buffer.output);
			rearRightTalon.set(ControlMode.Velocity, 0 + buffer.output);
			frontLeftTalon.set(ControlMode.Velocity, 0 + buffer.output);
			frontRightTalon.set(ControlMode.Velocity, 0 + buffer.output);
		}
	
		orientationHelper.disable();
		
	}
	
		
    public void initDefaultCommand() 
    {
    	setDefaultCommand(new DriveJ(isFieldOriented));
    } 
}