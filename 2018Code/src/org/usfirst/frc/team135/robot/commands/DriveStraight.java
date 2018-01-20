package org.usfirst.frc.team135.robot.commands;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.extra.AngleOut;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveStraight extends Command {

	PIDController angleController;
	AngleOut angleOut;
	private double timeout;
	
    public DriveStraight() 
    {
    	requires(Robot.drivetrain);
    	requires(Robot.gyro);
    	angleOut = new AngleOut();
    	//angleController = new PIDController(0.0, 0.0, 0.0, Robot.gyro.getPIDSource(), angleOut);
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	this.addChild(angleController);
    	this.addChild(angleOut);
    	this.addChild(timeout);
    	
    	SmartDashboard.putData("Drive Straight", this);
    	
    	setTimeout(timeout);
    	angleController.enable();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    //	Robot.drivetrain.CartesianDrive(y, x, angleOut.output, Robot.gyro.getRawAngle());
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
