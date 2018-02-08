package org.usfirst.frc.team135.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;

/**
 *
 */
public class UltrasonicSensor extends Subsystem {

	private static final int FRONT_SONAR_TRIG_PORT = 1;
    private static final int FRONT_SONAR_ECHO_PORT = 2;
    
    private static final int RIGHT_SONAR_TRIG_PORT = 3;
    private static final int RIGHT_SONAR_ECHO_PORT = 4;
    
    private static final int LEFT_SONAR_TRIG_PORT = 5;
    private static final int LEFT_SONAR_ECHO_PORT = 6;
    
    private static final int BACK_SONAR_TRIG_PORT = 7;
    private static final int BACK_SONAR_ECHO_PORT = 8;
    
    public Ultrasonic rightSonar = new Ultrasonic(RIGHT_SONAR_TRIG_PORT, RIGHT_SONAR_ECHO_PORT);
    public Ultrasonic frontSonar = new Ultrasonic(FRONT_SONAR_TRIG_PORT, FRONT_SONAR_ECHO_PORT);
    public Ultrasonic leftSonar = new Ultrasonic(LEFT_SONAR_TRIG_PORT, LEFT_SONAR_ECHO_PORT);
    public Ultrasonic backSonar = new Ultrasonic(BACK_SONAR_TRIG_PORT, BACK_SONAR_ECHO_PORT);
    private static UltrasonicSensor instance;
    
    
    public static UltrasonicSensor getInstance()
    {
    	if (instance == null)
    	{
    		instance = new UltrasonicSensor();
    	}
    	return instance;
    }
    
    public UltrasonicSensor()
    {
    	rightSonar.setAutomaticMode(true);
    	leftSonar.setAutomaticMode(true);
    	frontSonar.setAutomaticMode(true);
    	backSonar.setAutomaticMode(true);
    }
    	public double GetRightSonarValue()
    	{
    		double RightSonarDistance = rightSonar.getRangeInches();
    		SmartDashboard.putNumber("Right Sonar Distance: ",RightSonarDistance);
    		return RightSonarDistance;
    	}
    	public double GetFrontSonarValue()
    	{
    		double FrontSonarDistance = frontSonar.getRangeInches();
    		SmartDashboard.putNumber("Front Sonar Distance: ", FrontSonarDistance);
    		return FrontSonarDistance;
    	}
    	public double GetLeftSonarValue()
    	{
    		double LeftSonarDistance = leftSonar.getRangeInches();
    		SmartDashboard.putNumber("Left Sonar Distance: ", LeftSonarDistance);
    		return LeftSonarDistance;
    	}
    	public double GetBackSonarValue()
    	{
    		double BackSonarDistance = backSonar.getRangeInches();
    		SmartDashboard.putNumber("Back Sonar Distance: ", BackSonarDistance);
    		return BackSonarDistance;
    	}
    	
    public void initDefaultCommand() {
        
    }
}

