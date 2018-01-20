package org.usfirst.frc.team135.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Gyro extends Subsystem {

	private static Gyro instance;
	
	//private double calibrationRate;
	
	private Timer timer;
	
	private ADXRS450_Gyro device;
	
	private Gyro()
	{
		device = new ADXRS450_Gyro();
		device.reset();
		device.calibrate();
		
		timer = new Timer();
		
		timer.start();
	}
	
	public static Gyro getInstance()
	{
		if (instance == null)
		{
			instance = new Gyro();
		}
		
		return instance;
	}
	
	
	public double getRawAngle()
	{
		return device.getAngle();
	}
	
	public double getRawRate()
	{
		return device.getRate();
	}
	public double getCorrectedAngle()
	{
		return getRawAngle();
	}
	
	public double getCorrectedRate()
	{
		return device.getRate();
	}

    public void initDefaultCommand() {
    	
    }
}