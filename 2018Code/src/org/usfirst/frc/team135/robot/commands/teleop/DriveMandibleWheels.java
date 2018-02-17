package org.usfirst.frc.team135.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.subsystems.Intake;

/**
 *
 */
public class DriveMandibleWheels extends Command {

	private int _power;
    public DriveMandibleWheels(boolean isInward) {
    	
    	this._power = (isInward) ? 1 : -1;
    	requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.intake.DriveWheels(this._power);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intake.DriveWheels(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}