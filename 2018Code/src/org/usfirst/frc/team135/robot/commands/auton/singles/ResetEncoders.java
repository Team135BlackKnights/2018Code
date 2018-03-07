package org.usfirst.frc.team135.robot.commands.auton.singles;

import org.usfirst.frc.team135.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ResetEncoders extends InstantCommand {

    public ResetEncoders() {
        super();

    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.drivetrain.ResetEncoders();
    }

}
