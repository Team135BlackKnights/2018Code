package org.usfirst.frc.team135.robot.util;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;
import edu.wpi.first.wpilibj.drive.Vector2d;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tInstances;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tResourceType;
import edu.wpi.first.wpilibj.hal.HAL;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MecanumDriveVelocity extends RobotDriveBase{

	private static int instances = 0;
	
	private boolean pidEnabled = false;
	
	private boolean m_reported = false;

    private WPI_TalonSRX[] mTalons;
    
    private WPI_TalonSRX
    	FRONT_LEFT,
    	REAR_LEFT,
    	FRONT_RIGHT,
    	REAR_RIGHT;
    
	
	public MecanumDriveVelocity(WPI_TalonSRX fl, WPI_TalonSRX bl, WPI_TalonSRX fr, WPI_TalonSRX br)
	{
    	FRONT_LEFT = fl;
    	REAR_LEFT = bl;
    	FRONT_RIGHT = fr;
    	REAR_RIGHT = br;
		
		
		addChild(fl);
		addChild(bl);
		addChild(fr);
		addChild(br);
			//talon.config_kI(0, .0001, 10);
			//talon.config_kD(0, .15, 10);
		
		
		
		/* Motors are inverted already by main drive functions.
		if (invertedSide == Side.LEFT)
		{
			talons[FRONT_LEFT].setInverted(true);
			talons[REAR_LEFT].setInverted(true);
		}
		else if(invertedSide == Side.RIGHT)
		{
			talons[FRONT_RIGHT].setInverted(true);
			talons[REAR_RIGHT].setInverted(true);
		}
		*/
		instances++;
		
		setName("MecanumDriveVelocity", instances);
		
	}
	
	@Override
	public void initSendable(SendableBuilder builder) {
		builder.setSmartDashboardType("MecanumDrivevelocity");
		builder.addDoubleProperty("Front Left Motor Speed", FRONT_LEFT::get,
		        FRONT_LEFT::set);
		
		builder.addDoubleProperty("Front Right Motor Speed", FRONT_RIGHT::get,
		        FRONT_RIGHT::set);
		
		builder.addDoubleProperty("Rear Left Motor Speed", REAR_LEFT::get,
		        REAR_LEFT::set);
		
		builder.addDoubleProperty("Rear Right Motor Speed", REAR_RIGHT::get,
		        REAR_RIGHT::set);
		
	}
	
	public void driveCartesian(double y, double x, double zRotation, double fieldOrientation, double maxVel)
	{
	    if (!m_reported) {
	        HAL.report(tResourceType.kResourceType_RobotDrive, 4,
	                   tInstances.kRobotDrive_MecanumCartesian);
	        m_reported = true;
	      }
	    


		y = limit(y);
		y = applyDeadband(y, m_deadband);

		x = limit(x);
		x = applyDeadband(x, m_deadband);

		// Compensate for gyro angle.
		Vector2d input = new Vector2d(y, x);
		input.rotate(fieldOrientation);

		double[] wheelSpeeds = new double[4];
		
		//y is reversed on the right side because they spin in the opposite direction of the x normally
		//rear wheels direct the motion 45 degrees into the robot so x is reversed.
		
		//This set of assignments maps the joystick input to the wheel
		wheelSpeeds[MotorType.kFrontLeft.value] = input.x + input.y + zRotation;
		wheelSpeeds[MotorType.kFrontRight.value] = input.x - input.y + zRotation;
		wheelSpeeds[MotorType.kRearLeft.value] = -input.x + input.y + zRotation;
		wheelSpeeds[MotorType.kRearRight.value] = -input.x - input.y + zRotation;
		normalize(wheelSpeeds);


		FRONT_LEFT.set(ControlMode.Velocity, wheelSpeeds[MotorType.kFrontLeft.value] * maxVel);
		REAR_LEFT.set(ControlMode.Velocity, wheelSpeeds[MotorType.kRearLeft.value] * maxVel);
		FRONT_RIGHT.set(ControlMode.Velocity,wheelSpeeds[MotorType.kFrontRight.value] * maxVel);
		REAR_RIGHT.set(ControlMode.Velocity,wheelSpeeds[MotorType.kRearRight.value] * maxVel);
		
	    m_safetyHelper.feed();
	}
	
	public void driveCartesian(double ySpeed, double xSpeed, double zRotation, double maxVel) {
	    driveCartesian(ySpeed, xSpeed, zRotation, 0.0, maxVel);
	}
	
	public void drivePolar(double magnitude, double angle, double zRotation, double maxVel) 
	{
	    if (!m_reported) 
	    {
	    	HAL.report(tResourceType.kResourceType_RobotDrive, 4, tInstances.kRobotDrive_MecanumPolar);
		      m_reported = true;
		}

		driveCartesian(magnitude * Math.sin(angle * (Math.PI / 180.0)),
		                   magnitude * Math.cos(angle * (Math.PI / 180.0)), zRotation, 0.0, maxVel);
	}
	
	public void disablePID()
	{
		pidEnabled = false;
	}

	@Override
	public void stopMotor() {
		for(WPI_TalonSRX talon : mTalons)
		{
			talon.set(0.0);
		}
	}

	@Override
	public String getDescription() {
	    return "MecanumDriveVelocity";
	}

}
