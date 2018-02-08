package org.usfirst.frc.team135.robot.commands.auton.singles;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.util.NavX_wrapper;
import org.usfirst.frc.team135.robot.util.PIDOut;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraightDistance extends Command {

	private double distance, drivingAngle, rotationalAngle;
	private PIDController distanceController, angleController;
	private PIDOut bufSpeed, bufRotationZ;
	private NavX_wrapper navx;
	
	private boolean isDone = false;
	
    public DriveStraightDistance(double distance, double drivingAngle, double rotationalAngle) {
    	requires(Robot.drivetrain);
    	
    	this.distance = distance;
    	this.drivingAngle = drivingAngle;
    	this.rotationalAngle = rotationalAngle;
    	
    	//initBuffers();
    	
    	navx = new NavX_wrapper(Robot.navx);
    	
    	bufSpeed= new PIDOut();
    	bufRotationZ = new PIDOut();
    	
    //	distanceController = new PIDController(1.0, .01, 10, )

    	
    	
    }
    

    // Called just before this Command runs the first time
    protected void initialize()
    {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	
    	Robot.drivetrain.driveCartesian(x, y, rotationZ);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isDone;
    }

    // Called once after isFinished returns true
    protected void end() 
    {
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
    	Robot.drivetrain.stopMotors();
    }
}
