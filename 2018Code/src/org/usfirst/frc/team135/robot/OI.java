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
		DRIVE_HANG,
		TOGGLE_COMPRESSOR,
		TOGGLE_HANG,
		THROW_CUBE_FORWARD,
		THROW_CUBE_BACKWARD,
		RESET_NAVX,
		RUN_HANG_TEST_MOTOR_FORWARD,
		RUN_HANG_TEST_MOTOR_BACKWARD;
	
 
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
		
		/*
		DRIVE_REAR_RIGHT = new JoystickButton(MANIP, 4);
		DRIVE_REAR_LEFT = new JoystickButton(MANIP, 3);
		DRIVE_FRONT_LEFT = new JoystickButton(MANIP, 5);
		DRIVE_FRONT_RIGHT = new JoystickButton(MANIP, 6);
		*/
		MANDIBLES_WHEELS_IN = new JoystickButton(MANIP, 1);
		MANDIBLES_WHEELS_OUT = new JoystickButton(MANIP, 2);
		
		MANDIBLES_OPEN = new JoystickButton(MANIP, 6);
		MANDIBLES_CLOSE = new JoystickButton(MANIP, 4);
		
		MANDIBLES_UP = new JoystickButton(MANIP, 5);
		MANDIBLES_DOWN = new JoystickButton(MANIP, 3);
		
		THROW_CUBE_FORWARD = new JoystickButton(MANIP, 8);
		THROW_CUBE_BACKWARD = new JoystickButton(MANIP, 7);
		
		DRIVE_HANG = new JoystickButton(RIGHT, 1);
		TOGGLE_COMPRESSOR = new JoystickButton(RIGHT, 4);
		TOGGLE_HANG = new JoystickButton(RIGHT, 6);
		
		RESET_NAVX = new JoystickButton(LEFT, 12);
		RUN_HANG_TEST_MOTOR_FORWARD = new JoystickButton(RIGHT, 10);
		RUN_HANG_TEST_MOTOR_BACKWARD = new JoystickButton(RIGHT, 9);
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
	
	public boolean GetNavXResetButton()
	{
		return LEFT.getRawButton(11);
	}
	
	private void AssignButtons()
	{
		/*
		DRIVE_REAR_LEFT.whileHeld(new DriveMotor(DRIVETRAIN.REAR_LEFT_TALON_ID));
		DRIVE_REAR_RIGHT.whileHeld(new DriveMotor(DRIVETRAIN.REAR_RIGHT_TALON_ID));
		DRIVE_FRONT_LEFT.whileHeld(new DriveMotor(DRIVETRAIN.FRONT_LEFT_TALON_ID));
		DRIVE_FRONT_RIGHT.whileHeld(new DriveMotor(DRIVETRAIN.FRONT_RIGHT_TALON_ID));
		*/
		
		MANDIBLES_CLOSE.whenPressed(new GrabMandibles());
		MANDIBLES_OPEN.whenPressed(new ReleaseMandibles());
		MANDIBLES_UP.whenPressed(new RetractMandibles());
		MANDIBLES_DOWN.whenPressed(new ExtendMandibles());
		
		THROW_CUBE_FORWARD.whenPressed(new ThrowCubeForward());
		THROW_CUBE_BACKWARD.whenPressed(new ThrowCubeBackwards());
		
		MANDIBLES_WHEELS_IN.whileHeld(new DriveMandibleWheels(true));
		MANDIBLES_WHEELS_OUT.whileHeld(new DriveMandibleWheels(false));
	
		DRIVE_HANG.whileHeld(new RunHang());
		
		TOGGLE_HANG.whenPressed(new ToggleHang());
		
		RESET_NAVX.whenPressed(new ResetNavX());
		
		RUN_HANG_TEST_MOTOR_FORWARD.whileHeld(new RunHangTestMotor(1.0));
		RUN_HANG_TEST_MOTOR_BACKWARD.whileHeld(new RunHangTestMotor(-1.0));
		
		//TOGGLE_COMPRESSOR.whenPressed(new ToggleCompressor());
	}	
	
}
