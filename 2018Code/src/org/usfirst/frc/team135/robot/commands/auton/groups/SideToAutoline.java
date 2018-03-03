package org.usfirst.frc.team135.robot.commands.auton.groups;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.RobotMap.*;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveStraightForward;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveStraightForwardDistance;
import org.usfirst.frc.team135.robot.commands.auton.singles.SetLiftPosition;
/**
 *
 */
public class SideToAutoline extends CommandGroup implements RobotMap{
	
    public SideToAutoline(boolean isRight) {
    	
    	addSequential(new DriveStraightForward(FIELD.AUTO_LINE, isRight));
    }
}
