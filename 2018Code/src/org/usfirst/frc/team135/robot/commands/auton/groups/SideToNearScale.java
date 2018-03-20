package org.usfirst.frc.team135.robot.commands.auton.groups;

import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.commands.auton.singles.AutoDriveMandibleWheelsOut;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveStraightForward;
import org.usfirst.frc.team135.robot.commands.auton.singles.SetLiftPosition;
import org.usfirst.frc.team135.robot.commands.teleop.ExtendMandibles;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SideToNearScale extends CommandGroup implements RobotMap {
 
	private static final boolean _FORWARD = true;
    public SideToNearScale(boolean isRight) {
    	int strafe_direction = isRight ?  DIRECTION.LEFT : DIRECTION.RIGHT;
    	int encoder_direction = isRight ? DIRECTION.BACKWARD : DIRECTION.FORWARD;  	
    	
    	
    	addSequential(new ExtendMandibles());
    	addSequential(new DriveStraightForward(encoder_direction * (FIELD.SCALE_Y) - 60, isRight, 4));
    	addSequential(new SetLiftPosition(COMPETITION.LIFT.SCALE_POSITION));
    	//addSequential(new RetractMandibles());
    	addSequential(new DriveStraightForward(encoder_direction * (FIELD.SCALE_Y), isRight, 4));
    	addSequential(new AutoDriveMandibleWheelsOut(1));
    	
    	
    	//addSequential(new RetractMandibles());
    	//addSequential(new DriveStraightForward(encoder_direction * (FIELD.SCALE_Y), isRight, 4));
    	//addSequential(new DriveMandibleWheels(SideToNearScale._FORWARD));

    }
}
