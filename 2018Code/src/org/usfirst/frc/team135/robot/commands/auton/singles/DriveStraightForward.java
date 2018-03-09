package org.usfirst.frc.team135.robot.commands.auton.singles;

import java.util.Arrays;
import java.util.Collections;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.util.FunctionalDoubleManager;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class DriveStraightForward extends InstantCommand implements RobotMap{
	private static final int FORWARD = 1, BACKWARD = -1;
	private static final double DRIVE_POWER = .7;
	
	private FunctionalDoubleManager _rangedSensor, _encoder;
	private Mode _driveMode;
	private double _targetDisplacement;
	
	private boolean _isFacingBackwards;

	private double _timeout;
	
	private enum Mode
	{
		RANGED_SENSOR,
		ENCODER,
		FUSED
	}
	
    public DriveStraightForward(double targetDistance, FunctionalDoubleManager rangedSensor, boolean isFacingBackwards, double timeout) {
        super();
	    requires(Robot.drivetrain);
	    	    
    	Robot.drivetrain.ResetEncoders();
	    this._targetDisplacement = targetDistance;
	    this._rangedSensor = rangedSensor;
	    
	    this._driveMode = Mode.RANGED_SENSOR;
	    
	    this._isFacingBackwards = isFacingBackwards;
	    
	    this._timeout = timeout;
	   
	    
    }
    
    public DriveStraightForward(double targetDistance, FunctionalDoubleManager encoder, FunctionalDoubleManager rangedSensor, boolean isFacingBackwards, double timeout) {
        super();
	    requires(Robot.drivetrain);
	    
    	Robot.drivetrain.ResetEncoders();
	    this._targetDisplacement = targetDistance;
	    this._rangedSensor = rangedSensor;
	    this._encoder = encoder;
	    
	    this._driveMode = Mode.FUSED;
	    
	    this._isFacingBackwards = isFacingBackwards;
	    
	    this._timeout = timeout;
	    
	    
    }
    
    public DriveStraightForward(double targetDisplacement, boolean isFacingBackwards, double timeout) {
        super();
	    requires(Robot.drivetrain);

    	Robot.drivetrain.ResetEncoders();
	    this._targetDisplacement = targetDisplacement;
	    
	    this._encoder = () -> CONVERSIONS.TICKS2INCHES * Robot.drivetrain.getEncoderCounts(Robot.drivetrain.rearLeftTalon);
	    this._driveMode = Mode.ENCODER;
	    
	    this._isFacingBackwards = isFacingBackwards;
	    
	    this._timeout = timeout;
	    
    }
    
    // Called once when the command executes
    protected void initialize() 
    {
    	Timer timer = new Timer();
    	
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
    	
    	timer.start();
    	if (this._driveMode == Mode.RANGED_SENSOR)
    	{

    		if (direction == DriveStraightForward.FORWARD)
    		{
				while (this._rangedSensor.get() < this._targetDisplacement && timer.get() < this._timeout && DriverStation.getInstance().isAutonomous()) {

					Robot.drivetrain.driveTank(DriveStraightForward.DRIVE_POWER * direction, DriveStraightForward.DRIVE_POWER * direction);

				}
    		}
    		else if (direction == DriveStraightForward.BACKWARD)
    		{
				while (this._rangedSensor.get() > this._targetDisplacement && timer.get() < this._timeout && DriverStation.getInstance().isAutonomous()) {

					Robot.drivetrain.driveTank(DriveStraightForward.DRIVE_POWER * direction, DriveStraightForward.DRIVE_POWER * direction);

				}
    		}
    	   
    	}
    	else if (this._driveMode == Mode.ENCODER)
    	{
    		if (direction == DriveStraightForward.FORWARD)
    		{
    			System.out.println("Encoder got: " + this._encoder.get());
        	    while(this._encoder.get() < this._targetDisplacement && timer.get() < this._timeout && DriverStation.getInstance().isAutonomous()) {
        	    	Robot.drivetrain.driveTank(DriveStraightForward.DRIVE_POWER * direction, DriveStraightForward.DRIVE_POWER * direction);
        	    	
        	    }
    		}
    		else if (direction == DriveStraightForward.BACKWARD)
    		{

        	    while(this._encoder.get() > this._targetDisplacement && timer.get() < this._timeout && DriverStation.getInstance().isAutonomous()) {	
        			System.out.println("Encoder got: " + this._encoder.get());
        			System.out.println("Target Displacement: " + this._targetDisplacement);
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

		    		if (fusedSensorVal > this._targetDisplacement || timer.get() > this._timeout || !DriverStation.getInstance().isAutonomous())
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

		    		if (fusedSensorVal < this._targetDisplacement || timer.get() > this._timeout || !DriverStation.getInstance().isAutonomous())
		    		{
		    			break;
		    		}
		    		
		    		Robot.drivetrain.driveTank(DriveStraightForward.DRIVE_POWER * direction, DriveStraightForward.DRIVE_POWER * direction);
				}
			}

    		
    	}
    	
    
    	Robot.drivetrain.stopMotors();
    }

}
