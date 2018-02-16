package org.usfirst.frc.team135.robot.commands.auton.groups;

import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.RobotMap.*;
import org.usfirst.frc.team135.robot.commands.auton.singles.SetLiftPosition;
import org.usfirst.frc.team135.robot.commands.teleop.DriveMandibleWheels;
import org.usfirst.frc.team135.robot.commands.teleop.ReleaseMandibles;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SideToNearScale extends CommandGroup implements RobotMap {

    public SideToNearScale() {
    	addParallel(new SetLiftPosition(LIFT.SCALE_POSITION));
    	//addParallel(new DriveStraightDistance(FIELD.SCALE_X, FIELD.SCALE_Y, 0));
    	addSequential(new DriveMandibleWheels(false)); //false is outward

    }
}