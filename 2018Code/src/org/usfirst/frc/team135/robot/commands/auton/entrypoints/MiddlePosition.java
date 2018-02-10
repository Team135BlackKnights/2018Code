package org.usfirst.frc.team135.robot.commands.auton.entrypoints;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MiddlePosition extends CommandGroup {

    public MiddlePosition() {
    	System.out.println(DriverStation.getInstance().getGameSpecificMessage())
    }
}
