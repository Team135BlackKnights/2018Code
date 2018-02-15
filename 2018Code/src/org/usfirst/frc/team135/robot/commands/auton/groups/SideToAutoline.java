package org.usfirst.frc.team135.robot.commands.auton.groups;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.RobotMap.*;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveStraightForwardDistance;
import org.usfirst.frc.team135.robot.commands.auton.singles.SetLiftPosition;
/**
 *
 */
public class SideToAutoline extends CommandGroup implements RobotMap{

	private static final double 
		SPEED = 1.0,
		TIMEOUT = 3.0;

    public SideToAutoline() {
    	addSequential(new DriveStraightForwardDistance(
    			FIELD.AUTO_LINE, () -> Robot.canifier.ReadRearLidarInches(),
    			0.0, () -> Robot.ultrasonic.GetLeftSonarValue(), //Needs distance from wall
    			SPEED, TIMEOUT));
    	//addSequential(new SetLiftPosition(LIFT.SWITCH_POSITION));
    }
}
