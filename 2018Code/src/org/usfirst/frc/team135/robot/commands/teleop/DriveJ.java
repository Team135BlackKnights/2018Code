package org.usfirst.frc.team135.robot.commands.teleop;

import org.usfirst.frc.team135.robot.Robot;

import org.usfirst.frc.team135.robot.OI;
import org.usfirst.frc.team135.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveJ extends Command {
	
	private boolean isFieldOriented;
	
    public DriveJ(boolean isFieldOriented) 
    {
    	requires(Robot.drivetrain);
    //	requires(Robot.gyro);
    	this.isFieldOriented = isFieldOriented;
    }
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.InitializeDriveTrain();
    	//Robot.gyro.ZeroGyro();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
		if (isFieldOriented) 
		{
			Robot.drivetrain.driveCartesian(-Robot.oi.GetRightX(), Robot.oi.GetRightY(), Robot.oi.GetLeftTwist());
		} 
		else 
		{
			Robot.drivetrain.driveCartesian(-Robot.oi.GetRightX(), Robot.oi.GetRightY(), Robot.oi.GetLeftTwist(), 0);
		}

		SmartDashboard.putNumber("Rear Left Speed", Robot.drivetrain.getEncoderSpeed(Robot.drivetrain.rearLeftTalon));
		SmartDashboard.putNumber("Rear Right Speed", Robot.drivetrain.getEncoderSpeed(Robot.drivetrain.rearRightTalon));
		SmartDashboard.putNumber("Front Left Speed", Robot.drivetrain.getEncoderSpeed(Robot.drivetrain.frontLeftTalon));
		SmartDashboard.putNumber("Front Right Speed", Robot.drivetrain.getEncoderSpeed(Robot.drivetrain.frontRightTalon));

		SmartDashboard.putNumber("Rear Left Setpoint", Robot.drivetrain.getEncoderSetpoint(Robot.drivetrain.rearLeftTalon));
		SmartDashboard.putNumber("Rear Right Setpoint", Robot.drivetrain.getEncoderSetpoint(Robot.drivetrain.rearRightTalon));
		SmartDashboard.putNumber("Front Left Setpoint", Robot.drivetrain.getEncoderSetpoint(Robot.drivetrain.frontLeftTalon));
		SmartDashboard.putNumber("Front Right Setpoint", Robot.drivetrain.getEncoderSetpoint(Robot.drivetrain.frontRightTalon));
		
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}