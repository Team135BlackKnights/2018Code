package org.usfirst.frc.team135.robot.commands.auton.singles;

import java.io.File;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.RobotMap.COMPETITION.DRIVETRAIN;
import org.usfirst.frc.team135.robot.util.PIDIn;
import org.usfirst.frc.team135.robot.util.PIDOut;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;
import jaci.pathfinder.*;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;
/**
 *
 */
public class DriveAlongProfile extends TimedCommand implements RobotMap {

	private Trajectory _leftTrajectory, _rightTrajectory;
	private EncoderFollower _leftEncoderFollower, _rightEncoderFollower;

	private static final double
		_MAX_VELOCITY = DRIVETRAIN.MAX_VELOCITY_TICKS * CONVERSIONS.TICKS2FEET,
		_kV = 1 / _MAX_VELOCITY,
		_MAX_ACCELERATION = DRIVETRAIN.MAX_ACCELERATION_TICKS * CONVERSIONS.TICKS2FEET,
		_kA = 1 / _MAX_ACCELERATION;
	
	private static final double 
		_rightDrivekP = Robot.drivetrain.RearRightkP,
		_rightDrivekI = 0,
		_rightDrivekD = Robot.drivetrain.RearRightkD;
	
	private static final double 
		_leftDrivekP = Robot.drivetrain.RearLeftkP,
		_leftDrivekI = 0,
		_leftDrivekD = Robot.drivetrain.RearLeftkD;
	
	private PIDController _angleController;
	private PIDOut _buffer;
	private PIDIn _pidSource;
	
	private static final double 
		_turnkP =  1/90.0,
		_turnkI = 0.0,
		_turnkD = 2 * (Math.sqrt(_turnkP));
	
	private boolean _done = false;
	
    public DriveAlongProfile(String filename, double timeout) {
    	super(timeout);
    	requires(Robot.drivetrain);		

    	File fileLeftTrajectory, fileRightTrajectory;
    	fileLeftTrajectory = new File("~/lvuser/Motion Profiles 2018/" + filename + "/" + filename + "_left_detailed");
    	fileRightTrajectory = new File("~/lvuser/Motion Profiles 2018/" + filename + "/" + filename + "_right_detailed");
    	
    	this._leftTrajectory = Pathfinder.readFromCSV(fileLeftTrajectory);
    	this._rightTrajectory = Pathfinder.readFromCSV(fileRightTrajectory);
    	
		this._leftEncoderFollower = new EncoderFollower(this._leftTrajectory);
		this._rightEncoderFollower = new EncoderFollower(this._rightTrajectory);
		

    }

    // Called just before this Command runs the first time
    protected void initialize() {    	
    	
    	this._leftEncoderFollower.configureEncoder((int)Robot.drivetrain.getEncoderCounts(Robot.drivetrain.rearLeftTalon), 
													(int)CONVERSIONS.REVS2TICKS, 
													DRIVETRAIN.WHEEL_DIAMETER * CONVERSIONS.INCHES2FEET);  
    	
    	this._rightEncoderFollower.configureEncoder((int)Robot.drivetrain.getEncoderCounts(Robot.drivetrain.rearRightTalon), 
													(int)CONVERSIONS.REVS2TICKS, 
													DRIVETRAIN.WHEEL_DIAMETER * CONVERSIONS.INCHES2FEET);
    	
    	this._leftEncoderFollower.configurePIDVA(DriveAlongProfile._leftDrivekP,
													DriveAlongProfile._leftDrivekI, 
													DriveAlongProfile._leftDrivekD, 
													DriveAlongProfile._kV, 
													DriveAlongProfile._kA);
    	
    	this._rightEncoderFollower.configurePIDVA(DriveAlongProfile._rightDrivekP, 
													DriveAlongProfile._rightDrivekI, 
													DriveAlongProfile._rightDrivekD, 
													DriveAlongProfile._kV, 
													DriveAlongProfile._kA);
    	
    	this._pidSource = new PIDIn(() -> Robot.navx.getFusedAngle(), PIDSourceType.kDisplacement);
    	this._buffer = new PIDOut();
    	
    	this._angleController = new PIDController(DriveAlongProfile._turnkP, DriveAlongProfile._turnkI, DriveAlongProfile._turnkD, 
    												this._pidSource, this._buffer);
    	
    	this._angleController.setInputRange(0, 360);
    	this._angleController.setContinuous(true);
    	this._angleController.setOutputRange(-1, 1);
    	this._angleController.setAbsoluteTolerance(.2);

    	this._angleController.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	double left = this._leftEncoderFollower.calculate((int) Robot.drivetrain.getEncoderCounts(Robot.drivetrain.frontLeftTalon));
    	double right = this._rightEncoderFollower.calculate((int) Robot.drivetrain.getEncoderCounts(Robot.drivetrain.frontRightTalon));
    	double heading = (this._leftEncoderFollower.getHeading() + this._rightEncoderFollower.getHeading()) / 2;
    	
    	this._angleController.setSetpoint(Pathfinder.r2d(heading) % 360);
    	
    	if ((left == 0 && right == 0 && this._buffer.output == 0) || !DriverStation.getInstance().isAutonomous())
    	{
    		this._done = true;
    		return;
    	}
    	
    	left += this._buffer.output;
    	right -= this._buffer.output;
    	
    	Robot.drivetrain.driveTank(left, right);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return this._done;
    }

    // Called once after isFinished returns true
    protected void end() 
    {
    	Robot.drivetrain.stopMotors();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
    	this.end();
    }
}
