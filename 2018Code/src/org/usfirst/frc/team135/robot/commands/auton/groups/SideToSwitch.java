package org.usfirst.frc.team135.robot.commands.auton.groups;

import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.RobotMap.*;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveStraightDistance;
import org.usfirst.frc.team135.robot.commands.auton.singles.SetLiftPosition;
import org.usfirst.frc.team135.robot.commands.teleop.ReleaseMandibles;
import org.usfirst.frc.team135.robot.commands.teleop.RetractMandibles;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SideToSwitch extends CommandGroup implements RobotMap {

    public SideToSwitch() 
    {
    	addParallel(new SetLiftPosition(LIFT.SWITCH_POSITION));
    	addParallel(new DriveStraightDistance(FIELD.SIDE_SWITCH_X, FIELD.SIDE_SWITCH_Y, 0));
    	addSequential(new ReleaseMandibles());
    	
    }
}
