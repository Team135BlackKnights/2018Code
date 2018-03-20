package org.usfirst.frc.team135.robot.commands.auton.groups;

import org.usfirst.frc.team135.robot.RobotMap.COMPETITION;
import org.usfirst.frc.team135.robot.RobotMap.DIRECTION;
import org.usfirst.frc.team135.robot.commands.auton.singles.Rotate;
import org.usfirst.frc.team135.robot.commands.auton.singles.SetLiftPosition;
import org.usfirst.frc.team135.robot.commands.teleop.ExtendMandibles;
import org.usfirst.frc.team135.robot.commands.teleop.RetractMandibles;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SideToFarSwitch extends CommandGroup {

	private static final double STRAFE_DISTANCE_FROM_SWITCH = 4;
    public SideToFarSwitch(boolean isRight) 
    {
    	int strafe_direction = isRight ?  DIRECTION.LEFT : DIRECTION.RIGHT;
    	int encoder_direction = isRight ? DIRECTION.BACKWARD : DIRECTION.FORWARD;  	
    	
    	addSequential(new ExtendMandibles());
    	//addSequential(new DriveStraightForward(encoder_direction * (FIELD.FAR_SWITCH_Y), 4));
    	addSequential(new Rotate(90));
    	addParallel(new SetLiftPosition(COMPETITION.LIFT.SWITCH_POSITION));
    	addSequential(new RetractMandibles());
    	//addSequential(new DriveStraightForward(-encoder_direction * (FIELD.FAR_SWITCH_X), 4));
       	//addSequential(new DriveStraightSideways(SideToFarSwitch.STRAFE_DISTANCE_FROM_SWITCH,
					//strafe_direction, () -> Robot.ultrasonic.getRightSonarValue(), 1.5));


    }
}
