package org.usfirst.frc.team135.robot.commands.auton.singles;

import java.util.Arrays;
import java.util.Collections;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.util.FunctionalDoubleManager;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class DriveStraightForward extends InstantCommand implements RobotMap{
	private static final int FORWARD = 1, BACKWARD = -1;
	private static final double DRIVE_POWER = .6;
	
	private FunctionalDoubleManager _rangedSensor, _encoder;
	private Mode _driveMode;
	private double _targetDisplacement;
	
	private boolean _isFacingBackwards;
	
	private enum Mode
	{
		RANGED_SENSOR,
		ENCODER,
		FUSED
	}
	
    public DriveStraightForward(double targetDistance, FunctionalDoubleManager rangedSensor, boolean isFacingBackwards) {
        super();
	    requires(Robot.drivetrain);
	    
	    this._targetDisplacement = targetDistance;
	    this._rangedSensor = rangedSensor;
	    
	    this._driveMode = Mode.RANGED_SENSOR;
	    
	    this._isFacingBackwards = isFacingBackwards;
	    
    }
    
    public DriveStraightForward(double targetDistance, FunctionalDoubleManager encoder, FunctionalDoubleManager rangedSensor, boolean isFacingBackwards) {
        super();
	    requires(Robot.drivetrain);
	    
	    this._targetDisplacement = targetDistance;
	    this._rangedSensor = rangedSensor;
	    this._encoder = encoder;
	    
	    this._driveMode = Mode.FUSED;
	    
	    this._isFacingBackwards = isFacingBackwards;
	    
	    
    }
    
    public DriveStraightForward(double targetDisplacement, boolean isFacingBackwards) {
        super();
	    requires(Robot.drivetrain);
	    
	    this._targetDisplacement = targetDisplacement;
	    
	    this._encoder = () -> CONVERSIONS.TICKS2INCHES * Robot.drivetrain.getEncoderCounts(Robot.drivetrain.rearLeftTalon);
	    this._driveMode = Mode.ENCODER;
	    
	    this._isFacingBackwards = isFacingBackwards;
	    
    }
    
    // Called once when the command executes
    protected void initialize() 
    {
    	int direction = 0;
    	if (this._targetDisplacement != 0)
    	{
    		direction = (this._targetDisplacement > 0) ? 1 : -1;
    	}
    	else
    	{
    		System.out.println("Done driving straight... 0?");
    		return;
    	}
    	
    	if (this._isFacingBackwards)
    	{
    		direction *= -1;
    	}
    	
    	if (this._driveMode == Mode.RANGED_SENSOR)
    	{

    		if (direction == DriveStraightForward.FORWARD)
    		{
				while (this._rangedSensor.get() < this._targetDisplacement) {

					Robot.drivetrain.driveTank(DriveStraightForward.DRIVE_POWER * direction, DriveStraightForward.DRIVE_POWER * direction);

				}
    		}
    		else if (direction == DriveStraightForward.BACKWARD)
    		{
				while (this._rangedSensor.get() > this._targetDisplacement) {

					Robot.drivetrain.driveTank(DriveStraightForward.DRIVE_POWER * direction, DriveStraightForward.DRIVE_POWER * direction);

				}
    		}
    	   
    	}
    	else if (this._driveMode == Mode.ENCODER)
    	{
    		if (direction == DriveStraightForward.FORWARD)
    		{
        	    while(this._encoder.get() < this._targetDisplacement) {
        		    
        	    	Robot.drivetrain.driveTank(DriveStraightForward.DRIVE_POWER * direction, DriveStraightForward.DRIVE_POWER * direction);
        	    	
        	    }
    		}
    		else if (direction == DriveStraightForward.BACKWARD)
    		{
        	    while(this._encoder.get() > this._targetDisplacement) {
        		    
        	    	Robot.drivetrain.driveTank(DriveStraightForward.DRIVE_POWER * direction, DriveStraightForward.DRIVE_POWER * direction);
        	    	
        	    }
    		}

    	}
    	else if (this._driveMode == Mode.FUSED)
    	{

			if (direction == DriveStraightForward.FORWARD) 
			{
				while(true)
				{
		    		double fusedSensorVal = Collections.min(Arrays.asList(new Double[] {this._encoder.get(), this._rangedSensor.get()}));

		    		if (fusedSensorVal < this._targetDisplacement)
		    		{
		    			break;
		    		}
		    		
        	    	Robot.drivetrain.driveTank(DriveStraightForward.DRIVE_POWER * direction, DriveStraightForward.DRIVE_POWER * direction);
		    		
				}
				
			} 
			else if (direction == DriveStraightForward.BACKWARD) 
			{
				while(true)
				{
		    		double fusedSensorVal = Collections.min(Arrays.asList(new Double[] {this._encoder.get(), this._rangedSensor.get()}));

		    		if (fusedSensorVal > this._targetDisplacement)
		    		{
		    			break;
		    		}
		    		
        	    	Robot.drivetrain.driveTank(DriveStraightForward.DRIVE_POWER * direction, DriveStraightForward.DRIVE_POWER * direction);
				}
			}

    		
    	}
    	
    	
    }

}
