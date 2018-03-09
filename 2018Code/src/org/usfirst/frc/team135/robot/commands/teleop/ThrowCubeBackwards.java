package org.usfirst.frc.team135.robot.commands.teleop;

import org.usfirst.frc.team135.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ThrowCubeBackwards extends InstantCommand {

    public ThrowCubeBackwards() {
        super();
        requires(Robot.intake);
    }

    // Called once when the command executes
    protected void initialize() {
    	if (Robot.lift.getEncoderPosition() > 1200)
    	{
        	Robot.intake.MoveMandibles(DoubleSolenoid.Value.kForward);
        	Timer.delay(.3);
        	Robot.intake.MoveMandibles(DoubleSolenoid.Value.kReverse);
        	//Timer.delay(.01);
        	
        	//Robot.intake.ActivateClaw(DoubleSolenoid.Value.kReverse);
    	}

    }

}
