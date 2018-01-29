package org.usfirst.frc.team135.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;

/**
 *
 */
public class UltrasonicSensor extends Subsystem {

    private static final int RIGHT_SONAR_PING_PORT = 3;
    private static final int RIGHT_SONAR_ECHO_PORT = 4;
    
    private static final int BACK_SONAR_PING_PORT = 5;
    private static final int BACK_SONAR_ECHO_PORT = 6;
    
    private static final int LEFT_SONAR_PING_PORT = 7;
    private static final int LEFT_SONAR_ECHO_PORT = 8;
    // 2015: Front Trig 1 Echo 2
    // 2015: Left Trig 6 Echo 5
    
    
    private Ultrasonic rightSonar = new Ultrasonic(RIGHT_SONAR_PING_PORT, RIGHT_SONAR_ECHO_PORT);
    private Ultrasonic backSonar = new Ultrasonic(BACK_SONAR_PING_PORT, BACK_SONAR_ECHO_PORT);
    private Ultrasonic leftSonar = new Ultrasonic(LEFT_SONAR_PING_PORT, LEFT_SONAR_ECHO_PORT);
    private static UltrasonicSensor instance;
    
    
    public UltrasonicSensor getInstance()
    {
    	if (instance == null)
    	{
    		instance = new UltrasonicSensor();
    	}
    	return instance;
    }

    	public double GetRightSonarValue()
    	{
    		double RightSonarDistance = rightSonar.getRangeInches();
    		SmartDashboard.putNumber("Right Sonar Distance: ",RightSonarDistance);
    		return RightSonarDistance;
    	}
    	public double GetBackSonarValue()
    	{
    		double BackSonarDistance = backSonar.getRangeInches();
    		SmartDashboard.putNumber("Back Sonar Distance: ", BackSonarDistance);
    		return BackSonarDistance;
    	}
    	public double GetLeftSonarValue()
    	{
    		double LeftSonarDistance = leftSonar.getRangeInches();
    		SmartDashboard.putNumber("Left Sonar Distance: ", LeftSonarDistance);
    		return LeftSonarDistance;
    	}
    	
    public void initDefaultCommand() {
        
    }
}
