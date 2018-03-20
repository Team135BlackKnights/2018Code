package org.usfirst.frc.team135.robot.commands.auton.singles;

import org.usfirst.frc.team135.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class AutoDriveMandibleWheelsOut extends InstantCommand {

	private double _timeout;
    public AutoDriveMandibleWheelsOut(double timeout) {
        super();
        requires(Robot.intake);
        
        this._timeout = timeout;
    }

    // Called once when the command executes
    protected void initialize() 
    {
    	Timer timer = new Timer();
    	timer.start();
    	while(timer.get() < this._timeout)
    	{
    		Robot.intake.DriveWheels(-1.0);
    	}
    }

}
