package org.usfirst.frc.team135.robot.commands.auton.groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.RobotMap.*;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveStraightForwardDistance;
import org.usfirst.frc.team135.robot.commands.auton.singles.SetLiftPosition;
/**
 *
 */
public class SideToAutoline extends CommandGroup implements RobotMap{

    public SideToAutoline() {
    	addSequential(new DriveStraightForwardDistance(FIELD.AUTO_LINE, 0.0, 3.0));
    	//addSequential(new SetLiftPosition(LIFT.SWITCH_POSITION));
    }
}
