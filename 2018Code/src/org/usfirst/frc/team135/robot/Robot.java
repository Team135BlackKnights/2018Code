/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team135.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team135.robot.commands.auton.entrypoints.LeftPosition;
import org.usfirst.frc.team135.robot.commands.auton.entrypoints.MiddlePosition;
import org.usfirst.frc.team135.robot.commands.auton.entrypoints.RightPosition;
import org.usfirst.frc.team135.robot.commands.auton.groups.SideToAutoline;
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
		
		navx = NavX.getInstance();
		//canifier = Canifier.getInstance();
		camera = Camera.getInstance();
		ultrasonic = UltrasonicSensor.getInstance();
		drivetrain = DriveTrain.getInstance();
		hang = Hang.getInstance();
		intake = Intake.GetInstance();
		lift = Lift.getInstance();
		oi = OI.getInstance();
		
		
		//CameraServer.getInstance().startAutomaticCapture();
		m_chooser.addDefault("Autoline", "Autoline");
		m_chooser.addObject("Left Position", "LeftPosition");
		m_chooser.addObject("Middle Position", "MiddlePosition");
		m_chooser.addObject("Right Position", "RightPosition");
		SmartDashboard.putData("Auto mode", m_chooser);
		
		/*SmartDashboard.putBoolean("Try to go for scale?", false);
		SmartDashboard.putBoolean("Try to go for switch?", false);
		SmartDashboard.putBoolean("Prefer Switch?", false);
		*/
		
		SmartDashboard.setPersistent("Try to go for scale?");
		SmartDashboard.setPersistent("Try to go for switch?");
		SmartDashboard.setPersistent("Prefer Switch?");
		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() 
	{
		camera.setDriverMode(true);
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
		msg = DriverStation.getInstance().getGameSpecificMessage();
		
		camera.setDriverMode(false);
		
		String position = m_chooser.getSelected();
		
		if (position.equals("LeftPosition"))
		{
			m_autonomousCommand = new LeftPosition();
		}
		else if (position.equals("MiddlePosition"))
		{
			m_autonomousCommand = new MiddlePosition();
		}
		else if (position.equals("RightPosition"))
		{
			m_autonomousCommand = new RightPosition();
		}
		else
		{
			m_autonomousCommand = new SideToAutoline(false);
		}

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
		
		Robot.camera.setDriverMode(true);
		
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
