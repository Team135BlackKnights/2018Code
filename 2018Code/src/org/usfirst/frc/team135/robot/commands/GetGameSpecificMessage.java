package org.usfirst.frc.team135.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
//import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team135.robot.commands.AutoLeftStationLeftSwitchPlate;
import org.usfirst.frc.team135.robot.commands.AutoLeftStationLeftScale;
import org.usfirst.frc.team135.robot.commands.AutoLeftStationAutoLine;
import org.usfirst.frc.team135.robot.commands.AutoMiddleStationLeftSwitchPlate;
import org.usfirst.frc.team135.robot.commands.AutoMiddleStationRightSwitchPlate;
import org.usfirst.frc.team135.robot.commands.AutoRightStationAutoLine;
import org.usfirst.frc.team135.robot.commands.AutoRightStationRightScale;
import org.usfirst.frc.team135.robot.commands.AutoRightStationRightSwitchPlate;
/**
 *
 */
public class GetGameSpecificMessage extends CommandGroup {
	String station;
    public GetGameSpecificMessage(String x) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	station = x;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	String gameData;
    	gameData = DriverStation.getInstance().getGameSpecificMessage();
     	if(station ==  "LeftStation")
    	{
    		if(gameData.charAt(0) == 'L' && gameData.charAt(1) == 'L')
    		{
    		 addSequential(new AutoLeftStationLeftSwitchPlate());
    		}
    		else if (gameData.charAt(0) == 'L' && gameData.charAt(1) == 'R')
    		{
    			addSequential(new AutoLeftStationLeftSwitchPlate());
    		}
    		else if(gameData.charAt(0) == 'R' && gameData.charAt(1) == 'L')
    		{
    			addSequential(new  AutoLeftStationLeftScale());
    		}
    		else
    		{
    			addSequential(new AutoLeftStationAutoLine());
    		}
    	}
    	
    	if(station == "MiddleStation")
    	{
    		if(gameData.charAt(0) == 'L' && gameData.charAt(1) == 'L')
    		{
    			addSequential(new AutoMiddleStationLeftSwitchPlate()); 
    		}
    		else if (gameData.charAt(0) == 'L' && gameData.charAt(1) == 'R')
    		{
    			addSequential(new AutoMiddleStationLeftSwitchPlate());
    		}
    		else if(gameData.charAt(0) == 'R' && gameData.charAt(1) == 'L')
    		{
    			addSequential(new AutoMiddleStationRightSwitchPlate());
    		}
    		else
    		{
    			addSequential(new AutoMiddleStationRightSwitchPlate());
    		}
    	}
    	
    	if (station == "RightStation")
    	{
    		if(gameData.charAt(0) == 'R' && gameData.charAt(1) == 'R')
    		{
    			addSequential(new AutoRightStationRightSwitchPlate());
    		}
    		else if (gameData.charAt(0) == 'L' && gameData.charAt(1) == 'R')
    		{
    			addSequential(new AutoRightStationRightScale());
    		}
    		else if(gameData.charAt(0) == 'R' && gameData.charAt(1) == 'L')
    		{
    			addSequential(new AutoRightStationRightSwitchPlate());
    		}
    		else
    		{
    			addSequential(new AutoRightStationAutoLine());
    		}
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