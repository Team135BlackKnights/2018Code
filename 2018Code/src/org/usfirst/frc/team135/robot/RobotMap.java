/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team135.robot;


public interface RobotMap 
{
	public class auto
	{
		public static String msg;
	}
	
	public class utilities
	{
		public static double clamp(double input, double low, double high)
		{
			return Math.max(0, Math.min(1, input));
		}
	}
	
	public class motors
	{
		public static final int
			FRONT_LEFT = 0,
			MID_LEFT = 1,
			REAR_LEFT = 2,
			FRONT_RIGHT = 3,
			MID_RIGHT = 4,
			REAR_RIGHT = 5;
	}
}
