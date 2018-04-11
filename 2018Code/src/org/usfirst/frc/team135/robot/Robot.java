/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team135.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team135.robot.RobotMap.AUTO;
import org.usfirst.frc.team135.robot.commands.auton.entrypoints.LeftPosition;
import org.usfirst.frc.team135.robot.commands.auton.entrypoints.MiddlePosition;
import org.usfirst.frc.team135.robot.commands.auton.entrypoints.RightPosition;
import org.usfirst.frc.team135.robot.commands.auton.entrypoints.Test;
import org.usfirst.frc.team135.robot.commands.auton.groups.MidToSwitch;
import org.usfirst.frc.team135.robot.commands.auton.groups.SideToAutoline;
import org.usfirst.frc.team135.robot.commands.auton.groups.SideToFarScale;
import org.usfirst.frc.team135.robot.commands.auton.groups.SideToNearScale;
import org.usfirst.frc.team135.robot.commands.auton.singles.DriveAndGetCube;
import org.usfirst.frc.team135.robot.commands.teleop.*;
import org.usfirst.frc.team135.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	
	//public static PDP pdp;
	public static NavX navx;
	public static UltrasonicSensor ultrasonic;
	public static OI oi;
	public static DriveTrain drivetrain;
	public static Lift lift;
	public static Intake intake;
	public static Hang hang;
	public static Camera camera;
	//public static Canifier canifier;
	
	public static String msg;
	Command m_autonomousCommand;

	SendableChooser<String> m_chooser = new SendableChooser<>();
	
	

	@Override
	public void robotInit() {
		//Order does matter.
		
		//pdp = PDP.getInstance();
		navx = NavX.getInstance();
		//canifier = Canifier.getInstance();
		ultrasonic = UltrasonicSensor.getInstance();
		drivetrain = DriveTrain.getInstance();
		hang = Hang.getInstance();
		intake = Intake.GetInstance();
		lift = Lift.getInstance();
		oi = OI.getInstance();
		camera = Camera.getInstance();
		
		//CameraServer.getInstance().addServer("10.1.35.11", 5800);
		
		m_chooser.addDefault("Autoline", "Autoline");
		m_chooser.addObject("Left Position", "LeftPosition");
		m_chooser.addObject("Middle Position", "MiddlePosition");
		m_chooser.addObject("Right Position", "RightPosition");
		m_chooser.addObject("Current Test", "CurrentTest");
		
		SmartDashboard.putData("Auto modes", m_chooser);
		
		SmartDashboard.setPersistent("Try to go for Scale?");
		SmartDashboard.setPersistent("Try to go for Switch?");
		SmartDashboard.setPersistent("Prefer Switch or Scale?");
		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() 
	{
/*
		getGameSpecificMessage.start();
		setSmartDashboardKeys.start();*/
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		//Robot.navx.reset();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		
		Robot.msg = DriverStation.getInstance().getGameSpecificMessage();
		
		/*
	
		if (m_chooser.getSelected().equals("LeftPosition"))
		{
			m_autonomousCommand = new LeftPosition();
		}
		else if (m_chooser.getSelected().equals("RightPosition"))
		{
			m_autonomousCommand = new RightPosition();
		}
		else if (m_chooser.getSelected().equals("MiddlePosition"))
		{
			m_autonomousCommand = new MiddlePosition();
		}
		else if (m_chooser.getSelected().equals("CurrentTest"))
		{
			m_autonomousCommand = new Test();
		}
		else
		{
			m_autonomousCommand = new SideToAutoline(true);
		}*/
		
		m_autonomousCommand = new MidToSwitch(true);
		
		m_autonomousCommand.start();
		
	}
	
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		camera.setDriverMode(true);
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
