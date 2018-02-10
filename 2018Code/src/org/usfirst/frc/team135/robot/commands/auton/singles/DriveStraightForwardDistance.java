package org.usfirst.frc.team135.robot.commands.auton.singles;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.util.Lidar_wrapper;
import org.usfirst.frc.team135.robot.util.NavX_wrapper;
import org.usfirst.frc.team135.robot.util.PIDOut;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraightForwardDistance extends Command {

	private double distance;
	private PIDController yController, angleZController;
	private PIDOut bufY, bufRotationZ;
	private NavX_wrapper navx;
	
	private int pos_stability;
	private boolean done = false;
	
	Timer timer;
	
    public DriveStraightForwardDistance( double distance) {
    	this.distance = distance;
    	
    	navx = new NavX_wrapper(Robot.navx);
    	
    	bufRotationZ = new PIDOut();
    	
    	
    	angleZController = new PIDController(.03, .0003, 0, navx, bufRotationZ);
    	
    	angleZController.setInputRange(0, 360);
    	angleZController.setContinuous();
    	angleZController.setOutputRange(-.2, .2);
    	angleZController.setAbsoluteTolerance(.5);
    	angleZController.setSetpoint(0);
    	
    	
    	timer = new Timer();
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	angleZController.enable();
    	yController.enable();
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	if ((Robot.canifier.ReadLidarInches() < distance || Robot.canifier.ReadLidarInches() > distance + 30) && timer.get() < 3)
    	{
    		pos_stability = 0;
    		Robot.drivetrain.driveCartesian(0, -.5, bufRotationZ.output);
    	}
    	else
    	{
    		if(pos_stability < 5)
    		{
    			pos_stability++;
    			return;
    		}
    		else
    		{
        		timer.stop();
        		timer.reset();
        		done = true;
    		}

    	}
    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.stopMotors();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
