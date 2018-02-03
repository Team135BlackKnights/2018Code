/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team135.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team135.robot.RobotMap.*;
import org.usfirst.frc.team135.robot.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI implements RobotMap
{
	private static OI instance;
	
	private Joystick LEFT, RIGHT, MANIP;  

	private JoystickButton 
		LIFT_DOWN, 
		LIFT_UP,
		DRIVE_REAR_LEFT, 
		DRIVE_REAR_RIGHT, 
		DRIVE_FRONT_RIGHT, 
		DRIVE_FRONT_LEFT,
		GRAB, 
		RELEASE, 
		RETRACT_MANDIBLES, 
		EXTEND_MANDIBLES,
		DRIVE_WHEELS_IN,
		DRIVE_WHEELS_OUT;
	
 
	public static OI getInstance() {
		if (instance == null) {
			instance = new OI();
		}
		return instance;
	}
	
	private OI()
	{
		LEFT = new Joystick(0);
		RIGHT = new Joystick(1);
		MANIP = new Joystick(2);
		
		LIFT_DOWN = new JoystickButton(MANIP, 4);
		LIFT_UP = new JoystickButton(MANIP, 6);
		/*
		DRIVE_REAR_RIGHT = new JoystickButton(MANIP, 4);
		DRIVE_REAR_LEFT = new JoystickButton(MANIP, 3);
		DRIVE_FRONT_LEFT = new JoystickButton(MANIP, 5);
		DRIVE_FRONT_RIGHT = new JoystickButton(MANIP, 6);
		*/
		DRIVE_WHEELS_IN = new JoystickButton(MANIP, 1);
		DRIVE_WHEELS_OUT = new JoystickButton(MANIP, 2);
		
		RELEASE = new JoystickButton(MANIP, 7);
		GRAB = new JoystickButton(MANIP, 8);
		
		RETRACT_MANDIBLES = new JoystickButton(MANIP, 9);
		EXTEND_MANDIBLES = new JoystickButton(MANIP, 10);
		
		assignButtons();
	}
	
	private double deadband(double input)
	{
		if(Math.abs(input) < .05)
		{
			return 0;
		}
		else
		{
			return input;
		}
	}
	
	public double GetLeftY()
	{
		return deadband(LEFT.getY());
	}
	
	public double GetLeftX()
	{
		return deadband(LEFT.getX());
	}
	
	public double GetLeftTwist()
	{
		return deadband(LEFT.getTwist());
	}
	public double GetRightY()
	{
		return deadband(-RIGHT.getY());
	}
	
	public double GetRightX()
	{
		return deadband(RIGHT.getX());
	}
	
	public double GetRightTwist()
	{
		return deadband(RIGHT.getTwist() / 2);
	}
	
	public double GetManipY()
	{
		return deadband(MANIP.getY());
	}
	
	public double GetManipX()
	{
		return deadband(MANIP.getX());
	}
	
	public double GetManipTwist()
	{
		return deadband(MANIP.getTwist());
	}
	
	private void assignButtons()
	{
		/*
		DRIVE_REAR_LEFT.whileHeld(new DriveMotor(DRIVETRAIN.REAR_LEFT_TALON_ID));
		DRIVE_REAR_RIGHT.whileHeld(new DriveMotor(DRIVETRAIN.REAR_RIGHT_TALON_ID));
		DRIVE_FRONT_LEFT.whileHeld(new DriveMotor(DRIVETRAIN.FRONT_LEFT_TALON_ID));
		DRIVE_FRONT_RIGHT.whileHeld(new DriveMotor(DRIVETRAIN.FRONT_RIGHT_TALON_ID));
		*/
		
		
		GRAB.whenPressed(new GrabMandibles());
		RELEASE.whenPressed(new ReleaseMandibles());
		RETRACT_MANDIBLES.whenPressed(new RetractMandibles());
		EXTEND_MANDIBLES.whenPressed(new ExtendMandibles());
		
		DRIVE_WHEELS_IN.whileHeld(new DriveMandibleWheels(true));
		DRIVE_WHEELS_OUT.whileHeld(new DriveMandibleWheels(false));
		
		LIFT_UP.whileHeld(new RunLift(true));
		LIFT_DOWN.whileHeld(new RunLift(false));
	}	
	
}
