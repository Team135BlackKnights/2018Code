package org.usfirst.frc.team135.robot.commands.auton.singles;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.util.FunctionalDoubleManager;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class StrafeStraightSideways extends InstantCommand {

	private FunctionalDoubleManager _rangedSensor;
	private double _targetDisplacement;
	
	private static final int RIGHT = 1, LEFT = -1;
	
	private static final double DRIVE_POWER = .6;
	
    public StrafeStraightSideways(double targetDisplacment, FunctionalDoubleManager rangedSensor) {
        super();
        requires(Robot.drivetrain);
        
        this._rangedSensor = rangedSensor;
        this._targetDisplacement = targetDisplacment;
    }

    // Called once when the command executes
    protected void initialize() 
    {
    	int direction = 0;
    	
    	if (this._targetDisplacement != 0)
    	{
    		direction = (this._targetDisplacement > 0) ? StrafeStraightSideways.RIGHT : StrafeStraightSideways.LEFT;
    	}
    	else
    	{
    		System.out.println("Done strafing... 0?");
    		return;
    	}
    	
    	if (direction == StrafeStraightSideways.RIGHT)
    	{
    		while (this._rangedSensor.get() < this._targetDisplacement)
    		{
        		Robot.drivetrain.driveCartesian(direction * StrafeStraightSideways.DRIVE_POWER, 0, 0);
    		}
    	}
    	else if (direction == StrafeStraightSideways.LEFT)
    	{
    		while (this._rangedSensor.get() > this._targetDisplacement)
    		{
        		Robot.drivetrain.driveCartesian(direction * StrafeStraightSideways.DRIVE_POWER, 0, 0);
    		}
    	}
    		
    }

}
