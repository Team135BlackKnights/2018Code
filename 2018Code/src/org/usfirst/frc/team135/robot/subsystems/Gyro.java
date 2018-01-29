package org.usfirst.frc.team135.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Gyro extends Subsystem {

	private static Gyro instance;
	
	//private double calibrationRate;
	
	private Timer timer;
	
	private ADXRS450_Gyro gyro;
	
	private Gyro()
	{
		gyro = new ADXRS450_Gyro();
		gyro.reset();
		gyro.calibrate();
		
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
	
	public void ZeroGyro()
	{
		gyro.reset();
	}
	
	public double getAngle()
	{
		return gyro.getAngle(); //degrees
	}
	
	public double getRate()
	{
		return gyro.getRate(); //degrees per second
	}

    public void initDefaultCommand() {
    	
    }
}