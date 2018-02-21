package org.usfirst.frc.team135.robot.commands.auton.singles;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap.DIRECTION;
import org.usfirst.frc.team135.robot.RobotMap.FIELD;
import org.usfirst.frc.team135.robot.util.FunctionalDoubleManager;
import org.usfirst.frc.team135.robot.util.Lidar_wrapper;
import org.usfirst.frc.team135.robot.util.NavX_wrapper;
import org.usfirst.frc.team135.robot.util.PIDOut;

import edu.wpi.first.wpilibj.DriverStation;
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
	private boolean done_driving = false;
	
	private boolean yEnabled = false, xEnabled = false;
	
	
	private double xSpeed, ySpeed;
	
	private double xBuffer, yBuffer;
	Timer timer;
	
	private FunctionalDoubleManager xSensor;
	private FunctionalDoubleManager ySensor;
	
    public DriveStraightForwardDistance(double distanceX, double xSpeed, double xBuffer, FunctionalDoubleManager xSensor, boolean xEnabled,
    		double distanceY, double ySpeed, double yBuffer, FunctionalDoubleManager ySensor, boolean yEnabled, 
    		double timeout) 
    {
    	this.distanceY = distanceY;
    	this.distanceX = distanceX;
    	this.xSpeed = xSpeed;
    	this.ySpeed = ySpeed;
    	this.xSensor = xSensor;
    	this.ySensor = ySensor;	
    	this.xEnabled = xEnabled;
    	this.yEnabled = yEnabled;
    	this.xBuffer = xBuffer;
    	this.yBuffer = yBuffer;
    	navx = new NavX_wrapper(Robot.navx);
    	bufRotationZ = new PIDOut();
    	angleZController = new PIDController(.06, .0005, .5, navx, bufRotationZ);
    	initAngleController();
    
    	
    	
    	
    	this.timeout = timeout;
    	timer = new Timer();
    }
    
    private void initAngleController()
    {
    	angleZController.setInputRange(0, 360);
    	angleZController.setContinuous();
    	angleZController.setOutputRange(DIRECTION.COUNTERCLOCKWISE * .2, DIRECTION.CLOCKWISE * .2);
    	angleZController.setAbsoluteTolerance(.2);
    	
    }

	// Called just before this Command runs the first time
    protected void initialize() {
    	angleZController.setSetpoint(Robot.navx.getFusedAngle());
    	angleZController.enable();
    	bufRotationZ.output = 0.0;
    	timer.start();
    	double strafing = 0, driving = 0;
    	
    	System.out.println("Starting Distance: " + distanceX + ", " + distanceY);
    	System.out.println(Robot.navx.getFusedAngle());
    	while(timer.get() < timeout && DriverStation.getInstance().isAutonomous())
    	{
    		
    		driving = determineDrive();
    		strafing = determineStrafe();
  	 
        	if (done_driving && done_strafing)
        	{
        		System.out.println("Done");
        		return;
        	}
        	
      		//System.out.println("Running at y: " + ySensor.get());
    		//System.out.println("Running at x: " + xSensor.get());
        
        	Robot.drivetrain.driveCartesian(strafing, driving, bufRotationZ.output);
    	}
    }
    
    private double determineDrive()
    {
       	if (Math.abs(ySensor.get() - distanceY) >= yBuffer && !done_driving && yEnabled)
    	{
    		return ySpeed;
    	}
    	else
    	{
    		System.out.println("Checking if I should stop forward at: " + ySensor.get());
    		if (!yEnabled || done_driving)
    		{
    			if (!yEnabled && !done_driving)
    			{
    				done_driving = true;
    			}
    			return 0;
    		}
    		
    		if (ySensor.get() > distanceY + 10)
    		{
    			return ySpeed;
    		}
    		
    		for(int pos_stability = 1; pos_stability <= 5; )
    		{
    			if (Math.abs(ySensor.get() - distanceY) < yBuffer)
    			{
    				pos_stability++;
    				Timer.delay(.002);
    			}
    			else
    			{
    				System.out.println("false alarm");
    				return ySpeed;
    			}
    		}     	
			System.out.println("Confirmed driving stop at: " + ySensor.get());
			done_driving = true;
			return 0;
    	}
    	
    }
    
    private double determineStrafe()
    {
    	if (ySensor.get() > FIELD.WALL_SLANT_END
				&& !done_strafing
				&& Math.abs(xSensor.get() - distanceX) >= xBuffer && xEnabled) 
    	{
    		return xSpeed;
    	}
    	else
    	{
    		System.out.println("Checking if I should stop sideways at: " + xSensor.get());
    		
    		if (!xEnabled || done_strafing )
    		{
    			if (!xEnabled && !done_strafing)
    			{
    				System.out.println("Done strafing!");
    				done_strafing = true;
    			}
    			
    			return 0;
    			
    		}
    		
    		if (ySensor.get() < FIELD.WALL_SLANT_END)
    		{
    			return 0;
    		}
    		
    		
    		
    		for(int pos_stability = 1; pos_stability <= 10; )
    		{
    			if (Math.abs(xSensor.get() - distanceX) < xBuffer)
    			{
    				pos_stability++;
    				Timer.delay(.002);
    			}
    			else
    			{
    				System.out.println("false alarm");
    				return xSpeed;
    			}
    		}
    		
			System.out.println("Confirmed sideways stop at: " + xSensor.get());
			done_strafing = true;
    		return 0;
    	}
		
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {


    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
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
