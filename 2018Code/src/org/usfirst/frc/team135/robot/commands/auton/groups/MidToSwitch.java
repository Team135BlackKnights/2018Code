package org.usfirst.frc.team135.robot.commands.auton.groups;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.RobotMap.COMPETITION.CAMERA;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveAlongProfile;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveDiagonal;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveStraightForward;
import org.usfirst.frc.team135.robot.commands.auton.singles.InitializeAngle;
import org.usfirst.frc.team135.robot.commands.auton.singles.SetLiftPosition;
import org.usfirst.frc.team135.robot.commands.auton.singles.StrafeStraightForwards;
import org.usfirst.frc.team135.robot.commands.auton.singles.StrafeStraightSideways;
import org.usfirst.frc.team135.robot.commands.teleop.ExtendMandibles;
import org.usfirst.frc.team135.robot.commands.teleop.GrabMandibles;
import org.usfirst.frc.team135.robot.commands.teleop.RetractMandibles;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MidToSwitch extends CommandGroup implements RobotMap{

	private static final double
		SPEED = 1.0,
		TIMEOUT = 5.0;
	
    public MidToSwitch(boolean switchIsRight) {
    	
    	addSequential(new InitializeAngle(270));
    	addSequential(new RetractMandibles());
       	addSequential(new SetLiftPosition(COMPETITION.LIFT.SWITCH_POSITION));
       	//addSequential(new StrafeStraightForwards(12, 1, true, () -> Robot.ultrasonic.getRightSonarValue(),  2));
       	
       	Robot.camera.setDriverMode(false);
       	
    	if(switchIsRight)
    	{
    		addSequential(new DriveDiagonal(.5, 20, () -> Robot.camera.getXOffsetDegrees() + 90.0, false, Robot.navx.getFusedAngle(), 5));
    		//addSequential(new DriveAlongProfile(PROFILING.MID_TO_RIGHT_SWITCH, MidToSwitch.TIMEOUT));
    		//addSequential(new DriveStraightForward(FIELD.MID_SWITCH_X * .1 * -1, false, 5));
    	}
    	else
    	{
    		addSequential(new DriveDiagonal(.5, -30, () -> Robot.camera.getXOffsetDegrees() + 90.0, true, Robot.navx.getFusedAngle(), 5));
    	}
    	
  
    	//addSequential(new StrafeStraightSideways(2, 1, true, () -> Robot.ultrasonic.getLeftSonarValue(), 2));

       	addSequential(new GrabMandibles());
    	

    }
}
