package org.usfirst.frc.team135.robot.commands.auton.singles;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.util.*;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraightDistance extends Command {

	private double distance, drivingAngle, rotationalAngle;
	private PIDController xController, yController, angleController;
	private PIDOut bufX, bufY, bufRotationZ;
	private NavX_wrapper navx;
	private Lidar_wrapper lidar;
	
	
	private boolean isDone = false;
	
	private double xMultiplier, yMultiplier;
	
    public DriveStraightDistance(double distance, double drivingAngle, double rotationalAngle) {
    	requires(Robot.drivetrain);
    	
    	this.distance = distance;
    	this.drivingAngle = drivingAngle;
    	this.rotationalAngle = rotationalAngle;
    	
    	//initBuffers();
    	
    	navx = new NavX_wrapper(Robot.navx);
    	lidar = new Lidar_wrapper(Robot.canifier);
    	
    	bufSpeed = new PIDOut();
    	bufRotationZ = new PIDOut();
    	
    	xController = new PIDController(1.0, .01, 10, lidar, bufX);
    	yController = new PIDController(1.0, .01, 10, Robot.ultrasonic.rightSonar, bufY);
    	
    	angleController = new PIDController(.05, .0005, .5, navx, bufRotationZ);
    	
    	xMultiplier = Math.cos(Math.toRadians(drivingAngle));
    	yMultiplier = Math.sin(Math.toRadians(drivingAngle));
    	
    //	distanceController = new PIDController(1.0, .01, 10, )

    	
    	
    }
    

    // Called just before this Command runs the first time
    protected void initialize()
    {
    	distanceController.enable();
    	angleController.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	
    	Robot.drivetrain.driveCartesian(bufSpeed.output * xMultiplier, bufSpeed.output * yMultiplier, bufRotationZ.output);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return ();
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
