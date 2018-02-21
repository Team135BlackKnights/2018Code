
package org.usfirst.frc.team135.robot.commands.auton.entrypoints;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.commands.auton.groups.SideToNearScale;
import org.usfirst.frc.team135.robot.commands.auton.groups.SideToNearSwitch;
import org.usfirst.frc.team135.robot.commands.auton.singles.InitializeAngle;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class LeftPosition extends CommandGroup {

	private static final int
		CLOSE = 1,
		FAR = 0,
		INVALID = -1;
		
	
    public LeftPosition() 
    {
    	addSequential(new InitializeAngle(180));
    	String msg = DriverStation.getInstance().getGameSpecificMessage();
    	int switchPosition = getSwitchPosition(msg);
    	int scalePosition = getScalePosition(msg);
    	
    	if (switchPosition == INVALID || scalePosition == INVALID)
    	{

    	}
    	
    	
    	if (SmartDashboard.getBoolean("Try to go for Switch?", true) && !SmartDashboard.getBoolean("Try to go for Scale?", false))
    	{
    		//Go for switch only
    		if (switchPosition == CLOSE)
    		{
    			//3 cube eventually
    			addSequential(new SideToNearSwitch());
    			
    		}
    		else
    		{
    			//2 cube
    		}
    	}
    	else if (!SmartDashboard.getBoolean("Try to go for Switch?", true) && SmartDashboard.getBoolean("Try to go for Scale?", false))
    	{
    		//Go for scale only
    		if (scalePosition == CLOSE)
    		{
    			//2 cube
    			addSequential(new SideToNearScale());
    		}
    		else
    		{
    			//1 cube
    		}
    		
    	}
    	else
    	{
    		//Either you want it all or are extremely indecive and didn't select anything. Either way here's a balanced auto
    		
    		if (switchPosition == CLOSE && scalePosition == CLOSE)
    		{
    			//1 on switch, 1 on scale
    		}
    		else if (switchPosition == FAR && scalePosition == FAR)
    		{
    			if (SmartDashboard.getBoolean("Prefer Switch or Scale?", true))
    			{
    				//1 cube switch
    			}
    			else
    			{
    				//1 cube scale
    			}
    		}
    		else if (switchPosition == CLOSE && scalePosition == FAR)
    		{
    			if (SmartDashboard.getBoolean("Prefer Switch or Scale?", true))
    			{
    				//3 cube switch
    			}
    			else
    			{
    				//1 cube scale
    			}
    		}
    		else if (switchPosition == FAR && scalePosition == CLOSE)
    		{
    			if (SmartDashboard.getBoolean("Prefer Switch or Scale?", true))
    			{
    				//1 cube switch
    			}
    			else
    			{
    				//2 cube scale
    			}
    		}
    	}

    	
    	/*
    	if (switchPosition == CLOSE)
    	{
    		if (SmartDashboard.getBoolean("Try to go for Switch?", true))
    		{
    			//Go for switch
    		}
    		else
    		{
    			if (scalePosition == CLOSE)
    			{
    				if (SmartDashboard.getBoolean("Try to go for Scale?", false))
    				{
    					//Go for scale
    				}
    				else //In the case you you didn't choose preference for either I'll choose for you.
    				{
    					//Go for switch
    				}
    			}
    			
    		}
    	}
    	else
    	{
    		if (SmartDashboard.getBoolean("Try to go for Switch?", true))
    		{
    			//Go for switch
    		}
    		else
    		{
    			if (scalePosition == CLOSE)
    			{
    				if (SmartDashboard.getBoolean("Try to go for Scale?", false))
    				{
    					//Go for scale
    				}
    				else //In the case you you didn't choose preference for either I'll choose for you.
    				{
    					//Go for switch
    				}
    			}
    			
    		}
    	}
*/
    	

    	
    }
    
    private int getSwitchPosition(String msg)
    {
    	if (msg.toUpperCase().charAt(0)  == 'L') //Switch is straight up from us
    	{
    		return CLOSE;
    	}
    	else if (msg.toUpperCase().charAt(0)  == 'R') //Switch is far away from us
    	{
    		return FAR;
    	}
    	else
    	{
    		System.out.println("Unable to get a valid game specific message. Only running autoline.");
    		return INVALID;
    	}
    }
    
    private int getScalePosition(String msg)
    {
    	if (msg.toUpperCase().charAt(1) == 'L') //Switch is straight up from us
    	{
    		return CLOSE;
    	}
    	else if (msg.toUpperCase().charAt(1) == 'R') //Switch is far away from us
    	{
    		return FAR;
    	}
    	else
    	{
    		System.out.println("Unable to get a valid game specific message. Only running autoline.");
    		return INVALID;
    	}
    }
}
