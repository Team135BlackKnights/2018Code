package org.usfirst.frc.team135.robot.commands.teleop;

import org.usfirst.frc.team135.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ResetNavX extends InstantCommand {

    public ResetNavX() {
        super();
<<<<<<< HEAD
    }

    // Called once when the command executes
    protected void initialize() {
    	
=======

    }

    // Called once when the command executes
    protected void initialize() 
    {
    	if (Robot.oi.GetNavXResetButton())
    	{
    		Robot.navx.initAngle = -Robot.navx.getFusedAngle();
    	}
>>>>>>> branch 'AutoTestingV1.11' of https://github.com/Team135BlackKnights/2018Code.git
    }

}
