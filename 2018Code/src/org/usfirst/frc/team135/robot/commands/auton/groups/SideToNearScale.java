package org.usfirst.frc.team135.robot.commands.auton.groups;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.RobotMap.*;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveMandiblesWheelsTimed;
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
    	int strafe_direction = isRight ?  DIRECTION.LEFT : DIRECTION.RIGHT;
    	int encoder_direction = isRight ? DIRECTION.BACKWARD : DIRECTION.FORWARD;  
    	double drive_mandible_wheels_timeout = 1;
    	
    	
    	addSequential(new DriveStraightForward(encoder_direction * (FIELD.SIDE_SCALE_Y - 40), isRight, 4));
    	addSequential(new ExtendMandibles());
    	addSequential(new SetLiftPosition(COMPETITION.LIFT.SCALE_POSITION));
    	addSequential(new RetractMandibles());
    	addSequential(new DriveStraightForward(encoder_direction * (FIELD.SIDE_SCALE_Y), isRight, 4));
    	addSequential(new StrafeStraightSideways(FIELD.SIDE_SCALE_X,
       											strafe_direction, !isRight, () -> Robot.ultrasonic.getLeftSonarValue(), STRAFE_MODE.GAIN, 1.5));
    	addSequential(new DriveMandiblesWheelsTimed(drive_mandible_wheels_timeout, -1));

    }
}
