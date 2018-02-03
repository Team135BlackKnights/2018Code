package org.usfirst.frc.team135.robot.commands;

//import org.usfirst.frc.team135.robot.*;
import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap; 
//import org.usfirst.frc.team135.robot.subsystems.DriveTrain;

//import java.awt.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.DriverStation;
//import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;

//import org.usfirst.frc.team135.robot.commands.AutoLeftStationAutoLine;
/**
 *4096 rpm
 */
public class AutoDriveStraight extends CommandGroup {
	
	private Timer timer;
	private int distance;


	public AutoDriveStraight(int dist) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.sonar);
    	distance = dist; 
    	timer = new Timer();
    //tis always field oriented	 
	}

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

		//set motors equal to .5 speed
    	//set timer value (How does this become generalized if the value differs for each path?) 
    	if (distance == RobotMap.autoLine) {
    		
    		timer.start();
    		while (Robot.sonar.GetBackSonarValue() <RobotMap.autoLine && timer.get() < 5 && DriverStation.getInstance().isAutonomous()) 
    		{
    			Robot.drivetrain.driveCartesian(0, 1, 0, 0, false); 
    		}
    		timer.stop();
    		timer.reset();
    		timer.start();
    		while (Robot.sonar.GetBackSonarValue() >= RobotMap.autoLine && timer.get() < 5 && DriverStation.getInstance().isAutonomous())
    		{
    			Robot.drivetrain.driveCartesian(0, 0, 0, 0, false);
    		}
    	}
    	
    	else if (distance == RobotMap.autoLineMid) {
    		
    		timer.start();
    		while (Robot.sonar.GetBackSonarValue() <RobotMap.autoLineMid && timer.get() < 5 && DriverStation.getInstance().isAutonomous()) //change value when we know that we need for each path
    		{
    			Robot.drivetrain.driveCartesian(0, 1, 0, 0, false); 
    		}
    		timer.stop();
    		timer.reset();
    		timer.start();
    		while (Robot.sonar.GetBackSonarValue() >= RobotMap.autoLineMid && timer.get() < 5 && DriverStation.getInstance().isAutonomous())
    		{
    			Robot.drivetrain.driveCartesian(0, 0, 0, 0, false);
    		}
    	}
    	
   	else if (distance == RobotMap.autoSwitch) {
    		
    		timer.start();
    		while (Robot.sonar.GetBackSonarValue() <RobotMap.autoSwitch && timer.get() < 5 && DriverStation.getInstance().isAutonomous()) //change value when we know that we need for each path
    		{
    			Robot.drivetrain.driveCartesian(0, 1, 0, 0, false); 
    		}
    		timer.stop();
    		timer.reset();
    		timer.start();
    		while (Robot.sonar.GetBackSonarValue() >= RobotMap.autoSwitch && timer.get() < 5 && DriverStation.getInstance().isAutonomous())
    		{
    			Robot.drivetrain.driveCartesian(0, 0, 0, 0, false);
    		}
    		
    	}
    	
   	else if (distance == RobotMap.midLeftSwitchStraight) {
		
		timer.start();
		while (Robot.sonar.GetBackSonarValue() <RobotMap.midLeftSwitchStraight && timer.get() < 5 && DriverStation.getInstance().isAutonomous()) //change value when we know that we need for each path
		{
			Robot.drivetrain.driveCartesian(0, 1, 0, 0, false); 
		}
		timer.stop();
		timer.reset();
		timer.start();
		while (Robot.sonar.GetBackSonarValue() >= RobotMap.midLeftSwitchStraight && timer.get() < 5 && DriverStation.getInstance().isAutonomous())
		{
			Robot.drivetrain.driveCartesian(0, 0, 0, 0, false);
		}
		
	}
    	
   	else if (distance == RobotMap.midRightSwitchStraight) {
		
		timer.start();
		while (Robot.sonar.GetBackSonarValue() <RobotMap.midRightSwitchStraight && timer.get() < 5 && DriverStation.getInstance().isAutonomous()) //change value when we know that we need for each path
		{
			Robot.drivetrain.driveCartesian(0, 1, 0, 0, false); 
		}
		timer.stop();
		timer.reset();
		timer.start();
		while (Robot.sonar.GetBackSonarValue() >= RobotMap.midRightSwitchStraight && timer.get() < 5 && DriverStation.getInstance().isAutonomous())
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
