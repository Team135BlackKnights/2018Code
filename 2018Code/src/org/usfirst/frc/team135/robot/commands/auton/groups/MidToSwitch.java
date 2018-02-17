package org.usfirst.frc.team135.robot.commands.auton.groups;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap.FIELD;
import org.usfirst.frc.team135.robot.RobotMap.LIFT;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveStraightForwardDistance;
import org.usfirst.frc.team135.robot.commands.auton.singles.SetLiftPosition;
import org.usfirst.frc.team135.robot.commands.teleop.ReleaseMandibles;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MidToSwitch extends CommandGroup {

	private static final double
		SPEED = 1.0,
		TIMEOUT = 5.0;
	
    public MidToSwitch(boolean switchIsRight) {
    	
    	
    	//addSequential(new ReleaseMandibles());
    	
    	//addSequential(new SetLiftPosition(LIFT.SWITCH_POSITION));
    	
    	if(switchIsRight)
    	{
        	/*addSequential(new DriveStraightForwardDistance(
        			FIELD.MID_SWITCH_X, .5, 5, () -> Robot.canifier.getRearLidarInches(), false,
        			FIELD.MID_SWITCH_Y, 1.0,  82, () -> Robot.ultrasonic.getLeftSonarValue(), true,
        			TIMEOUT));*/
        	
          	addSequential(new DriveStraightForwardDistance(
        			FIELD.MID_SWITCH_X, 1, 25, () -> Robot.canifier.getRearLidarInches(), true,
        			FIELD.MID_SWITCH_Y, .75, 50, () -> Robot.ultrasonic.getLeftSonarValue(), true,
        			TIMEOUT));
    	}
    	else
    	{
        	/*addSequential(new DriveStraightForwardDistance(
        			FIELD.MID_SWITCH_X, .5, 5, () -> Robot.canifier.getFrontLidarInches(), false,
        			FIELD.MID_SWITCH_Y, .5, 82, () -> Robot.ultrasonic.getLeftSonarValue(), true,
        			TIMEOUT));    		
        	*/
        	addSequential(new DriveStraightForwardDistance(
        			FIELD.MID_SWITCH_X, 1, 25, () -> Robot.canifier.getFrontLidarInches(), true,
        			FIELD.MID_SWITCH_Y, .75, 50, () -> Robot.ultrasonic.getLeftSonarValue(), true,
        			TIMEOUT));    		
    	
    	}

    
    	

    }
}
