package org.usfirst.frc.team135.robot.commands.auton.groups;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.RobotMap.*;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveStraightForward;
import org.usfirst.frc.team135.robot.commands.auton.singles.SetLiftPosition;
import org.usfirst.frc.team135.robot.commands.auton.singles.StrafeStraightSideways;
import org.usfirst.frc.team135.robot.commands.teleop.DriveMandibleWheels;
import org.usfirst.frc.team135.robot.commands.teleop.ReleaseMandibles;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SideToNearScale extends CommandGroup implements RobotMap {

    public SideToNearScale(boolean isRight) {
    	//addParallel(new SetLiftPosition(COMPETITION.LIFT.SCALE_POSITION));
    	addParallel(new DriveStraightForward(FIELD.SCALE_Y, isRight, 4));
    	//addSequential(new StrafeStraightSideways(FIELD.SCALE_X, () -> Robot.ultrasonic.getRightSonarValue()));
    	//addSequential(new DriveMandibleWheels(false));

    }
}
