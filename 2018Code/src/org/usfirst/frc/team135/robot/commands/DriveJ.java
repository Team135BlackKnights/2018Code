package org.usfirst.frc.team135.robot.commands;

import org.usfirst.frc.team135.robot.Robot;

import org.usfirst.frc.team135.robot.OI;
import org.usfirst.frc.team135.robot.subsystems.DriveTrain;

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
    	Robot.drivetrain.InitializeDriveTrain();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	Robot.drivetrain.driveCartesianLocal(Robot.oi.GetY(OI.RIGHT), Robot.oi.GetX(OI.RIGHT), Robot.oi.GetTwist(OI.RIGHT),
    									Robot.gyro.getCorrectedAngle());
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
