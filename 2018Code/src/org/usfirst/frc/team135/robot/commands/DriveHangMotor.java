package org.usfirst.frc.team135.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.subsystems.Hang;

/**
 *
 */
public class DriveHangMotor extends Command {
	
	double HANG_MOTOR_POWER = 1;
	
	
    public DriveHangMotor(boolean isUp) {
        
    	requires(Robot.hang);
    	if (!isUp)
    	{
    		HANG_MOTOR_POWER *= -1;
    	}
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.hang.InitializeHang();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.hang.DriveHangMotor(HANG_MOTOR_POWER);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.hang.DriveHangMotor(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
