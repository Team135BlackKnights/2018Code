package org.usfirst.frc.team135.robot.commands.auton.singles;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.RobotMap.DIRECTION;
import org.usfirst.frc.team135.robot.RobotMap.COMPETITION.DRIVETRAIN;
import org.usfirst.frc.team135.robot.util.PIDIn;
import org.usfirst.frc.team135.robot.util.PIDOut;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.*;
import jaci.pathfinder.Trajectory.Config;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;
/**
 *
 */
public class DriveAlongProfile extends Command implements RobotMap {

	private Waypoint[] _points;
	private Trajectory _leftTrajectory, _rightTrajectory;
	private EncoderFollower _leftEncoderFollower, _rightEncoderFollower;
	
	private static final Trajectory.FitMethod FIT_METHOD = Trajectory.FitMethod.HERMITE_QUINTIC;

	private static final int SAMPLES = Trajectory.Config.SAMPLES_HIGH;
	
	private static final double
		TIMESTEP = 0.05, //In seconds
		MAX_VELOCITY = DRIVETRAIN.MAX_VELOCITY_TICKS * CONVERSIONS.TICKS2METERS,
		MAX_ACCELERATION = DRIVETRAIN.MAX_ACCELERATION_TICKS * CONVERSIONS.TICKS2METERS,
		MAX_JERK = DRIVETRAIN.MAX_JERK_TICKS * CONVERSIONS.TICKS2METERS;
	
	private static final double VELOCITY_RATIO = 1 / MAX_VELOCITY;
	
	private static final double 
		_drivekP = 1.0,
		_drivekI = 0.01,
		_drivekD = 10.0,
		_drivekA = 0; //Acceleration gain. Tweak this if you want to go to accelerate faster.
	
	private PIDController _angleController;
	private PIDOut _buffer;
	private PIDIn _pidSource;
	
	private static final double 
		_turnkP = .267,
		_turnkI = 0.00267,
		_turnkD = 2.67,
		_turnkF = 0; //There might be some error causing real world process we can take out with kF
	
	private static final double TRACK_WIDTH = DRIVETRAIN.TRACK_WIDTH * CONVERSIONS.INCHES2METERS; //Jaci calls this wheelbase width
	
	private boolean _done = false;
	
    public DriveAlongProfile(Waypoint[] points) {
    	requires(Robot.drivetrain);
  
    	this._points = points.clone();
    	
    	this._buffer = new PIDOut();
    	this._pidSource = new PIDIn(() -> Robot.navx.getFusedAngle(), PIDSourceType.kDisplacement);
    	
    	this._angleController = new PIDController(DriveAlongProfile._turnkP, DriveAlongProfile._turnkI, DriveAlongProfile._turnkD,
    												DriveAlongProfile._turnkF, this._pidSource, this._buffer);
    	
    	this._angleController.setInputRange(0, 360);
    	this._angleController.setContinuous(true);
    	this._angleController.setOutputRange(-.2, .2);
    	this._angleController.setAbsoluteTolerance(.2);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Trajectory.Config baseTrajectoryConfig = new Trajectory.Config(DriveAlongProfile.FIT_METHOD, 
    																	DriveAlongProfile.SAMPLES, 
    																	DriveAlongProfile.TIMESTEP, 
    																	DriveAlongProfile.MAX_VELOCITY, 
    																	DriveAlongProfile.MAX_ACCELERATION, 
    																	DriveAlongProfile.MAX_JERK);
    	
    	Trajectory baseTrajectory = Pathfinder.generate(this._points, baseTrajectoryConfig);
    	
    	TankModifier modifier = new TankModifier(baseTrajectory);
    	
    	modifier.modify(TRACK_WIDTH);
    	
    	this._leftTrajectory = modifier.getLeftTrajectory();
    	this._rightTrajectory = modifier.getRightTrajectory();
    	
    	this._leftEncoderFollower.configureEncoder((int)Robot.drivetrain.getEncoderCounts(Robot.drivetrain.frontLeftTalon), 
    												(int)CONVERSIONS.REVS2TICKS, 
    												DRIVETRAIN.WHEEL_DIAMETER);
    	
    	this._rightEncoderFollower.configureEncoder((int)Robot.drivetrain.getEncoderCounts(Robot.drivetrain.frontRightTalon), 
													(int)CONVERSIONS.REVS2TICKS, 
													DRIVETRAIN.WHEEL_DIAMETER);
    	
    	this._leftEncoderFollower.configurePIDVA(DriveAlongProfile._drivekP, 
    											DriveAlongProfile._drivekI, 
    											DriveAlongProfile._drivekD, 
    											DriveAlongProfile.VELOCITY_RATIO, 
    											DriveAlongProfile._drivekA);
    	
    	this._rightEncoderFollower.configurePIDVA(DriveAlongProfile._drivekP, 
    											DriveAlongProfile._drivekI, 
    											DriveAlongProfile._drivekD, VELOCITY_RATIO, 
    											DriveAlongProfile._drivekA);
    	this._angleController.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	double left = this._leftEncoderFollower.calculate((int) Robot.drivetrain.getEncoderCounts(Robot.drivetrain.frontLeftTalon));
    	double right = this._rightEncoderFollower.calculate((int) Robot.drivetrain.getEncoderCounts(Robot.drivetrain.frontRightTalon));
    	
    	this._angleController.setSetpoint(Pathfinder.r2d(this._leftEncoderFollower.getHeading()) % 360);
    	
    	left += this._buffer.output;
    	right -= this._buffer.output;
    	
    	Robot.drivetrain.driveTank(left, right);
    	
    	if (left == 0 && right == 0 && this._buffer.output == 0)
    	{
    		this._done = true;
    	}
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
    	end();
    }
}
