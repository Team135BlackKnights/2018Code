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
    	EncoderDrive(256,256,256,256); //Forward
    	EncoderDrive(0,256,0,256); //Forward Right
    	EncoderDrive(256,-256,-256,256); //Right
    	EncoderDrive(0,-256,-256,0); //BackRight
    	EncoderDrive(-256,-256,-256,-256); //Back
    	EncoderDrive(-256,0,0,-256); //BackLeft
    	EncoderDrive(-256,256,256,-256); //Left
    	EncoderDrive(0,256,256,0); //FrontLeft
    	
    	
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
    
    private void EncoderDrive(int FL, int FR, int BL, int BR,)
    {
    	Robot.drivetrain.frontLeftTalon.setSelectedSensorPosition(FL, 0, 10);
    	Robot.drivetrain.frontRightTalon.setSelectedSensorPosition(FR, 0, 10);
    	Robot.drivetrain.rearLeftTalon.setSelectedSensorPosition(BL, 0, 10);
    	Robot.drivetrain.rearRightTalon.setSelectedSensorPosition(BR, 0, 10);
    }
   
    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
