package org.usfirst.frc.team135.robot.commands;

import org.usfirst.frc.team135.robot.Robot;

import org.usfirst.frc.team135.robot.OI;
import org.usfirst.frc.team135.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveJ extends Command {
	
	private String Orientation;
	
    public DriveJ(String orientation) 
    {
    	requires(Robot.drivetrain);
    //	requires(Robot.gyro);
    	this.Orientation = orientation;
    }
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.InitializeDriveTrain();
    	//Robot.gyro.ZeroGyro();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    if (Orientation == "Field")
    {
    	Robot.drivetrain.driveFieldOriented(Robot.oi.GetLeftX(), Robot.oi.GetRightY(), Robot.oi.GetTwist(), 0);
		SmartDashboard.putNumber("Rear Left Speed", Robot.drivetrain.getEncoderSpeed(Robot.drivetrain.rearLeftTalon));
		SmartDashboard.putNumber("Rear Right Speed", Robot.drivetrain.getEncoderSpeed(Robot.drivetrain.rearRightTalon));
		SmartDashboard.putNumber("Front Left Speed", Robot.drivetrain.getEncoderSpeed(Robot.drivetrain.frontLeftTalon));
		SmartDashboard.putNumber("Front Right Speed", Robot.drivetrain.getEncoderSpeed(Robot.drivetrain.frontRightTalon));
    }
    else if (Orientation == "Robot")
    { 
    	Robot.drivetrain.driveRobotOriented(Robot.oi.GetLeftX(), Robot.oi.GetRightY(), Robot.oi.GetTwist());
		SmartDashboard.putNumber("Rear Left Speed", Robot.drivetrain.getEncoderSpeed(Robot.drivetrain.rearLeftTalon));
		SmartDashboard.putNumber("Rear Right Speed", Robot.drivetrain.getEncoderSpeed(Robot.drivetrain.rearRightTalon));
		SmartDashboard.putNumber("Front Left Speed", Robot.drivetrain.getEncoderSpeed(Robot.drivetrain.frontLeftTalon));
		SmartDashboard.putNumber("Front Right Speed", Robot.drivetrain.getEncoderSpeed(Robot.drivetrain.frontRightTalon));

    }
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
    }
}
