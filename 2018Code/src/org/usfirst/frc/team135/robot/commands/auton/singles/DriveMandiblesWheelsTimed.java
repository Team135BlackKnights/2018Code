package org.usfirst.frc.team135.robot.commands.auton.singles;

import org.usfirst.frc.team135.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class DriveMandiblesWheelsTimed extends TimedCommand {

	private double _power;
	
	private static final double STOP = 0;
	
    public DriveMandiblesWheelsTimed(double timeout, double power) {
        super(timeout);
        requires(Robot.intake);
        
        this._power = power;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	Robot.intake.DriveWheels(this._power);
    }

    // Called once after timeout
    protected void end() 
    {
    	Robot.intake.DriveWheels(STOP);
    }
    
    protected boolean isFinished()
    {
    	return Robot.ultrasonic.isCubeInMandibles();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
    	this.end();
    }
}
