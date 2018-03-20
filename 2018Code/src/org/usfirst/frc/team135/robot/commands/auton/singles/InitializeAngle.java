package org.usfirst.frc.team135.robot.commands.auton.singles;

import org.usfirst.frc.team135.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class InitializeAngle extends InstantCommand {

	double angle;
    public InitializeAngle(double angle) {
        super();
        this.angle = angle;
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.navx.reset();
    	Robot.navx.initAngle = angle;
    }

}
