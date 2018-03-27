package org.usfirst.frc.team135.robot.commands.auton.singles;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap.CONVERSIONS;
import org.usfirst.frc.team135.robot.RobotMap.PRACTICE;
import org.usfirst.frc.team135.robot.util.FunctionalDoubleManager;
import org.usfirst.frc.team135.robot.util.PIDIn;
import org.usfirst.frc.team135.robot.util.PIDOut;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class DriveStraightYWithSonar extends TimedCommand {

	private PIDController _xController, _yController;
	private PIDIn _xIn, _yIn;
	private PIDOut _xOut, _yOut;
	
	
	 //This is important for determining when we should stop. The error changes sign when we pass our setpoint. This is vital for single sided PID.
	private double _initSign;
	
	private double _directionY;
	
	private boolean _initialized = false;
	
    public DriveStraightYWithSonar(FunctionalDoubleManager xSensor, double xSetpointInches, double ySetpointInches, double timeout) {
    	super(timeout);
    	requires(Robot.drivetrain);
    	
    	this._xIn = new PIDIn(xSensor, PIDSourceType.kDisplacement);
    	this._yIn = new PIDIn(() -> CONVERSIONS.TICKS2INCHES * Math.abs(Robot.drivetrain.getEncoderCounts(Robot.drivetrain.rearLeftTalon)), 
    							PIDSourceType.kDisplacement);
    	
    	this._xOut = new PIDOut();
    	this._yOut = new PIDOut();
    	
    	this._xController = new PIDController(0, 0, 0, this._xIn, this._xOut);
    	this._yController = new PIDController(0, 0, 0, this._yIn, this._yOut);
  
    	this._xController.setOutputRange(-.7, .7);
    	this._xController.setAbsoluteTolerance(2);
    	this._xController.setSetpoint(xSetpointInches);

    	
    	this._yController.setOutputRange(0, .7);
    	this._yController.setAbsoluteTolerance(8);
    	this._yController.setSetpoint(Math.abs(ySetpointInches));
    	this._directionY = Math.signum(ySetpointInches);
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
 	
    }
    
    private void betterInitialize()
    {
    	double kP_x, kI_x, kD_x;
    	double kP_y, kI_y, kD_y;
    	
    	this._xController.enable();
    	this._yController.enable();
    	
    	this._initSign = Math.signum(this._yController.getError());
    	
    	kP_x = 1 / this._xController.getError();
    	kI_x = kP_x / 100.0;
    	kD_x = kP_x * 10.0;
    	
    	kP_y = 1 / this._yController.getError();
    	kI_y = kP_y / 100.0;
    	kD_y = kP_y * 10.0;
    	
    	this._xController.setPID(kP_x, kI_x, kD_x);
    	this._yController.setPID(kP_y, kI_y, kD_y); 
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	if (!this._initialized)
    	{
    		this.betterInitialize();
    	}
    	Robot.drivetrain.driveCartesian(this._xOut.output, this._directionY * this._yOut.output, 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.signum(this._yController.getError()) != this._initSign; //When our encoder switches signs, we stop.
    }

    // Called once after isFinished returns true
    protected void end() 
    {
    	this._yController.disable();
    	this._xController.disable();
    	
    	Robot.drivetrain.stopMotors();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}
