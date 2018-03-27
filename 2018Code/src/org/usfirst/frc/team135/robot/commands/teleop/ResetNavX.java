package org.usfirst.frc.team135.robot.commands.teleop;

import org.usfirst.frc.team135.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ResetNavX extends InstantCommand {

    public ResetNavX() {
        super();

    }

    // Called once when the command executes
    protected void initialize() 
    {
    	if (Robot.oi.GetNavXResetButton())
    	{
    		Robot.navx.initAngle = 0;
    		Robot.navx.getNewInstance();
    	}

    }

}
