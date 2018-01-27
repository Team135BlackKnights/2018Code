package org.usfirst.frc.team135.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.subsystems.Intake;

/**
 *
 */
public class DriveMandibleWheels extends Command {

    public DriveMandibleWheels() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.intake.InitializeWheelMotors();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.intake.DriveWheels(Robot.oi.GetManipY());
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
    }
}
