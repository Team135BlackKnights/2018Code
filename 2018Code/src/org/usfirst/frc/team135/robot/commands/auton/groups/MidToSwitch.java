package org.usfirst.frc.team135.robot.commands.auton.groups;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap.FIELD;
import org.usfirst.frc.team135.robot.RobotMap.LIFT;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveStraightDistance;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveStraightPID;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveStraightTime;
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
        	
          	addSequential(new DriveStraightDistance(
        			FIELD.MID_SWITCH_X, -.8, 30, 0, () -> Robot.ultrasonic.getRearSonarValue(), true,
        			FIELD.MID_SWITCH_Y, .8, 27, 0, () -> Robot.ultrasonic.getLeftSonarValue(), true,
        			TIMEOUT));
    		
    		/*addSequential(new DriveStraightPID(FIELD.MID_SWITCH_X, () -> Robot.canifier.getRearLidarInches(), 
    											FIELD.MID_SWITCH_Y, () -> Robot.ultrasonic.getLeftSonarValue(),
    											3.0));*/
    	}
    	else
    	{
        	/*addSequential(new DriveStraightForwardDistance(
        			FIELD.MID_SWITCH_X, .5, 5, () -> Robot.canifier.getFrontLidarInches(), false,
        			FIELD.MID_SWITCH_Y, .5, 82, () -> Robot.ultrasonic.getLeftSonarValue(), true,
        			TIMEOUT));    		
        	*/
        	addSequential(new DriveStraightDistance(
        			FIELD.MID_SWITCH_X, .8, 30, 0, () -> Robot.ultrasonic.getFrontSonarValue(), true,
        			FIELD.MID_SWITCH_Y, .8, 27, 1, () -> Robot.ultrasonic.getLeftSonarValue(), true,
        			TIMEOUT));   	
    		
    	/*	addSequential(new DriveStraightTime(1.0, .75, 1));
    		addSequential(new DriveStraightPID(FIELD.MID_SWITCH_X, () -> Robot.canifier.getFrontLidarInches(), 
    											FIELD.MID_SWITCH_Y, () -> Robot.ultrasonic.getLeftSonarValue(),
    											3.0));
 
    	*/	
    		
    	}

    
    	

    }
}
