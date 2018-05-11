package org.usfirst.frc.team135.robot.commands.auton.groups;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap.STRAFE_MODE;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveDiagonal;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveMandiblesWheelsTimed;
import org.usfirst.frc.team135.robot.commands.auton.singles.Rotate;
import org.usfirst.frc.team135.robot.commands.auton.singles.StrafeStraightSideways;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SwitchToStack extends CommandGroup {

    public SwitchToStack(boolean isRight) 
    {
    	final int LEFT = -1;
    	double angle;
    	
    	if (isRight)
    	{
    		angle = 210;
    	}
    	else
    	{
    		angle = 150;
    	}
    	
    	addSequential(new DriveDiagonal(.5, angle, () -> Robot.camera.getXOffsetDegrees(), true, angle,  5));
    	addParallel(new DriveMandiblesWheelsTimed(1, 5));
    }
}
