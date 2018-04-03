package org.usfirst.frc.team135.robot.commands.auton.singles;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.subsystems.NavX;
import org.usfirst.frc.team135.robot.util.PIDIn;
import org.usfirst.frc.team135.robot.util.PIDOut;
import org.usfirst.frc.team135.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.TimedCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TurnToAngle extends TimedCommand {
	
	private double _initSignError = 0;
	

	private PIDController angleController;
	private PIDIn pidIn;
	private PIDOut pidOut;
	
    public TurnToAngle(double angle, double timeout) {
    	super(timeout);
    	requires(Robot.drivetrain);
    	
    	pidIn = new PIDIn(() -> Robot.navx.getFusedAngle(), PIDSourceType.kDisplacement);
    	pidOut = new PIDOut();
    	
    	angleController = new PIDController(1/90.0, 0, 0, pidIn, pidOut);
    	
    	angleController.setInputRange(0, 360);
    	angleController.setContinuous();
    	angleController.setOutputRange(-1, 1);
    	
    	angleController.setSetpoint(angle);
    	
    	this._initSignError = Math.signum(angleController.getError());

    }

    // Called once when the command executes
    protected void initialize() {
    	angleController.enable();
    	/*
    	SmartDashboard.putNumber("NavX: ", Robot.navx.getYawBasedAngle());
    	System.out.println(Robot.navx.getYawBasedAngle());
    	Timer timer = new Timer();
    	timer.start();
    	
		if (this._angle > 0) {
			while (Robot.navx.getYawBasedAngle() < this._angle && timer.get() < this._timeout && DriverStation.getInstance().isAutonomous()) {
				Robot.drivetrain.driveCartesian(0, 0, 1);
			}
		}

		if (this._angle < 0) {
			while (Robot.navx.getYawBasedAngle() > this._angle && timer.get() < this._timeout && DriverStation.getInstance().isAutonomous()) {
				Robot.drivetrain.driveCartesian(0, 0, -1);
			}
		}
		*/
    }
    
    protected void execute()
    {
    	Robot.drivetrain.driveCartesian(0, 0, pidOut.output);
    }
    
    protected boolean isFinished()
    {
    	return this._initSignError != Math.signum(angleController.getError()) || !DriverStation.getInstance().isAutonomous();
    }
    
    protected void interupted()
    {
    	Robot.drivetrain.stopMotors();
    }
    
    protected void end()
    {
    	Robot.drivetrain.stopMotors();
    }

}
