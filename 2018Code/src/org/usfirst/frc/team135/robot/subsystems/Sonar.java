package org.usfirst.frc.team135.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.first.wpilibj.Ultrasonic;

/**
 *
 */
public class Sonar extends Subsystem {
	static final int BACK_SONAR_TRIGGER_PORT = 2;
	static final int BACK_SONAR_ECHO_PORT = 3;
	
	static final int FRONT_SONAR_TRIGGER_PORT = 0;
	static final int FRONT_SONAR_ECHO_PORT = 1;
	
	static final int LEFT_SONAR_TRIGGER_PORT = 4;
	static final int LEFT_SONAR_ECHO_PORT = 5;
	
	static final int RIGHT_SONAR_TRIGGER_PORT = 6;
	static final int RIGHT_SONAR_ECHO_PORT = 7;
	
	double distance = 0;
	private Ultrasonic backSonar = new Ultrasonic(BACK_SONAR_TRIGGER_PORT,BACK_SONAR_ECHO_PORT);
	private Ultrasonic frontSonar = new Ultrasonic(FRONT_SONAR_TRIGGER_PORT,FRONT_SONAR_ECHO_PORT);
	private Ultrasonic leftSonar = new Ultrasonic(LEFT_SONAR_TRIGGER_PORT,LEFT_SONAR_ECHO_PORT);
	private Ultrasonic rightSonar = new Ultrasonic(RIGHT_SONAR_TRIGGER_PORT,RIGHT_SONAR_ECHO_PORT);
	
	private static Sonar instance;
	
	public Sonar()
	{
		backSonar.setAutomaticMode(true);
		frontSonar.setAutomaticMode(true);
		leftSonar.setAutomaticMode(true);
		rightSonar.setAutomaticMode(true);
	}
	
	public static Sonar getInstance()
	{
		if (instance == null)
		{
			instance = new Sonar();
		}
		
		return instance;
	}
	
	
	public double GetBackSonarValue() //is the distance in inches
	{
		double value = backSonar.getRangeInches(); //Function that gets the inches range from the sonar
		//System.out.println("Distance = "+ value);
		return value;
	}
	
	public double GetFrontSonarValue() //is the distance in inches
	{
		double value = frontSonar.getRangeInches(); //Function that gets the inches range from the sonar
		//System.out.println("Distance = "+ value);
		return value;
	}
	
	public double GetLeftSonarValue() //is the distance in inches
	{
		double value = leftSonar.getRangeInches(); //Function that gets the inches range from the sonar
		//////System.out.println("Distance = "+ value);
		return value;
	}
	
	public double GetRightSonarValue() //is the distance in inches
	{
		double value = rightSonar.getRangeInches(); //Function that gets the inches range from the sonar
		///////System.out.println("Distance = "+ value);
		return value;
	}
	
    public void initDefaultCommand() {
        
    }
}

