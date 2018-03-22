package org.usfirst.frc.team135.robot.commands.auton.groups;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveAlongProfile;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveDiagonal;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveStraightForward;
import org.usfirst.frc.team135.robot.commands.auton.singles.SetLiftPosition;
import org.usfirst.frc.team135.robot.commands.auton.singles.StrafeStraightForwards;
import org.usfirst.frc.team135.robot.commands.auton.singles.StrafeStraightSideways;
import org.usfirst.frc.team135.robot.commands.teleop.ExtendMandibles;
import org.usfirst.frc.team135.robot.commands.teleop.GrabMandibles;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MidToSwitch extends CommandGroup implements RobotMap{

	private static final double
		SPEED = 1.0,
		TIMEOUT = 5.0;
	
    public MidToSwitch(boolean switchIsRight) {
    	
    	addSequential(new ExtendMandibles());
       	addSequential(new SetLiftPosition(COMPETITION.LIFT.SWITCH_POSITION));
       	//addSequential(new StrafeStraightForwards(12, 1, true, () -> Robot.ultrasonic.getRightSonarValue(),  2));
       	
    	if(switchIsRight)
    	{
    		addSequential(new DriveDiagonal(.7, -5, () -> Robot.camera.getXOffsetDegrees() + 90.0, false, 5000));
    		//addSequential(new DriveAlongProfile(PROFILING.MID_TO_RIGHT_SWITCH, MidToSwitch.TIMEOUT));
    		//addSequential(new DriveStraightForward(FIELD.MID_SWITCH_X * .1 * -1, false, 5));
    	}
    	else
    	{
    		//addSequential(new DriveStraightForward(FIELD.MID_SWITCH_X  * .2, false, 5));
    	}
    	
  
    	//addSequential(new StrafeStraightSideways(2, 1, true, () -> Robot.ultrasonic.getLeftSonarValue(), 2));

       	addSequential(new GrabMandibles());
    	

    }
}
