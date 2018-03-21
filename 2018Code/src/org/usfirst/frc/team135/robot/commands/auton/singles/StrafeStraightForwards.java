package org.usfirst.frc.team135.robot.commands.auton.singles;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.util.FunctionalDoubleManager;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class StrafeStraightForwards extends InstantCommand {

	private FunctionalDoubleManager _rangedSensor;
	private double _targetDistance;
	
	private static final int FORWARD = 1, BACKWARD = -1;
	
	private static final double DRIVE_POWER = .6;
	
	private double _timeout;
	
	private int _direction = 1;
	
    public StrafeStraightForwards(double targetDistance, int direction, boolean facingBackwards, FunctionalDoubleManager rangedSensor, double timeout) {
        super();
        requires(Robot.drivetrain);
        
        this._rangedSensor = rangedSensor;
        this._targetDistance = targetDistance;
        
        this._direction = direction;
        
        /*if (facingBackwards)
        {
        	this._direction *= -1;
        }*/
        
        this._timeout = timeout;
        
        
    }

    // Called once when the command executes
    protected void initialize() 
    {
    	
    	/*
    	if (this._targetDistance != 0)
    	{
    		direction = (this._targetDistance < this._rangedSensor.get()) ? StrafeStraightSideways.RIGHT : StrafeStraightSideways.LEFT;
    	}
    	else
    	{
    		System.out.println("Done strafing... 0?");
    		return;
    	}
    	*/
    	
    	Timer timer = new Timer();
    	
    	timer.start();
    	
    	if (this._direction == StrafeStraightForwards.FORWARD)
    	{
    		while (this._rangedSensor.get() > this._targetDistance && DriverStation.getInstance().isAutonomous() && timer.get() < this._timeout)
    		{
        		Robot.drivetrain.driveCartesian(0, this._direction * StrafeStraightForwards.DRIVE_POWER, 0);
    		}
    	}
    	else if (this._direction == StrafeStraightForwards.BACKWARD)
    	{
    		while (this._rangedSensor.get() > this._targetDistance && DriverStation.getInstance().isAutonomous()  && timer.get() < this._timeout)
    		{
        		Robot.drivetrain.driveCartesian(0, this._direction * StrafeStraightForwards.DRIVE_POWER, 0);
    		}
    	}
    	
    	Robot.drivetrain.stopMotors();
    		
    }

}
