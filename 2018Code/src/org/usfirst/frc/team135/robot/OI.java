/*----------------------------------------------------------------------------*/
/* CopyRIGHT_JOY (c) 2RIGHT_JOY17-2RIGHT_JOY18 FIRST. All RIGHT_JOYs Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team135.robot;

//////import org.usfirst.frc.team135.robot.commands.DriveStraight;
//////////import org.usfirst.frc.team135.robot.commands.MotorTest;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI implements RobotMap
{
	public Joystick[] joysticks = new Joystick[3];
	
	public JoystickButton
		DRIVE_FL,
		DRIVE_BL,
		DRIVE_FR,
		DRIVE_BR,
		DRIVE_STRAIGHT_FORWARD,
		DRIVE_STRAIGHT_BACKWARD;

	public static OI instance;
	
	private OI()
	{
		for (int i = 0; i < joysticks.length; i++)
		{
			joysticks[i] = new Joystick(i);
		}	
		
		DRIVE_FL = new JoystickButton(joysticks[JOY.RIGHT], 5);
		DRIVE_BL = new JoystickButton(joysticks[JOY.RIGHT], 3);
		DRIVE_FR = new JoystickButton(joysticks[JOY.RIGHT], 6);
		DRIVE_BR = new JoystickButton(joysticks[JOY.RIGHT], 4);
		DRIVE_STRAIGHT_FORWARD = new JoystickButton(joysticks[JOY.RIGHT], 1);
		DRIVE_STRAIGHT_BACKWARD = new JoystickButton(joysticks[JOY.RIGHT], 2);
		
		mapBtns();
	}
	
	public void putData()
	{
		for (int i = 0; i < 2; i++)
		{
			SmartDashboard.putNumber("Joystick Y " + i, getY(i));
		}
	}
	
	private double deadband(double value)
	{
		if (Math.abs(value) > .06)
		{
			return 0;
		}
		else
		{
			return value;
		}
	}
	
	public void mapBtns()
	{
		/*
		DRIVE_FL.whileHeld(new MotorTest(
				MOTORS.NUMBER.FL.getCAN_ID(), 
				MOTORS.REVERSED.FL,
				MOTORS.MAX_VELOCITY));
		
		DRIVE_BL.whileHeld(new MotorTest(
				MOTORS.NUMBER.BL.getCAN_ID(), 
				MOTORS.REVERSED.BL,
				MOTORS.MAX_VELOCITY));
		
		DRIVE_FR.whileHeld(new MotorTest(
				MOTORS.NUMBER.FR.getCAN_ID(), 
				MOTORS.REVERSED.FL,
				MOTORS.MAX_VELOCITY));
		
		DRIVE_BR.whileHeld(new MotorTest(
				MOTORS.NUMBER.BR.getCAN_ID(), 
				MOTORS.REVERSED.BR,
				MOTORS.MAX_VELOCITY));

		DRIVE_STRAIGHT_FORWARD.whileHeld(new DriveStraight(true));
		DRIVE_STRAIGHT_BACKWARD.whileHeld(new DriveStraight(false));*/
	}
	
	public boolean button_isPressed(int stick, int button)
	{	
		return joysticks[stick].getRawButton(button);
	}
	
	public double getY(int stick)
	{
		return -deadband(joysticks[stick].getY());
	}
	
	public double getX(int stick)
	{
		return deadband(joysticks[stick].getX());
	}
	
	public double getTwist(int stick)
	{
		return deadband(joysticks[stick].getTwist() / 2.0);
	}
	
	public static OI getInstance()
	{
		if (instance == null)
		{
			instance = new OI();
		}
		
		return instance;
	}

}
