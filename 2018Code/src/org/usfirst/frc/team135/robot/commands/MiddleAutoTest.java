package org.usfirst.frc.team135.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team135.robot.*;
import edu.wpi.first.wpilibj.*;

/**
 *
 */
public class MiddleAutoTest extends CommandGroup {

	
	//private String Orientation;
    public MiddleAutoTest() {
        requires(Robot.drivetrain);
        
        
    	//this.Orientation = orientation;

        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.InitializeDriveTrain(); //x,y

        Robot.drivetrain.driveFieldOriented(1, Math.tan(30), 0, 0); //30 Degrees
        SonarManager();
        Timer.delay(.1);
        GoToSwitch();
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    	private void SonarManager()
    	{
    		if (Robot.sonar.GetLeftSonarValue() >= 30 && Robot.sonar.GetFrontSonarValue() <= 145) //Goes at 30 Degrees
    		{
    			Robot.drivetrain.driveFieldOriented(1, Math.tan(30), 0, 0);
    		}
    	/*	else if (Robot.sonar.GetLeftSonarValue() <= 30 && Robot.sonar.GetFrontSonarValue() <= 145) //Continues going up
    		{
    			Robot.drivetrain.driveFieldOriented(0, 1, 0, 0);
    		}
    		else if (Robot.sonar.GetLeftSonarValue() >= 30 && Robot.sonar.GetFrontSonarValue() >= 145) //Continues going right
    		{
    			Robot.drivetrain.driveFieldOriented(1, 0, 0, 0);
    		}*/
    		else //Don't drive at all
    		{
    			Robot.drivetrain.driveFieldOriented(0, 0, 0, 0); 
    		}
    	}
    	private void GoToSwitch()
    	{
    		if (Robot.sonar.GetFrontSonarValue() <= 20)
    		{
    			Robot.drivetrain.driveFieldOriented(0, 1, 0, 0);
    		}
    		else
    		{
    			Robot.drivetrain.driveFieldOriented(0, 0, 0, 0);
    		}
    	}
    
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
