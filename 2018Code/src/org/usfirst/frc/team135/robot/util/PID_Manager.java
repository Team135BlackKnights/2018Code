package org.usfirst.frc.team135.robot.util;

public class PID_Manager 
{
	public double 
		kP = 0.0, 
		kI = 0.0, 
		kD = 0.0;
	
	public PID_Manager(double kP, double kI, double kD)
	{
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
	}                                                                                                                                    
}
