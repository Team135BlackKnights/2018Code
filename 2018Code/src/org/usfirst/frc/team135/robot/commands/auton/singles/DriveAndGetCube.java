package org.usfirst.frc.team135.robot.commands.auton.singles;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap.COMPETITION.CAMERA;
import org.usfirst.frc.team135.robot.RobotMap.PRACTICE;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class DriveAndGetCube extends TimedCommand {

	private double _angle;
	private boolean _done = false;
	
    public DriveAndGetCube(double timeout, int mode) {
        super(timeout);
        requires(Robot.drivetrain);
        requires(Robot.intake);
        
        Robot.camera.setTrackingMode(mode);
      
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	//System.out.println("HI");
    	Robot.intake.DriveWheels(1);   	
    	if (Robot.camera.isTargetVisible())
    	{
    		this._angle = Robot.camera.getXOffsetDegrees() + Robot.navx.getFusedAngle() + 180;
    	}
    	
    	if (Robot.ultrasonic.getRightSonarValue() < 6)
    	{
    		this._done = true;
    		return;
    	}
    	
    	double x = .5 * Math.cos(Math.toRadians(this._angle));
    	double y = .5 * Math.sin(Math.toRadians(this._angle));
    	
    	Robot.drivetrain.driveCartesian(x, y, 0);   	
    }

    protected boolean isFinished() 
    {
        return (DriverStation.getInstance().isOperatorControl() || this._done);
    }
    
    // Called once after timeout
    protected void end() 
    {
    	Robot.drivetrain.stopMotors();
    	Robot.intake.DriveWheels(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
    	this.end();
    }
}
