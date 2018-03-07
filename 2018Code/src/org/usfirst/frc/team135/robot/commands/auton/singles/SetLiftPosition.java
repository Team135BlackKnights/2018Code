package org.usfirst.frc.team135.robot.commands.auton.singles;

import org.usfirst.frc.team135.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetLiftPosition extends Command {

	private double position = 0.0;
	private boolean done = false;
    public SetLiftPosition(double position) {
    	requires(Robot.lift);
    	this.position = position;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (!done)
    	{
    		Robot.lift.setToPosition(this.position);
    		done = true;
    	}
    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.lift.stopMotor();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
