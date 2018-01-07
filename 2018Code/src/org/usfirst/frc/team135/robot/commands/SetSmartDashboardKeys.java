package org.usfirst.frc.team135.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SetSmartDashboardKeys extends Command {

    boolean finished = false;
	
    public SetSmartDashboardKeys() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
		if(!SmartDashboard.containsKey("Joystick Deadband"))
		{
			SmartDashboard.putNumber("Joystick Deadband", .05);
			SmartDashboard.setPersistent("Joystick Deadband");
		}
		
		if (!SmartDashboard.containsKey("Drivetrain Motor Drive Percent Power"))
		{
			SmartDashboard.putNumber("Drivetrain Motor Drive Percent Power", 1.0);
			SmartDashboard.setPersistent("Drivetrain Motor Drive Power Percentage");
		}
		
		if (!SmartDashboard.containsKey("Drivetrain Motor Drive Power Cap"))
		{
			SmartDashboard.putNumber("Drivetrain Motor Drive Power Cap", 1.0);
			SmartDashboard.setPersistent("Drivetrain Motor Drive Power Cap");
		}
		
		if (!SmartDashboard.containsKey("Forward Tank Tuning Constant"))
		{
			SmartDashboard.putNumber("Forward Tank Tuning Constant", 1.0);
			SmartDashboard.setPersistent("Forward Tank Tuning Constant");
		}
		
		if (!SmartDashboard.containsKey("Strafe Tank Tuning Constant"))
		{
			SmartDashboard.putNumber("Strafe Tank Tuning Constant", 1.0);
			SmartDashboard.setPersistent("Strafe Tank Tuning Constant");
		}
		
		if (!SmartDashboard.containsKey("Strafe Tank Tuning Constant"))
		{
			SmartDashboard.putNumber("Turn Tank Tuning Constant", 1.0);
			SmartDashboard.setPersistent("Turn Tank Tuning Constant");
		}
		
		if (!SmartDashboard.containsKey("Global directions"))
		{
			SmartDashboard.putBoolean("Global directions", true);
			SmartDashboard.setPersistent("Global directions");
		}
		
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	finished = true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }


}
