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
	
private JoystickButton DRIVE_HANG_UP, DRIVE_HANG_DOWN, DEPLOY_HANG;
	
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
		
		DRIVE_HANG_DOWN = new JoystickButton(RIGHT, 7);
		DRIVE_HANG_UP = new JoystickButton(RIGHT, 8);
		DEPLOY_HANG = new JoystickButton(MANIP, 2);
		
		ConfigureButtonMapping();
	}
	
	public double GetLeftY()
	{
		return LEFT.getY();
	}
	
	public double GetLeftX()
	{
		return LEFT.getX();
	}
	
	public double GetLeftTwist()
	{
		return LEFT.getTwist();
	}
	public double GetRightY()
	{
		return RIGHT.getY();
	}
	
	public double GetRightX()
	{
		return RIGHT.getX();
	}
	
	public double GetRightTwist()
	{
		return RIGHT.getTwist();
	}
	
	public double GetManipY()
	{
		return MANIP.getY();
	}
	
	public double GetManipX()
	{
		return MANIP.getX();
	}
	
	public double GetManipTwist()
	{
		return MANIP.getTwist();
	}
	
	private void ConfigureButtonMapping()
	{
		DRIVE_HANG_DOWN = new JoystickButton(RIGHT, 7);
		DRIVE_HANG_UP = new JoystickButton(RIGHT, 8);
		DEPLOY_HANG = new JoystickButton(MANIP, 2);
		AssignButtons();
	}
	private void AssignButtons()
	{
		DRIVE_HANG_DOWN.whileHeld(new DriveHangMotor(false));
		DRIVE_HANG_UP.whileHeld(new DriveHangMotor(true));
		DEPLOY_HANG.whenPressed(new DeployHang());
	}	
	
}
