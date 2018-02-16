package org.usfirst.frc.team135.robot.commands.auton.entrypoints;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class MiddlePosition extends CommandGroup {

    public MiddlePosition() {
    	if (SmartDashboard.getBoolean("Try to go for Switch?", true) && !SmartDashboard.getBoolean("Try to go for Scale?", false))
    	{
    		
    	}
    	else
    	{
    	
    	}
    }
}
