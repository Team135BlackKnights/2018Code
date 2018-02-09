package org.usfirst.frc.team135.robot.commands;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;


public class AutoDriveDiagonal extends CommandGroup {
private Timer timer;
    	int distance;
    	double angle;
    public AutoDriveDiagonal(int dist, double ang) {
    	requires(Robot.sonar);
    	distance = dist;
    	angle = ang;
    	timer = new Timer();
    
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
		//set motors equal to .5 speed 
    	//set timer value (How does this become generalized if the value differs for each path?) 
    	if (distance == RobotMap.midLeftSwitch) {
    		
    		timer.start();
    		while (Robot.sonar.GetRightSonarValue() >RobotMap.midLeftSwitch && timer.get() < 5 && DriverStation.getInstance().isAutonomous()) 
    		{
    			Robot.drivetrain.driveCartesian(-1, Math.tan(Math.toRadians(angle)), 0, 0, false); 
    		}
    		timer.stop();
    		timer.reset();
    		timer.start();
    		while (Robot.sonar.GetRightSonarValue() <= RobotMap.midLeftSwitch && timer.get() < 5 && DriverStation.getInstance().isAutonomous())
    		{
    			Robot.drivetrain.driveCartesian(0, 0, 0, 0, false);
    		}
    	}
    	
    	else if (distance == RobotMap.midRightSwitch) {
    		
    		timer.start();
    		while (Robot.sonar.GetRightSonarValue() >RobotMap.midRightSwitch && timer.get() < 5 && DriverStation.getInstance().isAutonomous()) //change value when we know that we need for each path
    		{
    			Robot.drivetrain.driveCartesian(1, Math.tan(Math.toRadians(angle)), 0, 0, false); 
    		}
    		timer.stop();
    		timer.reset();
    		timer.start();
    		while (Robot.sonar.GetRightSonarValue() <= RobotMap.midRightSwitch && timer.get() < 5 && DriverStation.getInstance().isAutonomous())
    		{
    			Robot.drivetrain.driveCartesian(0, 0, 0, 0, false);
    		}
    	}
    	
   	else 
		{
			Robot.drivetrain.driveCartesian(0,0, 0, 0, false);
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
