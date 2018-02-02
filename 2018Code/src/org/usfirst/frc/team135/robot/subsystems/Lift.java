package org.usfirst.frc.team135.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.RobotMap.*;

/**
 *
 */
public class Lift extends Subsystem implements RobotMap
{
	private static Lift instance;
	
	private TalonSRX liftMotor;
	
	private Lift()
	{
		liftMotor = new TalonSRX(LIFT.LIFT_MOTOR);
		liftMotor.setInverted(true);
		
		liftMotor.setSensorPhase(true);
		liftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		liftMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 10, 10);
		liftMotor.setSelectedSensorPosition(0, 0, 10);
		
		//Motors don't stop precisely where you want them to. Usually stop a bit later.
		liftMotor.configForwardSoftLimitThreshold(1480, 10);
		liftMotor.configForwardSoftLimitEnable(true, 10);
		
		liftMotor.configReverseSoftLimitThreshold(10, 10);
		liftMotor.configReverseSoftLimitEnable(true, 10);
		
		liftMotor.configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_100Ms, 10);
		liftMotor.configVelocityMeasurementWindow(5, 10); //Might want to check this later
		
	} 	
	
	public static Lift getInstance()
	{
		if (instance == null)
		{
			instance = new Lift();
		}
		
		return instance;
	}
	
	public double getEncoderPosition()
	{
		return (double)liftMotor.getSelectedSensorPosition(0);
	}
	
	public void set(double speed)
	{
		liftMotor.set(ControlMode.PercentOutput, speed*0.5);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void periodic()
    {
    	System.out.println(getEncoderPosition());
    }
}

