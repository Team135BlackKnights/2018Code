package org.usfirst.frc.team135.robot.commands.auton.groups;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.util.PIDIn;
import org.usfirst.frc.team135.robot.util.PIDOut;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class PIDExampleCommand extends TimedCommand {

	private static final double 
		_kP = 1, 	// = 1/initial error - 2/initial error
		_kI = _kP / 100, 
		_kD = _kP * 10;
	
	
	
	private PIDIn _pidIn;
	private PIDOut _pidOut;
	
	private PIDController _controller;
	
    public PIDExampleCommand(double setpoint, double timeout) 
    {
        super(timeout);
        //output = kP * error + kI * avg_error + kD * delta_error
        
        this._pidIn = new PIDIn(() -> Robot.navx.getFusedAngle(), PIDSourceType.kDisplacement);
        this._pidOut = new PIDOut();
        
        this._controller = new PIDController(_kP, _kI, _kD, this._pidIn, this._pidOut);
        this._controller.setInputRange(0, 360);
        this._controller.setContinuous();
        this._controller.setOutputRange(-1, 1);
        this._controller.setAbsoluteTolerance(.5);
        
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	this._controller.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.driveCartesian(0, 0, this._pidOut.output);
    }

    // Called once after timeout
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
