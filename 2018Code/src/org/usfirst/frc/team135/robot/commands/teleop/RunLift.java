package org.usfirst.frc.team135.robot.commands.teleop;

import org.usfirst.frc.team135.robot.Robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RunLift extends Command {
	
	
    public RunLift() 
    {
    	requires(Robot.lift);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//System.out.println("Running lift at power: " + Preferences.getInstance().getDouble("Lift Speed", 0.0));
    	
    	double joyValue = Robot.oi.GetManipY();
    	/*
    	if (Robot.lift.getLiftMotorCurrentDraw() > 60)
    	{
    		
    		SmartDashboard.putBoolean("Lift Current Alert", false);
    	}
    	else
    	{
    		SmartDashboard.putBoolean("Lift Current Alert", true);
    	}
    	*/
    	if (Robot.lift.getEncoderPosition() >= 1450)
    	{
    		Robot.intake.setCompressorOn();
    		Robot.lift.mantainPosition();
    		return;
    	}
    	
    	if (Math.abs(joyValue) < .05)
    	{
    		joyValue = 0;
    	}
    	
    	if (joyValue > 0)
    	{
    		Robot.intake.setCompressorOff();		
    		joyValue *= Preferences.getInstance().getDouble("Lift Up Speed", 0.0);
    	}
    	else if (joyValue < 0)
    	{
    		Robot.intake.setCompressorOn();
    		joyValue *= -Preferences.getInstance().getDouble("Lift Down Speed", 0.0);
    	}
    	else if (joyValue == 0)
    	{
    		Robot.intake.setCompressorOn();
    		Robot.lift.mantainPosition();
    		return;
    	}
    	
  
    	
    	Robot.lift.set(joyValue);

    	
    	
    	
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
