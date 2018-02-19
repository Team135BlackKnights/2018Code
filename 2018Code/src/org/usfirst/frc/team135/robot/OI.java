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
import org.usfirst.frc.team135.robot.commands.teleop.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI implements RobotMap
{
	private static OI instance;
	
	private Joystick LEFT, RIGHT, MANIP;  

	private JoystickButton 
		//Unused
		DRIVE_REAR_LEFT, 
		DRIVE_REAR_RIGHT, 
		DRIVE_FRONT_RIGHT, 
		DRIVE_FRONT_LEFT,
		
		
		MANDIBLES_OPEN, 
		MANDIBLES_CLOSE, 
		MANDIBLES_UP, 
		MANDIBLES_DOWN,
		MANDIBLES_WHEELS_IN,
		MANDIBLES_WHEELS_OUT,
		DRIVE_HANG_FORWARD,
		DRIVE_HANG_BACKWARD,
		RELEASE_HANG;
	
 
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
		
		/*
		LIFT_DOWN = new JoystickButton(MANIP, 4);
		LIFT_UP = new JoystickButton(MANIP, 6);
		*/
		
		
		DRIVE_REAR_RIGHT = new JoystickButton(LEFT, 4);
		DRIVE_REAR_LEFT = new JoystickButton(LEFT, 3);
		DRIVE_FRONT_LEFT = new JoystickButton(LEFT, 5);
		DRIVE_FRONT_RIGHT = new JoystickButton(LEFT, 6);
		
		MANDIBLES_WHEELS_IN = new JoystickButton(MANIP, 1);
		MANDIBLES_WHEELS_OUT = new JoystickButton(MANIP, 2);
		
		MANDIBLES_OPEN = new JoystickButton(MANIP, 6);
		MANDIBLES_CLOSE = new JoystickButton(MANIP, 4);
		
		MANDIBLES_UP = new JoystickButton(MANIP, 5);
		MANDIBLES_DOWN = new JoystickButton(MANIP, 3);
		
		DRIVE_HANG_FORWARD = new JoystickButton(RIGHT, 1);
		DRIVE_HANG_BACKWARD = new JoystickButton(RIGHT, 2);
		
		RELEASE_HANG = new JoystickButton(RIGHT, 7);
		AssignButtons();
	}
	
	private double deadband(double input)
	{
		if(Math.abs(input) < .1)
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
		return deadband(LEFT.getTwist() / 2);
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
		return deadband(RIGHT.getTwist() / 3);
	}
	
	public double GetManipY()
	{
		return deadband(-MANIP.getY());
	}
	
	public double GetManipX()
	{
		return deadband(MANIP.getX());
	}
	
	public double GetManipTwist()
	{
		return deadband(MANIP.getTwist());
	}
	
	private void AssignButtons()
	{
		
		DRIVE_REAR_LEFT.whileHeld(new DriveMotor(COMPETITION.DRIVETRAIN.REAR_LEFT_TALON_ID));
		DRIVE_REAR_RIGHT.whileHeld(new DriveMotor(COMPETITION.DRIVETRAIN.REAR_RIGHT_TALON_ID));
		DRIVE_FRONT_LEFT.whileHeld(new DriveMotor(COMPETITION.DRIVETRAIN.FRONT_LEFT_TALON_ID));
		DRIVE_FRONT_RIGHT.whileHeld(new DriveMotor(COMPETITION.DRIVETRAIN.FRONT_RIGHT_TALON_ID));
		
		
		MANDIBLES_CLOSE.whenPressed(new GrabMandibles());
		MANDIBLES_OPEN.whenPressed(new ReleaseMandibles());
		MANDIBLES_UP.whenPressed(new RetractMandibles());
		MANDIBLES_DOWN.whenPressed(new ExtendMandibles());
		
		MANDIBLES_WHEELS_IN.whileHeld(new DriveMandibleWheels(true));
		MANDIBLES_WHEELS_OUT.whileHeld(new DriveMandibleWheels(false));
	
		DRIVE_HANG_FORWARD.whileHeld(new DriveHang(.75));
		DRIVE_HANG_BACKWARD.whileHeld(new DriveHang(-.75));
		
		RELEASE_HANG.whenPressed(new ToggleHangState());
	}	
	
}
