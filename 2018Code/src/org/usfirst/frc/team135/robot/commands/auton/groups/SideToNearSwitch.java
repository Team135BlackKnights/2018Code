package org.usfirst.frc.team135.robot.commands.auton.groups;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveStraightForward;
import org.usfirst.frc.team135.robot.commands.auton.singles.SetLiftPosition;
import org.usfirst.frc.team135.robot.commands.auton.singles.StrafeStraightSideways;
import org.usfirst.frc.team135.robot.commands.teleop.ExtendMandibles;
import org.usfirst.frc.team135.robot.commands.teleop.GrabMandibles;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SideToNearSwitch extends CommandGroup implements RobotMap {	
    public SideToNearSwitch(boolean isRight) 
    {
    	int direction = isRight ? -1 : 1;
    	//addSequential(new DriveStraightForward(direction * FIELD.SIDE_SWITCH_Y, isRight, 5));
    	//addSequential(new ExtendMandibles());
       	//addSequential(new SetLiftPosition(COMPETITION.LIFT.SWITCH_POSITION));
       	addSequential(new StrafeStraightSideways(FIELD.SIDE_SWITCH_X, () -> Robot.ultrasonic.getLeftSonarValue()));       	
       	//addSequential(new GrabMandibles());

    	
    }
}
