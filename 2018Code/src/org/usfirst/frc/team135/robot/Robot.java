/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team135.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team135.robot.commands.auton.entrypoints.LeftPosition;
import org.usfirst.frc.team135.robot.commands.auton.entrypoints.MiddlePosition;
import org.usfirst.frc.team135.robot.commands.auton.entrypoints.RightPosition;
import org.usfirst.frc.team135.robot.commands.auton.groups.SideToAutoline;
import org.usfirst.frc.team135.robot.commands.auton.groups.SideToSwitch;
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
	public static final ExampleSubsystem kExampleSubsystem
			= new ExampleSubsystem();
	
	public static NavX navx;
	public static UltrasonicSensor ultrasonic;
	public static OI oi;
	public static DriveTrain drivetrain;
	public static Lift lift;
	public static Intake intake;
	public static Hang hang;
	public static Canifier canifier;
	Command m_autonomousCommand;
	Command resetHang;
	SendableChooser<Command> m_chooser = new SendableChooser<>();
	
	

	@Override
	public void robotInit() {
		//Order does matter.
		
		navx = NavX.getInstance();
		canifier = Canifier.getInstance();
		ultrasonic = UltrasonicSensor.getInstance();
		drivetrain = DriveTrain.getInstance();
		hang = Hang.getInstance();
		lift = Lift.getInstance();
		intake = Intake.GetInstance();
		oi = OI.getInstance();
		
		
		CameraServer.getInstance().startAutomaticCapture();
		
		
		m_chooser.addDefault("Autoline", new SideToAutoline());
		m_chooser.addObject("Left Position", new LeftPosition());
		m_chooser.addObject("Middle Position", new MiddlePosition());
		m_chooser.addObject("Right Position", new RightPosition());
		SmartDashboard.putData("Auto mode", m_chooser);
		SmartDashboard.putBoolean("Try to go for Scale?", false);
		SmartDashboard.putBoolean("Try to go for Switch?", false);
		SmartDashboard.putBoolean("Tiebreak with: Switch (Check)/Scale (Uncheck)", false);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() 
	{
		//resetHang = new ResetHang();
		//resetHang.start();
/*
		getGameSpecificMessage.start();
		setSmartDashboardKeys.start();*/
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
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
		m_autonomousCommand = new SideToSwitch();

		
		//m_chooser.getSelected().start();
		
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
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
