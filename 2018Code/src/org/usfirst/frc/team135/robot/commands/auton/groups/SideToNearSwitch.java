package org.usfirst.frc.team135.robot.commands.auton.groups;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.RobotMap.*;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveStraightForward;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveStraightForwardDistance;
import org.usfirst.frc.team135.robot.commands.auton.singles.SetLiftPosition;
import org.usfirst.frc.team135.robot.commands.auton.singles.StrafeStraightSideways;
import org.usfirst.frc.team135.robot.commands.teleop.DriveMandibleWheels;
import org.usfirst.frc.team135.robot.commands.teleop.ExtendMandibles;
import org.usfirst.frc.team135.robot.commands.teleop.GrabMandibles;
import org.usfirst.frc.team135.robot.commands.teleop.ReleaseMandibles;
import org.usfirst.frc.team135.robot.commands.teleop.RetractMandibles;
import org.usfirst.frc.team135.robot.util.FunctionalDoubleManager;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SideToNearSwitch extends CommandGroup implements RobotMap {	
    public SideToNearSwitch(boolean isRight) 
    {

    	addSequential(new DriveStraightForward(FIELD.SIDE_SWITCH_Y, isRight));
    	addSequential(new ExtendMandibles());
       	addSequential(new SetLiftPosition(COMPETITION.LIFT.SWITCH_POSITION));
       	addSequential(new StrafeStraightSideways(FIELD.SIDE_SWITCH_X, () -> Robot.ultrasonic.getRightSonarValue()));       	
       	addSequential(new GrabMandibles());

    	
    }
}
