package org.usfirst.frc.team135.robot.commands.auton.groups;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap.FIELD;
import org.usfirst.frc.team135.robot.RobotMap.LIFT;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveStraightForwardDistance;
import org.usfirst.frc.team135.robot.commands.auton.singles.SetLiftPosition;
import org.usfirst.frc.team135.robot.commands.teleop.ReleaseMandibles;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MidToSwitch extends CommandGroup {

	private static final double
		SPEED = 1.0,
		TIMEOUT = 2.5;
	
    public MidToSwitch(boolean switchIsRight) {
    	addParallel(new SetLiftPosition(LIFT.SWITCH_POSITION));
    	addParallel(new DriveStraightForwardDistance(
    			FIELD.MID_SWITCH_X, () -> Robot.canifier.ReadRearLidarInches(),
    			FIELD.MID_SWITCH_Y, () -> Robot.ultrasonic.GetLeftSonarValue(),
    			SPEED, TIMEOUT));
    	addSequential(new ReleaseMandibles());
    }
}
