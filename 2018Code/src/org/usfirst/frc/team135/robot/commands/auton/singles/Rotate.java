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

	private double rotationalAngle;
	private PIDController angleZController;
	NavX_wrapper navx;
	private PIDOut bufRotationZ;
	

	
    public Rotate(double rotationalAngle) {
    	requires(Robot.drivetrain);
    	this.rotationalAngle = rotationalAngle;

    	bufRotationZ = new PIDOut();
    	navx = new NavX_wrapper(Robot.navx);
    	angleZController = new PIDController(.005, .00005, .05, navx, bufRotationZ);
    	
    	angleZController.setInputRange(0, 360);
    	angleZController.setContinuous();
    	angleZController.setOutputRange(-.2, .2);
    	angleZController.setAbsoluteTolerance(.5);
    	
    	
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
    	angleZController.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	while(angleZController.getError() > .5)
    	{
    		Robot.drivetrain.driveCartesian(0, 0, bufRotationZ.output);
    	}

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
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
