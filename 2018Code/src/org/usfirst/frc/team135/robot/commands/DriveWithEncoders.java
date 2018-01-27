package org.usfirst.frc.team135.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team135.robot.*;

/**
 *
 */
public class DriveWithEncoders extends Command {

    public DriveWithEncoders() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	ResetEncoders();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Forward(256); //Goes forward one motor revolution
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    private void ResetEncoders() //Resets Encoder Values
    {
    	Robot.drivetrain.frontLeftTalon.setSelectedSensorPosition(0, 0, 10);
    	Robot.drivetrain.frontRightTalon.setSelectedSensorPosition(0, 0, 10);
    	Robot.drivetrain.rearLeftTalon.setSelectedSensorPosition(0, 0, 10);
    	Robot.drivetrain.rearRightTalon.setSelectedSensorPosition(0, 0, 10);
    }
    
    private void Forward(int EncoderTicks)
    {
    	Robot.drivetrain.frontLeftTalon.setSelectedSensorPosition(EncoderTicks, 0, 10);
    	Robot.drivetrain.frontRightTalon.setSelectedSensorPosition(EncoderTicks, 0, 10);
    	Robot.drivetrain.rearLeftTalon.setSelectedSensorPosition(EncoderTicks, 0, 10);
    	Robot.drivetrain.rearRightTalon.setSelectedSensorPosition(EncoderTicks, 0, 10);
    }
    
    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
