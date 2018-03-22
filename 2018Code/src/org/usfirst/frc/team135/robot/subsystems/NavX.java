package org.usfirst.frc.team135.robot.subsystems;

import org.usfirst.frc.team135.robot.Robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;

public class NavX extends Subsystem
{
	private static NavX instance;
	public double initAngle = 0.0;
	private AHRS ahrs;
	
	@Override
	protected void initDefaultCommand() {

		
	}
	
	private NavX()
	{
		ahrs = new AHRS(SerialPort.Port.kUSB1);
		ahrs.reset();
		initAngle = ahrs.getFusedHeading();
		//System.out.println(initAngle);
	}
	
	public static NavX getInstance()
	{
		if (instance == null)
		{
			instance = new NavX();
		}
		return instance;
	}
	
	public void reset()
	{
		ahrs.reset();
	}
	public double getFusedAngle()
	{
		return ((ahrs.getFusedHeading() + initAngle) % 360);
	}
	
	public void periodic()
	{
		
	}
	

}