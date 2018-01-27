package org.usfirst.frc.team135.robot.commands;

import org.usfirst.frc.team135.robot.Robot;
import edu.wpi.first.wpilibj.*;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MiddleSonar extends Command {

    public MiddleSonar() {
        requires(Robot.drivetrain);
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	 Robot.drivetrain.driveFieldOriented(-1, 0, 0, 0); 
    	 DriveSideSonar();
    	 
    }
    
    private void DriveSideSonar()
	{
		if (Robot.sonar.GetLeftSonarValue() >= 8)
		{
			Robot.drivetrain.driveFieldOriented(-.5, 0, 0, 0); 
		}
		else if (Robot.sonar.GetLeftSonarValue() >= 2)
		{
			Robot.drivetrain.driveFieldOriented(-.25, 0, 0, 0);
		}
		else
		{
			Robot.drivetrain.driveFieldOriented(0, 0, 0, 0);
		}
	}
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
