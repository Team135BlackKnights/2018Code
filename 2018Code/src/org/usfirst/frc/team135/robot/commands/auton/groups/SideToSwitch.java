package org.usfirst.frc.team135.robot.commands.auton.groups;

import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.RobotMap.*;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveStraightForwardDistance;
import org.usfirst.frc.team135.robot.commands.auton.singles.SetLiftPosition;
import org.usfirst.frc.team135.robot.commands.teleop.DriveMandibleWheels;
import org.usfirst.frc.team135.robot.commands.teleop.ExtendMandibles;
import org.usfirst.frc.team135.robot.commands.teleop.ReleaseMandibles;
import org.usfirst.frc.team135.robot.commands.teleop.RetractMandibles;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SideToSwitch extends CommandGroup implements RobotMap {

	private boolean done = false;
    public SideToSwitch() 
    {
    	if (!done)
    	{
    		
        	addParallel(new DriveStraightForwardDistance(FIELD.SIDE_SWITCH_X, FIELD.SIDE_SWITCH_Y, 2.0));
        	//addParallel(new SetLiftPosition(LIFT.SWITCH_POSITION));
        	//addSequential(new ExtendMandibles());
        	//addSequential(new StrafeStraightSidewaysDistance(FIELD.SIDE_SWITCH_X, false));    
        	//addSequential(new ReleaseMandibles());
    	}
    	
    	done = true;
    
    	
    }
}
