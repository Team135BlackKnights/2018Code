package org.usfirst.frc.team135.robot.commands;

import org.usfirst.frc.team135.robot.Robot;

import java.util.Optional;

import org.usfirst.frc.team135.robot.OI;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveJ extends Command {
	
    public DriveJ() 
    {
    	requires(Robot.drivetrain);
    	requires(Robot.gyro);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Double globalDirection = SmartDashboard.getBoolean("Global directions", true) ? 
    								Robot.gyro.getCorrectedAngle() : null;
    	
    	Robot.drivetrain.CartesianDrive(Robot.oi.GetY(OI.RIGHT), Robot.oi.GetX(OI.RIGHT), Robot.oi.GetTwist(OI.LEFT),
    									Optional.ofNullable(globalDirection));
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
