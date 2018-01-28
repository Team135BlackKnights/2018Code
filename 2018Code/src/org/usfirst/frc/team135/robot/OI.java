/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team135.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team135.robot.RobotMap;

import org.usfirst.frc.team135.robot.commands.DriveMotor;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI implements RobotMap
{
	private static OI instance;
	
	//private Joystick[] joysticks = new Joystick[3];
	//private JoystickButton[][] joystickButtons = new JoystickButton[3][12];
	
	Joystick LEFT, RIGHT, MANIP; 
	
	JoystickButton 
		DRIVE_REAR_LEFT, 
		DRIVE_REAR_RIGHT, 
		DRIVE_FRONT_RIGHT, 
		DRIVE_FRONT_LEFT;

	
	public static OI getInstance() {
		if (instance == null) {
			instance = new OI();
		}
		return instance;
	}
	
	private OI()
	{
		RIGHT = new Joystick(1);
		LEFT = new Joystick(0);
		MANIP = new Joystick(2);
		
		ConfigureButtonMapping();
	}
	
	public double SetThreshold(double value)
	{
		if ((value >= -0.15) && (value <= 0.15))
		{
			return 0;
		}
		else
		{
			return value;
		}
	}
	
	public double GetManipY()
	{
		return SetThreshold(MANIP.getY());
	}
	public double GetRightY()
	{
		return SetThreshold(RIGHT.getY());
	}
	
	public double GetLeftX()
	{
		return SetThreshold(LEFT.getX());
	}
	
	public double GetTwist()
	{
		return SetThreshold(RIGHT.getTwist() * .5);
	}	
	
	public void ConfigureButtonMapping()
	{
		DRIVE_REAR_RIGHT = new JoystickButton(MANIP, 4);
		DRIVE_REAR_LEFT = new JoystickButton(MANIP, 3);
		DRIVE_FRONT_LEFT = new JoystickButton(MANIP, 5);
		DRIVE_FRONT_RIGHT = new JoystickButton(MANIP, 6);
		assignButtons();
		
	}
	
	public void assignButtons()
	{
		DRIVE_REAR_LEFT.whileHeld(new DriveMotor(REAR_LEFT_TALON_ID));
		DRIVE_REAR_RIGHT.whileHeld(new DriveMotor(REAR_RIGHT_TALON_ID));
		DRIVE_FRONT_LEFT.whileHeld(new DriveMotor(FRONT_LEFT_TALON_ID));
		DRIVE_FRONT_RIGHT.whileHeld(new DriveMotor(FRONT_RIGHT_TALON_ID));
	}
	
}
