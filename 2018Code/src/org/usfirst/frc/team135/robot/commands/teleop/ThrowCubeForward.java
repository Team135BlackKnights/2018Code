package org.usfirst.frc.team135.robot.commands.teleop;

import org.usfirst.frc.team135.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ThrowCubeForward extends InstantCommand {

    public ThrowCubeForward() {
        super();
        requires(Robot.intake);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.intake.MoveMandibles(DoubleSolenoid.Value.kReverse);
    	Timer.delay(.5);
    	Robot.intake.MoveMandibles(DoubleSolenoid.Value.kForward);
    	Timer.delay(.5 / 4);
    	Robot.intake.ActivateClaw(DoubleSolenoid.Value.kForward);
    }

}
