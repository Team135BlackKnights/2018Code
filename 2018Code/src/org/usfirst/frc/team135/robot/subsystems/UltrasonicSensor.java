package org.usfirst.frc.team135.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;

/**
 *
 */
public class UltrasonicSensor extends Subsystem {

    private static final int PING_PORT = 3;
    private static final int ECHO_PORT = 4;
    
    
    private Ultrasonic sonar = new Ultrasonic(PING_PORT, ECHO_PORT);
    private static UltrasonicSensor instance;
    
    
    public UltrasonicSensor getInstance()
    {
    	if (instance == null)
    	{
    		instance = new UltrasonicSensor();
    	}
    	return instance;
    }

    	public double GetSonarValue()
    	{
    		double SonarDistance = sonar.getRangeInches();
    		SmartDashboard.putNumber("SonarDistance: ",SonarDistance);
    		return SonarDistance;
    	}
    public void initDefaultCommand() {
        
    }
}

