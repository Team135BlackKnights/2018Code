package org.usfirst.frc.team135.robot.commands.auton.singles;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.util.*;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraightDistance extends Command {

	private double distance, drivingAngle, rotationalAngle;
	private PIDController xController, yController, angleZController;
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
    	
    	bufX = new PIDOut();
    	bufY = new PIDOut();
    	bufRotationZ = new PIDOut();
    	
    	xController = new PIDController(1.0, .01, 10, Robot.ultrasonic.rightSonar, bufX);
    	yController = new PIDController(1.0, .01, 10, Robot.ultrasonic.backSonar, bufY);
    	
    	angleZController = new PIDController(.05, .0005, .5, navx, bufRotationZ);
    	
    	angleZController.setInputRange(0, 360);
    	angleZController.setContinuous();
    	angleZController.setOutputRange(-.2, .2);
    	angleZController.setAbsoluteTolerance(1);
    	
    	xController.setAbsoluteTolerance(2.0);
    	xController.setOutputRange(-1, 1);
    	
    	yController.setAbsoluteTolerance(2.0);
    	yController.setOutputRange(-1, 1);
    	
    }
    

    // Called just before this Command runs the first time
    protected void initialize()
    {
    	xController.enable();
    	yController.enable();
    	angleZController.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	Timer timer = new Timer();
    	timer.start();
    	
    	while (DriverStation.getInstance().isAutonomous() && timer.get() < 8)
    	{
    		
    		if(xController.getError() < 2 && yController.getError() < 2 && angleZController.getError() < 2)
    		{
    			Robot.drivetrain.driveCartesian(bufX.output, bufY.output, bufRotationZ.output);
    		}
    		else
    		{
    			break;
    		}
    	}
    	
    	timer.stop();
    	timer.reset();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (xController.getError() < 2 && yController.getError() < 2 && angleZController.getError() < 2);
    }

    // Called once after isFinished returns true
    protected void end() 
    {
    	Robot.drivetrain.stopMotors();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
    	end();
    }
}
