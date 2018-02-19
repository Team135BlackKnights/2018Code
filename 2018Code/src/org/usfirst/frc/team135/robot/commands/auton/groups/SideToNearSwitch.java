package org.usfirst.frc.team135.robot.commands.auton.groups;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.RobotMap.*;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveStraightDistance;
import org.usfirst.frc.team135.robot.commands.auton.singles.SetLiftPosition;
import org.usfirst.frc.team135.robot.commands.teleop.DriveMandibleWheels;
import org.usfirst.frc.team135.robot.commands.teleop.ExtendMandibles;
import org.usfirst.frc.team135.robot.commands.teleop.GrabMandibles;
import org.usfirst.frc.team135.robot.commands.teleop.ReleaseMandibles;
import org.usfirst.frc.team135.robot.commands.teleop.RetractMandibles;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SideToNearSwitch extends CommandGroup implements RobotMap {

	private boolean done = false;
    public SideToNearSwitch() 
    {

        
        
       	addSequential(new DriveStraightDistance(
       			0, .5, 5, 0, () -> Robot.ultrasonic.getLeftSonarValue(), false,
       			FIELD.SIDE_SWITCH_Y, 1.0, 82, 0, () -> Robot.canifier.getFrontLidarInches(), true,
       			2.0));
       	
    	addSequential(new ExtendMandibles());
       	addSequential(new SetLiftPosition(LIFT.SWITCH_POSITION));
       	
       	addSequential(new DriveStraightDistance(
       			FIELD.SIDE_SWITCH_X, .5, 5, 0, () -> Robot.ultrasonic.getLeftSonarValue(), true,
       			Robot.canifier.getRearLidarInches(), 1.0, 82, 0, () -> Robot.canifier.getFrontLidarInches(), false,
       			2.0));
       	
       	
       	addSequential(new GrabMandibles());

    	
    }
}
