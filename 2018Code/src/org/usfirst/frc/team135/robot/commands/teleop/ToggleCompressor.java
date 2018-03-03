package org.usfirst.frc.team135.robot.commands.teleop;

import org.usfirst.frc.team135.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ToggleCompressor extends InstantCommand {

    public ToggleCompressor() {
        super();
        
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.intake.ToggleCompressor();
    }

}
