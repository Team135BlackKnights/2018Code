package org.usfirst.frc.team135.robot.commands.auton.groups;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.RobotMap.*;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveAlongProfile;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveMandibleWheelsTimed;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveStraightForward;
import org.usfirst.frc.team135.robot.commands.auton.singles.SetLiftPosition;
import org.usfirst.frc.team135.robot.commands.auton.singles.StrafeStraightSideways;
import org.usfirst.frc.team135.robot.commands.teleop.DriveMandibleWheels;
import org.usfirst.frc.team135.robot.commands.teleop.ExtendMandibles;
import org.usfirst.frc.team135.robot.commands.teleop.ReleaseMandibles;
import org.usfirst.frc.team135.robot.commands.teleop.RetractMandibles;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SideToNearScale extends CommandGroup implements RobotMap {
 
	private static final boolean _BACKWARD = false;
    public SideToNearScale(boolean isRight) {
  /*  	int strafe_direction = isRight ?  DIRECTION.LEFT : DIRECTION.RIGHT;
    	int encoder_direction = isRight ? DIRECTION.BACKWARD : DIRECTION.FORWARD;  	
    	
    	addSequential(new ExtendMandibles());
    	addSequential(new DriveStraightForward(encoder_direction * (FIELD.SCALE_Y), isRight, 4));
    	addParallel(new SetLiftPosition(COMPETITION.LIFT.SCALE_POSITION));
    	addSequential(new RetractMandibles());
    	//addSequential(new DriveStraightForward(encoder_direction * (FIELD.SCALE_Y), isRight, 4));
    	addSequential(new DriveMandibleWheels(SideToNearScale._BACKWARD));
		*/
	if (isRight)
	{
		addSequential(new DriveAlongProfile(PROFILING.RightSideNearScale, 4));	
	}
	else
	{
		addSequential(new DriveAlongProfile(PROFILING.LeftSideNearScale, 4));
	}
		addSequential(new ExtendMandibles());
		addSequential(new SetLiftPosition(COMPETITION.LIFT.SCALE_POSITION));
		addSequential(new DriveMandibleWheelsTimed(-1, 1));
    }
}
