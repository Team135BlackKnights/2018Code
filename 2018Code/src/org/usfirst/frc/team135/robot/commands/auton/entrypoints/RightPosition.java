package org.usfirst.frc.team135.robot.commands.auton.entrypoints;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightPosition extends CommandGroup {

    public RightPosition() {
    	System.out.println(DriverStation.getInstance().getGameSpecificMessage());
    }
}
