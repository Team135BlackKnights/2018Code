package org.usfirst.frc.team135.robot.commands.auton.singles;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.util.NavX_wrapper;
import org.usfirst.frc.team135.robot.util.PIDOut;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Rotate extends Command {

	private double rotationZ;
	private boolean done = false;
	/*
	PIDController angleController;
	PIDOut buffer;
	NavX_wrapper navx;
	*/
	
    public Rotate(double rotationZ) {
    	requires(Robot.drivetrain);
    	/*
    	setpoint = angle;

    	buffer = new PIDOut();
    	navx = new NavX_wrapper(Robot.navx);
    	angleController = new PIDController(.005, .00005, .05, navx, buffer);
    	*/
    	
    	//initAngleController();

    }
    
    /*
    private void initAngleController()
    {
    	angleController.setAbsoluteTolerance(.2);
    	angleController.setOutputRange(-.4, .4);
    	angleController.setInputRange(0, 360);
    	angleController.setContinuous(true);
    	angleController.setSetpoint(setpoint);
    }
    */
    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.driveCartesian(0, 0, rotationZ);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.driveCartesian(0, 0, buffer.output);
    	done = true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetrain.stopMotors();
    }
}
