package org.usfirst.frc.team135.robot.commands.auton.singles;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.util.FunctionManager;
import org.usfirst.frc.team135.robot.util.FunctionalDoubleManager;
import org.usfirst.frc.team135.robot.util.Lidar_wrapper;
import org.usfirst.frc.team135.robot.util.NavX_wrapper;
import org.usfirst.frc.team135.robot.util.PIDOut;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraightForwardDistance extends Command {

	private double distanceY, distanceX;
	private PIDController yController, angleZController;
	private PIDOut bufY, bufRotationZ;
	private NavX_wrapper navx;
	private double timeout;
	private Ultrasonic sonar;
	
	private boolean done_strafing = false;
	private boolean done = false;
	
	Timer timer;
	
	private FunctionalDoubleManager functionManager;
	
    public DriveStraightForwardDistance(double distanceX, double distanceY, double timeout) {
    	this.distanceY = distanceY;
    	this.distanceX = distanceX;
    	
    	navx = new NavX_wrapper(Robot.navx);
    	
    	bufRotationZ = new PIDOut();
    	
    	functionManager = () -> return Robot.canifier.ReadLidarInches();
    	
    	angleZController = new PIDController(.03, .0003, 0, navx, bufRotationZ);
    	
    	angleZController.setInputRange(0, 360);
    	angleZController.setContinuous();
    	angleZController.setOutputRange(-.2, .2);
    	angleZController.setAbsoluteTolerance(.5);
    	angleZController.setSetpoint((Robot.navx.getFusedAngle() + Robot.navx.getFusedAngle()) / 2);
    	
    	System.out.println("Distance: " + distanceY);
    	
    	this.timeout = timeout;
    	timer = new Timer();
    	
    	
    	sonar = Robot.ultrasonic.leftSonar;
    }


	// Called just before this Command runs the first time
    protected void initialize() {
    	angleZController.enable();
    	timer.start();
    	double strafing = 0.0;
    	
    	
    	while(timer.get() < timeout)
    	{
    		
    		if (Robot.canifier.ReadLidarInches() > 20 && Math.abs(sonar.getRangeInches() - distanceX) > 1 && !done_strafing)
        	{
    			if (sonar.getRangeInches() < distanceX)
    			{
    				strafing = -1.0;
    			}
    			else
    			{
    				strafing = 1.0;
    			}
    			
        		
        	}
        	else
        	{
        		System.out.println("Stopping sideways at: " + Robot.canifier.ReadLidarInches());
        		
        		for(int pos_stability = 1; pos_stability <= 8; )
        		{
        			if (Math.abs(Robot.canifier.ReadLidarInches()) >= distanceY - 100 && timer.get() < 3.5)
        			{
        				pos_stability++;
        				Timer.delay(.001);
        			}
        			else
        			{
        				System.out.println("false alarm");
        				break;
        			}
        			
        			if (pos_stability == 8)
        			{
        				System.out.println("Confirmed sideways stop at: " + Robot.canifier.ReadLidarInches());
                		
                		strafing = 0.0;
                		done_strafing = true;
                		
        			}
        		}     
        	}
    		
    		
        	if (Robot.canifier.ReadLidarInches() < distanceY - 82)
        	{
        		System.out.println("Running at lidar: " + Robot.canifier.ReadLidarInches());
        		System.out.println("Running at sonar: " + sonar.getRangeInches());
        		Robot.drivetrain.driveCartesian(strafing, -1, bufRotationZ.output);
        	}
        	else
        	{
        		System.out.println("Stopping forward at: " + Robot.canifier.ReadLidarInches());
        		for(int pos_stability = 1; pos_stability <= 8; )
        		{
        			if (Math.abs(Robot.canifier.ReadLidarInches()) >= distanceY - 100 && timer.get() < 3.5)
        			{
        				pos_stability++;
        				Timer.delay(.001);
        			}
        			else
        			{
        				System.out.println("false alarm");
        				break;
        			}
        			
        			if (pos_stability == 8)
        			{
        				System.out.println("Confirmed stop at: " + Robot.canifier.ReadLidarInches());
                		done = true;
                		
                		return;
        			}
        		}     	
        	}
        	
        	Robot.drivetrain.driveCartesian(strafing * speed, driving * speed, );
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {


    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.stopMotors();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
