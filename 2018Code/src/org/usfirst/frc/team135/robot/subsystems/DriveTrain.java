package org.usfirst.frc.team135.robot.subsystems;

import java.util.Arrays;
import java.util.Collections;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.RobotMap.*;
import org.usfirst.frc.team135.robot.commands.DriveJ;
//import org.usfirst.frc.team135.robot.commands.DriveStraight;
import org.usfirst.frc.team135.robot.util.DriveMotor;
import org.usfirst.frc.team135.robot.util.MecanumDriveVelocity;
//import org.usfirst.frc.team135.robot.util.PIDOutputBuf;
//import org.usfirst.frc.team135.robot.util.PID_GyroSource;
import org.usfirst.frc.team135.robot.util.PID_Manager;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.Vector2d;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.drive.RobotDriveBase.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.MotorSafetyHelper;
/**
 *
 */
public class DriveTrain extends Subsystem implements RobotMap {

    public void initDefaultCommand() 
    {
    	setDefaultCommand(new DriveJ());
    }
	
	private static DriveTrain instance;
	
	private DriveMotor FrontLeftMotor;
	private DriveMotor BackLeftMotor;
	private DriveMotor FrontRightMotor;
	private DriveMotor BackRightMotor;
		
	
	private PIDController angle_controller;

	private MecanumDriveVelocity chassis;

	//private PID_GyroSource gyroSource;
	//private PIDOutputBuf outputBuffer;
	private PIDController angleLocker;
	
	private PID_Manager angleLockerPID;
	public double lockedAngle;
	
	public Timer timer;
	
	public static DriveTrain getInstance()
	{
		if (instance == null)
		{
			instance = new DriveTrain();
		}
		
		return instance;
	}
	
	private DriveTrain()
	{
		FrontLeftMotor = new DriveMotor(MOTORS.NUMBER.FL.getCAN_ID(), 
										MOTORS.PID.FL,
										MOTORS.REVERSED.FL);
		
		BackLeftMotor = new DriveMotor(MOTORS.NUMBER.BL.getCAN_ID(),
									   MOTORS.PID.BL,
									   MOTORS.REVERSED.BL);
		
		FrontRightMotor = new DriveMotor(MOTORS.NUMBER.FR.getCAN_ID(),
										MOTORS.PID.FR,
										MOTORS.REVERSED.FR);
		
		BackRightMotor = new DriveMotor(MOTORS.NUMBER.BR.getCAN_ID(),
										MOTORS.PID.BR,
										MOTORS.REVERSED.BR);
			
		angleLockerPID = new PID_Manager(.015, .0005, .2);
		//gyroSource = new PID_GyroSource(Robot.gyro, PIDSourceType.kDisplacement);
		//outputBuffer = new PIDOutputBuf();
		//angleLocker = new PIDController(angleLockerPID.kP, angleLockerPID.kI, angleLockerPID.kD, gyroSource, outputBuffer);
		
		timer = new Timer();
		
		
		
		SmartDashboard.putNumber("Drivetrain Angle Locker kP", angleLockerPID.kP);
		SmartDashboard.putNumber("Drivetrain Angle Locker kI", angleLockerPID.kI);
		SmartDashboard.putNumber("Drivetrain Angle Locker kD", angleLockerPID.kD);
		//chassis = new MecanumDriveVelocity(FrontLeftMotor.getSC(), BackLeftMotor.getSC(), FrontRightMotor.getSC(), BackRightMotor.getSC());
	}
	
	public void driveCartesian(double y, double x, double zRotation, double orientation, boolean lockAngle)
	{
		double correction = 0.0;		
		Vector2d input = new Vector2d(x, y);		

		if (lockAngle)
		{
			if (!angleLocker.isEnabled())
			{
				angleLocker.enable();
			//	angleLocker.setSetpoint(Robot.gyro.getRawAngle());
				//timer.start();
			}
		}
		else
		{
			if (angleLocker.isEnabled())
			{
			//	angleLocker.setSetpoint(Robot.gyro.getRawAngle());
				angleLocker.disable();
				timer.stop();
				timer.reset();
			}
		}
		
		if (angleLocker.isEnabled())
		{
			angleLocker.setPID(angleLockerPID.kP, angleLockerPID.kI, angleLockerPID.kD);
		//	zRotation += outputBuffer.output;
			
		}

		//System.out.println(Robot.navx.getHeading());
		input.rotate(orientation);
		
		
		double
			FLSpeed = input.x + input.y + zRotation, //Front Left
			BLSpeed = -input.x + input.y + zRotation,	//Back Left
			FRSpeed =	input.x - input.y + zRotation,	//Front Right
			BRSpeed = -input.x - input.y + zRotation;  //Back Right

		
		IOData(FLSpeed > 0, BLSpeed > 0, FRSpeed > 0, BRSpeed > 0);
			
		normalize(FLSpeed, BLSpeed, FRSpeed, BRSpeed);
		
		
		FrontRightMotor.setVelocity(FRSpeed);
		BackRightMotor.setVelocity(BRSpeed);
		FrontLeftMotor.setVelocity(FLSpeed);
		BackLeftMotor.setVelocity(BLSpeed);
	}
	
	
	public void IOData(boolean flDir, boolean blDir, boolean frDir, boolean brDir)
	{	
		
		FrontLeftMotor.getData();
		BackLeftMotor.getData();
		FrontRightMotor.getData();
		BackRightMotor.getData();
		
		FrontLeftMotor.putData(flDir);
		BackLeftMotor.putData(blDir);
		FrontRightMotor.putData(frDir);
		BackRightMotor.putData(brDir);

		SmartDashboard.getNumber("Drivetrain Angle Locker kP", angleLockerPID.kP);
		SmartDashboard.getNumber("Drivetrain Angle Locker kI", angleLockerPID.kI);
		SmartDashboard.getNumber("Drivetrain Angle Locker kD", angleLockerPID.kD);
	}

	private void normalize(double FL, double BL, double FR, double BR) 
	{
		FL = Math.abs(FL);
		BL = Math.abs(BL);
		FR = Math.abs(FR);
		BR = Math.abs(BR);
	
		//Use java collections to find the max rather than a loop. 
		//Just pass a temporary arraylist for it to use
		double maxMagnitude = Collections.max(Arrays.asList(new Double[] {FL, BL, FR, BR}));
		
		//In a normal situation speeds are between -1.0 and 1.0
		if (maxMagnitude > 1.0) 
		{
			FL /= maxMagnitude;
			BL /= maxMagnitude;
			FR /= maxMagnitude;
			BR /= maxMagnitude;
		}
	}
	
	public void periodic() //Runs every time the scheduler.run() is called.
	{
		FrontLeftMotor.reconfigurePID();
		BackLeftMotor.reconfigurePID();
		FrontRightMotor.reconfigurePID();
		BackRightMotor.reconfigurePID();
	}
	
}
	

