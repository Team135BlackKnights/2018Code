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

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;


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
	
	//private static final double MOTOR_SETPOINT_PER_100MS = (MOTOR_SETPOINT/60/10*ENCODER_QUAD_COUNT); 
															/*measures encoder ticks per 100ms
															60 seconds per min  
														    10 "100ms" per second
														    256 NU (native units) per revolution
														    for sp 1000 ~ 1706.666 NU/100ms 
														    for sp 400 ~ 170.666 NU/100 ms
														    max speed is 313 NU/100ms
															*/
	private double kP; 
	private double kI;
	private double kD;
	private double kF;

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
		
		orientation = Preferences.getInstance().getString("Orientation", "Robot");
	}
	
	public void ConfigureTalons(WPI_TalonSRX talon, int talon_id)
	{

		talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		talon.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 10, 10);
		talon.setSelectedSensorPosition(0, 0, 10);
		
		talon.configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_100Ms, 10);
		talon.configVelocityMeasurementWindow(64, 0);
		
		ConfigureEncoderDirection();
	
		kP = Preferences.getInstance().getDouble("P", 0);
		kI = Preferences.getInstance().getDouble("I", 0);
		kD = Preferences.getInstance().getDouble("D", 0);
		kF = Preferences.getInstance().getDouble("FeedForward", 0);
		
		talon.config_kP(0, kP, 10); //slot 0, value, timeoutMS
		talon.config_kI(0, kI, 10);
		talon.config_kD(0, kD, 10);
		talon.config_kF(0, kF, 10);
		
		InitializeDriveTrain();
	}
	
	public void ConfigureEncoderDirection()
	{
		rearLeftTalon.setSensorPhase(false);
		rearRightTalon.setSensorPhase(true);
		frontLeftTalon.setSensorPhase(false);
		frontRightTalon.setSensorPhase(true);
	}
	
	public double returnPValue()
	{
		return kP;
	}
	
	public double returnVelocity()
	{
		double value = frontRightTalon.getSelectedSensorVelocity(0);
		return value;
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
	
	public double getTalonVoltage(WPI_TalonSRX talon)
	{
		return talon.getMotorOutputVoltage();
	}

		
    public void initDefaultCommand() 
    {
    	setDefaultCommand(new DriveJ(orientation));
    }
   
}