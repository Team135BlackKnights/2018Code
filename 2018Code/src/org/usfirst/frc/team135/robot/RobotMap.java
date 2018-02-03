/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team135.robot;

import org.usfirst.frc.team135.robot.util.PID_Manager;
import org.usfirst.frc.team135.robot.util.Reversal_Manager;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */

public interface RobotMap {

	public static final int autoLine = 200;
	public static final int autoLineMid = 100;
	public static final int autoScale = 325;
	public static final int autoSwitch = 165;
	
	public static final int autoSwitchSide = 45;
	public static final int autoScaleSide = 33;
	
	public static final int midLeftSwitchStraight = 18; //real number
	public static final int midRightSwitchStraight = 18; //real number plz
	
	public static final int midLeftSwitch = 132; //need real number plz
	public static final int midRightSwitch = 132; //ditto here
	
	public static final double rightSwitchAngle = 65.556; //need thingy
	public static final double leftSwitchAngle = 65.556; //need other thingy
	
	public interface JOY
	{
		public static final int 
			LEFT = 0, 
			RIGHT = 1, 
			MANIP = 2;
	}
	
	public interface BUTTON
	{
		public static final int 
			UNLOCK_ROTATION = 0;
	}
	
	public interface MOTORS
	{
		public static final double MAX_VELOCITY = 200; // Encoder ticks / decisecond

		public static final double WHEEL_RADIUS = 3.0; // Inches
		
		
		public enum NUMBER
		{
			//Name(CAN_ID)
			FL(0),
			BL(1),
			FR(2),
			BR(3);
			
			private final int CAN_ID;
			
			NUMBER(int CAN_ID)
			{
				this.CAN_ID = CAN_ID;			
			}
			
			public int getCAN_ID()
			{
				return CAN_ID;
			}
		
		}
	
		public interface REVERSED
		{
			//ReversalManager(motorReversed, sensorReversed)
			public static final Reversal_Manager 
				FL = new Reversal_Manager(false, false),
				BL = new Reversal_Manager(false, false),
				FR = new Reversal_Manager(true, false),
				BR = new Reversal_Manager(false, true);
		}
		

		public interface PID
		{
			/*
			 * When the wheels were tested off the ground, I found that
			 * kP = 1.1, kI = .19, and kD = 13  was fairly ideal.
			 */
			
			//kI = kP / 100
			//kD = kP * 10
			public static final PID_Manager 
				FL = new PID_Manager(.85, 0.0085, 8.5),
				BL = new PID_Manager(.85, 0.0085, 8.5),
				FR = new PID_Manager(.85, 0.0085, 8.5),
				BR = new PID_Manager(.85, 0.0085, 8.5);
			
			/*
			public static final PID_Manager 
				FL = new PID_Manager(4, 0, 0),
				BL = new PID_Manager(4, 0, 0),
				FR = new PID_Manager(4, 0, 0),
				BR = new PID_Manager(4, 0, 0);
		*/
		}
	
	}
	
}

