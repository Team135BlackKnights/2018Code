package org.usfirst.frc.team135.robot.commands.teleop;

import org.usfirst.frc.team135.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunHang extends Command {

    public RunHang() {
       
        requires(Robot.hang);
    }

    // Called once when the command executes
    protected void initialize() 
    {
    }
    
    protected void execute()
    {
    	
    	Robot.hang.RunHangMotor(1.0);
    }  
   
    protected boolean isFinished() {
        return false;
    }
    
    protected void end()
    {
    	Robot.hang.RunHangMotor(0);
    }
    
    protected void interrupted() {
    	this.end();
    }
}
