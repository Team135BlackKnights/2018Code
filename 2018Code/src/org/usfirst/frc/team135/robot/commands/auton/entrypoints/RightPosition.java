package org.usfirst.frc.team135.robot.commands.auton.entrypoints;

import org.usfirst.frc.team135.robot.commands.auton.groups.MidToSwitch;
import org.usfirst.frc.team135.robot.commands.auton.groups.SideToSwitch;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RightPosition extends CommandGroup {

	private static final int
	CLOSE = 1,
	FAR = 0,
	INVALID = -1;
    public RightPosition() {
    	String msg = DriverStation.getInstance().getGameSpecificMessage();
    	int switchPosition = getSwitchPosition(msg);
    	int scalePosition = getScalePosition(msg);
    	if (switchPosition == INVALID || scalePosition == INVALID)
    	{
    		//no point in running anything but autoline if we don't know where anything is
    	}
    	
    	
    	if (SmartDashboard.getBoolean("Try to go for Switch?", true) && !SmartDashboard.getBoolean("Try to go for Scale?", false))
    	{
    		addSequential(new SideToSwitch());
    		//Go for switch only
    		if (switchPosition == CLOSE)
    		{
    			//3 cube
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
    			if (SmartDashboard.getBoolean("Tiebreak with: Switch (Check)/Scale (Uncheck)", true))
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
    			if (SmartDashboard.getBoolean("Tiebreak with: Switch (Check)/Scale (Uncheck)", true))
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
    			if (SmartDashboard.getBoolean("Tiebreak with: Switch (Check)/Scale (Uncheck)", true))
    			{
    				//1 cube switch
    			}
    			else
    			{
    				//2 cube scale
    			}
    		}
    	}

    }
    
    private int getSwitchPosition(String msg)
    {
    	if (msg.toUpperCase().charAt(0) == 'R') //Switch is straight up from us
    	{
    		return CLOSE;
    	}
    	else if (msg.toUpperCase().charAt(0)  == 'L') //Switch is far away from us
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
    	if (msg.toUpperCase().charAt(1)  == 'R') //Switch is straight up from us
    	{
    		return CLOSE;
    	}
    	else if (msg.toUpperCase().charAt(1)  == 'L') //Switch is far away from us
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
