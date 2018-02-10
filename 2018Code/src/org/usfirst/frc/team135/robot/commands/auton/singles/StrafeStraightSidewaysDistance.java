package org.usfirst.frc.team135.robot.commands.auton.singles;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap.FIELD;
import org.usfirst.frc.team135.robot.util.*;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StrafeStraightSidewaysDistance extends Command {

	private double distance, drivingAngle, rotationalAngle;
	public Ultrasonic sonar;
	private PIDController angleZController;
	private PIDOut bufRotationZ;
	private NavX_wrapper navx;
	private	Timer timer = new Timer();
	private int pos_stability = 0;
	private boolean done = false;

	
    public StrafeStraightSidewaysDistance(double distance, boolean moveRight) {
    	requires(Robot.drivetrain);
    	
    	this.distance = distance;
    	this.drivingAngle = drivingAngle;
    	this.rotationalAngle = rotationalAngle;
    	
    	//initBuffers();
    	
    	navx = new NavX_wrapper(Robot.navx);

    	
    	bufRotationZ = new PIDOut();
    	
    	angleZController = new PIDController(.05, .0005, .5, navx, bufRotationZ);
    	
    	angleZController.setInputRange(0, 360);
    	angleZController.setContinuous();
    	angleZController.setOutputRange(-.2, .2);
    	angleZController.setAbsoluteTolerance(.5);
    	
    	sonar = (moveRight) ? Robot.ultrasonic.rightSonar : Robot.ultrasonic.leftSonar;


    }
    

    // Called just before this Command runs the first time
    protected void initialize()
    {
    	angleZController.enable();
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
 
    	  		
    	if((sonar.getRangeInches() < FIELD.SIDE_SWITCH_X || sonar.getRangeInches() < FIELD.SIDE_SWITCH_X + 30) && timer.get() < 3)
    	{
    		pos_stability = 0;
    		Robot.drivetrain.driveCartesian(.5, 0, bufRotationZ.output);
    	}
    	else
    	{
    		if (pos_stability < 5)
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
