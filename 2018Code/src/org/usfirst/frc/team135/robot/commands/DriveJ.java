package org.usfirst.frc.team135.robot.commands;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap;
//import org.usfirst.frc.team135.robot.util.PIDOutputBuf;
///////////////////////import org.usfirst.frc.team135.robot.util.PID_GyroSource;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveJ extends Command implements RobotMap {



    public DriveJ() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
        
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    
    	Robot.drivetrain.driveCartesian(Robot.oi.getY(JOY.RIGHT), Robot.oi.getX(JOY.RIGHT), Robot.oi.getTwist(JOY.LEFT), 0, 
    									Robot.oi.button_isPressed(JOY.RIGHT, BUTTON.UNLOCK_ROTATION));	
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
    	end();
    }
}
