package org.usfirst.frc.team135.robot.commands;

import org.usfirst.frc.team135.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveHang extends Command {

	private double speed;
    public DriveHang(double speed) {
        // Use requires() here to declare subsystem dependencies
        this.speed = speed;
    	requires(Robot.hang);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    		Robot.hang.InitializeHangMotors();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.hang.RunHangMotor(this.speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.hang.RunHangMotor(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
