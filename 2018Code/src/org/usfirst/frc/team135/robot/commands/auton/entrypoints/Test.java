package org.usfirst.frc.team135.robot.commands.auton.entrypoints;

import org.usfirst.frc.team135.robot.commands.auton.groups.MidToSwitch;
import org.usfirst.frc.team135.robot.commands.auton.groups.SideToAutoline;
import org.usfirst.frc.team135.robot.commands.auton.groups.SideToNearScale;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveAndGetCube;
import org.usfirst.frc.team135.robot.commands.teleop.ResetNavX;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Test extends CommandGroup {

    public Test() {
    	addSequential(new MidToSwitch(false));
    }
}
