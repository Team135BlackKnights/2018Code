package org.usfirst.frc.team135.robot.commands.teleop;

import org.usfirst.frc.team135.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ExtendMandibles extends InstantCommand {

    public ExtendMandibles() {
        super();
        // Use requires() here to declare subsystem dependencies
        requires(Robot.intake);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.intake.MoveMandibles(DoubleSolenoid.Value.kForward);
    }

}
