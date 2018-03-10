package org.usfirst.frc.team135.robot.commands.auton.entrypoints;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.commands.auton.groups.MidToSwitch;
import org.usfirst.frc.team135.robot.commands.auton.singles.InitializeAngle;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class MiddlePosition extends CommandGroup {

	private static final int
		RIGHT = 0,
		LEFT = 1,
		INVALID = -1;
	
    public MiddlePosition() {
    	int switchPosition = getSwitchPosition(Robot.msg);
    	
    	addSequential(new InitializeAngle(270));
    	
    	System.out.println(switchPosition);
    	
    	if (SmartDashboard.getBoolean("Try to go for Switch?", true) && !SmartDashboard.getBoolean("Try to go for Scale?", false))
    	{
    		System.out.println(Robot.navx.getFusedAngle());
    		if (switchPosition == RIGHT)
    		{
    			
    			//addSequential(new MidToSwitch(true));
    		}
    		else if (switchPosition == LEFT)
    		{
    			//addSequential(new MidToSwitch(false));
    		}
    		else
    		{
    			
    		}
    	}
    	else
    	{
    	
    	}
    }
    
    private int getSwitchPosition(String msg)
    {
    	if (msg.toUpperCase().charAt(0)  == 'L') //Switch is straight up from us
    	{
    		return LEFT;
    	}
    	else if (msg.toUpperCase().charAt(0)  == 'R') //Switch is far away from us
    	{
    		return RIGHT;
    	}
    	else
    	{
    		System.out.println("Unable to get a valid game specific message. Only running autoline.");
    		return INVALID;
    	}
    }
}
