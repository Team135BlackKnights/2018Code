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

private JoystickButton GRAB, RELEASE, RETRACT_MANDIBLES, EXTEND_MANDIBLES;
	

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
		
		ConfigureButtonMapping();
	}
	
	public double SetThreshold(double value)
	{
		if (value <= .1 && value >= -.1)
		{
			return 0;
		}
		else
		{
			return value;
		}
	}
	
	public double GetLeftY()
	{
		return SetThreshold(LEFT.getY());
	}
	
	public double GetLeftX()
	{
		return SetThreshold(LEFT.getX());
	}
	
	public double GetLeftTwist()
	{
		return SetThreshold(LEFT.getTwist());
	}
	public double GetRightY()
	{
		return SetThreshold(RIGHT.getY());
	}
	
	public double GetRightX()
	{
		return SetThreshold(RIGHT.getX());
	}
	
	public double GetRightTwist()
	{
		return SetThreshold(RIGHT.getTwist());
	}
	
	public double GetManipY()
	{
		return SetThreshold(MANIP.getY());
	}
	
	public double GetManipX()
	{
		return SetThreshold(MANIP.getX());
	}
	
	public double GetManipTwist()
	{
		return SetThreshold(MANIP.getTwist());
	}
	
	void ConfigureButtonMapping()
	{
		GRAB = new JoystickButton(MANIP, 8);
		RELEASE = new JoystickButton(MANIP, 9);
		RETRACT_MANDIBLES = new JoystickButton(MANIP, 10);
		EXTEND_MANDIBLES = new JoystickButton(MANIP, 11);
		
		AssignButtons();
	}
	private void AssignButtons()
	{
		GRAB.whenPressed(new GrabClaw());
		RELEASE.whenPressed(new ReleaseClaw());
		RETRACT_MANDIBLES.whenPressed(new RetractMandibles());
		EXTEND_MANDIBLES.whenPressed(new ExtendMandibles());

	}	
	
}
