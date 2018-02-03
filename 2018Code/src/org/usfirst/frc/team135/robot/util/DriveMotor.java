package org.usfirst.frc.team135.robot.util;

import org.usfirst.frc.team135.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.MotorSafetyHelper;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveMotor implements RobotMap
{
	
	private WPI_TalonSRX motor;
	
	private double setVelocity = 0.0;
	private double setPosition = 0.0;
	private double setPower = 0.0;
	
	private final double VELOCITY_BUF = 0.0;
	
	private final int SLOT_IDX = 0;
	private final int PID_IDX = 0;

	private boolean setSlowing = false;
	
	private MotorSafetyHelper safetyHelper;
	
	private boolean PIDState = true; //true is enabled
	
	public double 
		kP = 0.0, 
		kI = 0.0, 
		kD = 0.0,
		kF = (256 * 4) / MOTORS.MAX_VELOCITY;  //to tweak kF if we want.
	
	
	public DriveMotor(int CAN_ID, PID_Manager pid, Reversal_Manager rev)
	{
		motor = new WPI_TalonSRX(CAN_ID);
		
		motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		motor.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 10, 10);
		motor.setSelectedSensorPosition(PID_IDX, 0, 10);
		
		motor.configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_100Ms, 10);
		motor.configVelocityMeasurementWindow(5, 10); //Might want to check this later
		
		motor.setInverted(rev.isMotorReversed);
		motor.setSensorPhase(rev.isSensorReversed);
		
		safetyHelper = new MotorSafetyHelper(motor);
		
		this.kP = pid.kP;
		this.kI = pid.kI;
		this.kD = pid.kD;
		
		reconfigurePID();
		
	}
	
	public void invertMotor(boolean motorReversed)
	{
		motor.setInverted(motorReversed);
	}
	
	public void invertSensor(boolean sensorReversed)
	{
		motor.setSensorPhase(sensorReversed);
	}
	
	public void putData(boolean isForward)
	{
		int multiplier = (isForward) ? 1 : -1;
		SmartDashboard.putNumber("Motor " + motor.getDescription() + " Setpoint", Math.abs(setVelocity));
		SmartDashboard.putNumber("Wheel Speed " + motor.getDescription(), Math.abs(getVelocity()));
		SmartDashboard.putNumber("kP " + motor.getDescription(), kP);
		SmartDashboard.putNumber("kI " + motor.getDescription(), kI);
		SmartDashboard.putNumber("kD " + motor.getDescription(), kD);
		SmartDashboard.putNumber("kF " + motor.getDescription(), kF);
	}
	
	public void getData()
	{
		SmartDashboard.getNumber("kP " + motor.getDescription(), kP);
		SmartDashboard.getNumber("kI " + motor.getDescription(), kI);
		SmartDashboard.getNumber("kD " + motor.getDescription(), kD);
		SmartDashboard.getNumber("kF " + motor.getDescription(), kF);
	}
	public void reconfigurePID()
	{	
		motor.config_kP(SLOT_IDX, kP, 10);
		motor.config_kI(SLOT_IDX, kI, 10);
		motor.config_kD(SLOT_IDX, kD, 10);
		motor.config_kF(SLOT_IDX, kF, 10);
	}
	
	public void setPower(double power)
	{
		setPower = power;
		motor.set(power);
	}
	
	public void setVelocity(double percent_velocity)
	{
		setVelocity = percent_velocity * MOTORS.MAX_VELOCITY;

		motor.set(ControlMode.Velocity, setVelocity);
		
		safetyHelper.feed();
	}
	
	public void setPIDEnabled(boolean enabled)
	{
		if (!enabled && PIDState == true)
		{
			motor.config_kP(SLOT_IDX, 0.0, 10);
			motor.config_kI(SLOT_IDX, 0.0, 10);
			motor.config_kD(SLOT_IDX, 0.0, 10);
		}
		else if (enabled && PIDState == false)
		{
			reconfigurePID();
		}
	}
	
	public double getVelocity()
	{
		return (double)motor.getSelectedSensorVelocity(PID_IDX);
	}

	
	public double getPosition()
	{
		return (double)motor.getSelectedSensorVelocity(PID_IDX);
	}
	
	public int getCAN_ID()
	{
		return motor.getDeviceID();
	}
	

}
