package org.usfirst.frc.team135.robot.commands.auton.groups;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveDiagonal;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StackToSwitch extends CommandGroup {

    public StackToSwitch(boolean isRight) 
    {
    	double angle;
    	
    	if (isRight)
    	{
    		angle = 30;
    	}
    	else
    	{
    		angle = 330;
    	}
    	
    	addSequential(new DriveDiagonal(.5, angle, () -> Robot.camera.getXOffsetDegrees(), true, angle,  5));
    }
}
