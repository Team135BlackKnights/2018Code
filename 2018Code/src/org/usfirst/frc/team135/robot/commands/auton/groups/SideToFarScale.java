package org.usfirst.frc.team135.robot.commands.auton.groups;

import edu.wpi.first.wpilibj.command.TimedCommand;
import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.RobotMap.COMPETITION;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveStraightForward;
import org.usfirst.frc.team135.robot.commands.auton.singles.ResetEncoders;
import org.usfirst.frc.team135.robot.commands.auton.singles.SetLiftPosition;
import org.usfirst.frc.team135.robot.commands.auton.singles.StrafeStraightSideways;
import org.usfirst.frc.team135.robot.commands.teleop.ExtendMandibles;
import org.usfirst.frc.team135.robot.commands.teleop.GrabMandibles;
import org.usfirst.frc.team135.robot.commands.auton.singles.Rotate;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team135.robot.commands.auton.singles.TurnToAngle;

/**
 *
 */
public class SideToFarScale extends CommandGroup implements RobotMap {

	
    public SideToFarScale(boolean isRight) {
   //     super(timeout);
        int EncoderDirection = DIRECTION.BACKWARD;
        
    	addSequential(new DriveStraightForward(EncoderDirection * FIELD.FAR_SCALE_DISTANCE_FROM_WALL, isRight, 3));
    	addSequential(new TurnToAngle(90, 10));
    	addSequential(new ResetEncoders());
    	addSequential(new DriveStraightForward(EncoderDirection * -FIELD.FAR_SCALE_DISTANCE_TO_SCALE, isRight, 3));
    	addSequential(new ExtendMandibles());
    	addSequential(new SetLiftPosition(COMPETITION.LIFT.SCALE_POSITION));
    	addSequential(new StrafeStraightSideways(86, -1, true, () -> Robot.ultrasonic.getLeftSonarValue(),  STRAFE_MODE.GAIN, 3)); // Sonar distance from switch, -1 means left, backwards = true, Lambda passed is an left ultrasonic value, Gains value for strafe mode, 3 second timeout 
    	addSequential(new GrabMandibles());
    }
}