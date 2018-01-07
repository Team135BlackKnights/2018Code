package org.usfirst.frc.team135.robot.subsystems;

import org.usfirst.frc.team135.robot.Commons;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Gyro extends Subsystem implements Commons {
	
	private static Gyro instance;
	
	private Timer timer;
	
	public ADXRS450_Gyro gyro;
	
	public static Gyro getInstance()
	{
		if (instance == null)
		{
			instance = new Gyro();
		}
		
		return instance;
	}
	
	public Gyro()
	{
		gyro = new ADXRS450_Gyro(); //SPI Port 0
		timer = new Timer();
		
		timer.start();
		gyro.reset();
		gyro.calibrate();
		
		this.addChild(gyro);
		SmartDashboard.putData("Gyro", this);
	}
	
	public ADXRS450_Gyro getPIDSource()
	{
		return gyro;
	}
	
	public double getRawAngle()
	{
		return gyro.getAngle();
	}
	
	public double getRawRate()
	{
		return gyro.getRate();
	}
	
	public double getCorrectedAngle()
	{
		return gyro.getAngle();
	}
	
	public double getCorrectedRate()
	{
		return gyro.getRate();
	}
	
	private double getDriftRate()
	{
		//Going to be some statistical regression function of time
	}
	
	private double getTotalDrift()
	{
		//Integral of the drift rate gives us the drift.
	}

    public void initDefaultCommand() {
       
    }
}

