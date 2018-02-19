package org.usfirst.frc.team135.robot.commands.auton.groups;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.RobotMap.*;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveStraightDistance;
import org.usfirst.frc.team135.robot.commands.auton.singles.SetLiftPosition;
/**
 *
 */
public class SideToAutoline extends CommandGroup implements RobotMap{

	private static final double 
		SPEED = 1.0,
		TIMEOUT = 3.0;

    public SideToAutoline() {
    	addSequential(new DriveStraightDistance(
    			FIELD.AUTO_LINE, 1.0, 82, 0, () -> Robot.canifier.getRearLidarInches(), true,
    			0.0, 0, 5, 0, () -> Robot.ultrasonic.getLeftSonarValue(), false,
    			TIMEOUT));
    	//addSequential(new SetLiftPosition(LIFT.SWITCH_POSITION));
    }
}
