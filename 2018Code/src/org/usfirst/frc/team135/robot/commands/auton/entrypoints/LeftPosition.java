package org.usfirst.frc.team135.robot.commands.auton.entrypoints;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftPosition extends CommandGroup {

    public LeftPosition() 
    {
    	System.out.println(DriverStation.getInstance().getGameSpecificMessage());
    }
}
