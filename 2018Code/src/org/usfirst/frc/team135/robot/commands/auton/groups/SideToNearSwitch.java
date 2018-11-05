package org.usfirst.frc.team135.robot.commands.auton.groups;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveMandiblesWheelsTimed;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveStraightForward;
import org.usfirst.frc.team135.robot.commands.auton.singles.SetLiftPosition;
import org.usfirst.frc.team135.robot.commands.auton.singles.StrafeStraightSideways;
import org.usfirst.frc.team135.robot.commands.teleop.ExtendMandibles;
import org.usfirst.frc.team135.robot.commands.teleop.GrabMandibles;
import org.usfirst.frc.team135.robot.commands.teleop.ReleaseMandibles;
import org.usfirst.frc.team135.robot.commands.teleop.RetractMandibles;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SideToNearSwitch extends CommandGroup implements RobotMap {	
	
	private static final double DISTANCE_FROM_SWITCH_WALL = 4;
    public SideToNearSwitch(boolean isRight) 
    {
    	int strafe_direction = isRight ?  DIRECTION.LEFT : DIRECTION.RIGHT;
    	int encoder_direction = isRight ? DIRECTION.BACKWARD : DIRECTION.FORWARD;
    	double drive_mandible_wheels_timeout = 1;

    	Timer timer = new Timer();
    	timer.start();
    	addSequential(new DriveStraightForward(encoder_direction * FIELD.SIDE_SWITCH_Y, isRight, 3));
    	while (timer.get() < .5){}
    	timer.reset();
    	addSequential(new RetractMandibles());
    	while (timer.get() < .5){}
    	timer.reset();
       	addSequential(new SetLiftPosition(COMPETITION.LIFT.SWITCH_POSITION));
       	while (timer.get() < .5){}
       	timer.reset();
       	addSequential(new StrafeStraightSideways(SideToNearSwitch.DISTANCE_FROM_SWITCH_WALL,
       											strafe_direction, !isRight, () -> Robot.ultrasonic.getRightSonarValue(), STRAFE_MODE.REDUCE, 1.5));
       	while (timer.get() < .5){}
       	timer.reset();
    	addSequential(new DriveMandiblesWheelsTimed(drive_mandible_wheels_timeout, 1));
       	
    }
}
