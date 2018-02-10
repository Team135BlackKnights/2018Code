package org.usfirst.frc.team135.robot.commands.auton.groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.RobotMap.*;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveStraightDistance;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveStraightForwardDistance;
/**
 *
 */
public class ToAutoline extends CommandGroup implements RobotMap{

    public ToAutoline() {
    	System.out.println("RUN!");
    	addSequential(new DriveStraightForwardDistance(FIELD.AUTO_LINE));
    	System.out.println("Done!");
    }
}
