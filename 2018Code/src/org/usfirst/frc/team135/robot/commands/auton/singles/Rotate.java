package org.usfirst.frc.team135.robot.commands.auton.singles;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.util.PIDIn;
import org.usfirst.frc.team135.robot.util.PIDOut;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Rotate extends Command {

	private double setpoint;
	private boolean done = false;
	
	PIDController angleController;
	PIDOut buffer;
	PIDIn navx;
	
    public Rotate(double rotationZ) {
    	requires(Robot.drivetrain);
    	
    	setpoint = rotationZ;

    	buffer = new PIDOut();
    	navx = new PIDIn(() -> Robot.navx.getFusedAngle(), PIDSourceType.kDisplacement);
    	angleController = new PIDController(.02, .0002, .2, navx, buffer);
    	
    	
    	initAngleController();
    	

    }
    
    private void initAngleController()
    {
    	angleController.setAbsoluteTolerance(.2);
    	angleController.setOutputRange(-.4, .4);
    	angleController.setInputRange(0, 360);
    	angleController.setContinuous(true);
    	angleController.setSetpoint(setpoint);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	angleController.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Timer timer = new Timer();
    	timer.start();
    	while(Math.abs(angleController.getError()) < .2 && timer.get() < 1.5 && DriverStation.getInstance().isAutonomous())
    	{
    		Robot.drivetrain.driveCartesian(0, 0, buffer.output);
    	}
    	
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
