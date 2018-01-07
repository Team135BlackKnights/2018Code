/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team135.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team135.robot.Commons.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI implements Commons
{
	private static OI instance;
	
	private Joystick[] joysticks = new Joystick[3];
	private JoystickButton[][] joystickButtons = new JoystickButton[3][12];
	
	public static final int
		LEFT = 0,
		RIGHT = 1,
		MANIP = 2;
	
	/*public static final int
		BUTTON1 = 1,
		BUTTON2 = 2,
		ETC.
	*/
	
	public static OI getInstance() {
		if (instance == null) {
			instance = new OI();
		}
		return instance;
	}
	
	private OI()
	{
		for (int i = 0; i < 3; i++)
		{
			joysticks[i] = new Joystick(i);
			
			for (int j = 1; j <= 12; j++)
			{
				joystickButtons[i][j - 1] = new JoystickButton(joysticks[i], j);
			}
		}
		
		assignButtons();
	}
	
	public double GetY(int stick)
	{
		return joysticks[stick].getY();
	}
	
	public double GetX(int stick)
	{
		return joysticks[stick].getX();
	}
	
	public double GetTwist(int stick)
	{
		return joysticks[stick].getTwist();
	}
	
	private void assignButtons()
	{
		
	}
	
}
