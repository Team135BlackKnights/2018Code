package org.usfirst.frc.team135.robot.util;

public class Reversal_Manager 
{
	public boolean 
		isMotorReversed = false,
		isSensorReversed = false;
	
	public Reversal_Manager(boolean motorReversed, boolean sensorReversed)
	{
		isMotorReversed = motorReversed;
		isSensorReversed = sensorReversed;
	}

}
