package org.usfirst.frc.team135.robot.commands.auton.singles;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.util.NavX_wrapper;
import org.usfirst.frc.team135.robot.util.PIDOut;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class DriveStraightTime extends InstantCommand {

	double time;
	double x = 0.0, y = 0.0;
	private PIDController zController;
	private PIDOut zBuf;
	
    public DriveStraightTime(Double x, Double y, double time) {
        super();
        requires(Robot.drivetrain);
        
        NavX_wrapper navx = new NavX_wrapper(Robot.navx);
        zBuf = new PIDOut();
        zController = new PIDController(.01, 0 , .1, navx, zBuf);
        zController.setInputRange(0, 360);
        zController.setOutputRange(-.2, .2);
        zController.setAbsoluteTolerance(.2);
        zController.setContinuous();
        
        this.time = time;
        
        if (x != null)
        {
        	this.x = x;
        }
        
        if (y != null)
        {
        	this.y = y;
        }
    }

    // Called once when the command executes
    protected void initialize() 
    {

    	
    	zController.setSetpoint(Robot.navx.getFusedAngle());
    	zController.enable();

    	Timer timer = new Timer();
    	timer.start();
    	while(timer.get() < time)
    	{
    		Robot.drivetrain.driveCartesian(x, y, zBuf.output);
    	}
    	
    	System.out.println("Done with DriveStraightTime");
    	//Robot.drivetrain.stopMotors();
    }

}
