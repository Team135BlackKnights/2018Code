package org.usfirst.frc.team135.robot.commands.teleop;

import org.usfirst.frc.team135.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DeployHang extends Command {

    public DeployHang() {
    	requires(Robot.hang);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.hang.setRelease(true);

    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
