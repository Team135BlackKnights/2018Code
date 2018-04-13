package org.usfirst.frc.team135.robot.commands.auton.singles;

import org.usfirst.frc.team135.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class SetLiftPosition extends InstantCommand {

	private double _position;
    public SetLiftPosition(double position) {
        super();
        requires(Robot.lift);
        
        this._position = position;
    }

    // Called once when the command executes
    protected void initialize() 
    {
    	Robot.lift.setToPosition(this._position);
    	Robot.lift.holdVelocityAtZero();
    }

}
