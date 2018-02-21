package org.usfirst.frc.team135.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.RobotMap.*;
import org.usfirst.frc.team135.robot.commands.teleop.RunLift;

/**
 *
 */
public class Lift extends Subsystem implements RobotMap
{
	private static Lift instance;
	
	private double setpoint = 0.0;
	
	private TalonSRX liftMotor;
	
	private boolean isPositionInitialized = false;
	
	public boolean isMantaining;
	

	
	private Lift()
	{
		
		double
		kP = (Preferences.getInstance().getBoolean("Is Competition Bot?", true) ? COMPETITION.LIFT.kP : PRACTICE.LIFT.kP),
		kI = (Preferences.getInstance().getBoolean("Is Competition Bot?", true) ? COMPETITION.LIFT.kP : PRACTICE.LIFT.kP),
		kD = (Preferences.getInstance().getBoolean("Is Competition Bot?", true) ? COMPETITION.LIFT.kD : PRACTICE.LIFT.kD),
		kF = (Preferences.getInstance().getBoolean("Is Competition Bot?", true) ? COMPETITION.LIFT.kF : PRACTICE.LIFT.kF);
	
	int id = (Preferences.getInstance().getBoolean("Is Competition Bot?", true) ? COMPETITION.LIFT.LIFT_MOTOR_ID : PRACTICE.LIFT.LIFT_MOTOR_ID);
	
		liftMotor = new TalonSRX(id);
		liftMotor.setInverted(false);
		
		liftMotor.setSensorPhase(true);
		liftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		liftMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 10, 10);
		liftMotor.setSelectedSensorPosition(0, 0, 10);
		
		//Motors don't stop precisely where you want them to. Usually stop a bit later.
		liftMotor.configForwardSoftLimitThreshold(1580, 10);
		liftMotor.configForwardSoftLimitEnable(false, 10);
		
		liftMotor.configReverseSoftLimitThreshold(-50, 10);
		liftMotor.configReverseSoftLimitEnable(false, 10);
		
		liftMotor.configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_100Ms, 10);
		liftMotor.configVelocityMeasurementWindow(5, 10); //Might want to check this later
		
		
		liftMotor.config_kP(0, kP, 10);
		liftMotor.config_kI(0, kI, 10);
		liftMotor.config_kD(0, kD, 10);
		liftMotor.config_kF(0, kF, 10);
		liftMotor.configMotionCruiseVelocity(100, 10);
		liftMotor.configMotionAcceleration(500, 10);
		
		
		
	} 	
	
	public static Lift getInstance()
	{
		if (instance == null)
		{
			instance = new Lift();
		}
		
		return instance;
	}
	
	public void initPosition()
	{
		if (!isPositionInitialized)
		{
			Timer timer = new Timer();
			timer.start();
			do
			{
				//System.out.println(getEncoderVelocity());
				set(1.0);
				
			}while (timer.get() < 5);
			timer.stop();
			timer.reset();
			isPositionInitialized = true;
		}
		
	}
	
	public double getEncoderAcceleration()
	{
		double v1 = 0.0, v2 = 0.0;
		Timer timer = new Timer();
		v1 = getEncoderVelocity();
		timer.start();
		while (timer.get() < .2){}
		v2 = getEncoderVelocity();
		timer.stop();
		
		return ((v2 - v1) / timer.get());
	}
	
	public double getEncoderVelocity()
	{
		return (double)liftMotor.getSelectedSensorVelocity(0);
	}
	
	public double getEncoderPosition()
	{
		return (double)liftMotor.getSelectedSensorPosition(0);
	}
	
	public void set(double speed)
	{
		liftMotor.set(ControlMode.PercentOutput, speed);
	}
	
	public void stopMotor()
	{
		set(0);
	}
	
	public void setToPosition(double position)
	{	
		//liftMotor.set(ControlMode.MotionMagic, position);
		Timer timer = new Timer();
		
		timer.start();
		while(getEncoderPosition() < position && timer.get() < 2)
		{
			set(1.0);
		}
		
		timer.stop();
		timer.reset();
		
		setpoint = position;
	}
	
	public void mantainPosition()
	{
		liftMotor.set(ControlMode.Velocity, 0);
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(new RunLift());
    }
    
    
    
    public void periodic()
    {
    	SmartDashboard.putNumber("Lift Position", getEncoderPosition());
    	SmartDashboard.putNumber("Lift Setpoint", setpoint);
    	//SmartDashboard.putNumber("Lift Velocity", getEncoderVelocity());
    	//SmartDashboard.putNumber("Lift Acceleration", getEncoderAcceleration());
    	//System.out.println(getEncoderPosition());
    }
}

