package org.usfirst.frc.team135.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

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
	
	private double setPoint = 0.0;
	
	private TalonSRX liftMotor;
	
	private Lift()
	{
		Timer timer = new Timer();
		
		liftMotor = new TalonSRX(LIFT.LIFT_MOTOR_ID);
		liftMotor.setInverted(true);
		
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
		
		liftMotor.config_kP(0, 1, 10);
		liftMotor.config_kI(0, .01, 10);
		liftMotor.config_kD(0, 10, 10);
		liftMotor.config_kF(0, 0, 10);
		/*
		timer.start();
		while(timer.get() < 1 && Math.abs(getEncoderVelocity()) == 0 )
		{
			set(1.0);
			setToPosition(LIFT.LOW_POSITION);
		}
		*/
	} 	
	
	public static Lift getInstance()
	{
		if (instance == null)
		{
			instance = new Lift();
		}
		
		return instance;
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
		liftMotor.set(ControlMode.MotionMagic, position);
		setPoint = position;
	}
    public void initDefaultCommand() {
    	setDefaultCommand(new RunLift());
    }
    
    public void periodic()
    {
    	SmartDashboard.putNumber("Lift Position", getEncoderPosition());
    	SmartDashboard.putNumber("Lift Setpoint", setPoint);
    	//System.out.println(getEncoderPosition());
    }
}

