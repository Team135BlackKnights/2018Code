package org.usfirst.frc.team135.robot.commands.auton.groups;

import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveStraightForward;

import edu.wpi.first.wpilibj.command.CommandGroup;
/**
 *
 */
public class SideToAutoline extends CommandGroup implements RobotMap{
	
    public SideToAutoline(boolean isBackward) {
    	
    	addSequential(new DriveStraightForward(FIELD.AUTO_LINE, isBackward, 4));
    }
}
