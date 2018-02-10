package org.usfirst.frc.team135.robot.commands.auton.groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.RobotMap.*;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveStraightDistance;
/**
 *
 */
public class ToAutoline extends CommandGroup implements RobotMap{

    public ToAutoline() {
    	addSequential(new DriveStraightDistance(0, FIELD.AUTO_LINE, 0));
    }
}
