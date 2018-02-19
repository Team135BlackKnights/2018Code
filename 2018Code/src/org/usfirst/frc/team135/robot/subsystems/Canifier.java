package org.usfirst.frc.team135.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.CANifier;
import com.ctre.phoenix.CANifier.PWMChannel;
import com.ctre.phoenix.CANifierStatusFrame;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Timer;

import com.ctre.phoenix.ILoopable;
import java.nio.ByteBuffer;
import edu.wpi.first.wpilibj.can.CANJNI;

import org.usfirst.frc.team135.robot.RobotMap;
/**
 *
 */
public class Canifier extends Subsystem implements RobotMap{

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	static public Canifier instance;
	static public CANifier canifier;
	

	CANifier.PWMChannel rearLidar = CANifier.PWMChannel.PWMChannel0;
	CANifier.PWMChannel frontLidar = CANifier.PWMChannel.PWMChannel1;
    double[][] dutyCycleAndPeriods = new double[][] { new double[] { 0, 0 }, new double[] { 0, 0 }, new double[] { 0, 0 }, new double[] { 0, 0 } };

    
	public Canifier()
	{
		int id = (SmartDashboard.getBoolean("Is Competition Bot?", true) ? PRACTICE.CANIFIER.ID : COMPETITION.CANIFIER.ID);
		canifier = new CANifier(id);
	}
	public static Canifier getInstance()
	{
		if (instance == null)
		{
			instance = new Canifier();
		}
		return instance;
	}
	
	public double GetCanifierVoltage() 
	{
		double value = canifier.getBusVoltage();
		System.out.print("made it to voltage");
		System.out.print(value);
		return value;
	}
	
	public double getMeasuredPulseWidths(CANifier.PWMChannel pwmCh)
	{
        canifier.getPWMInput(CANifier.PWMChannel.PWMChannel0, dutyCycleAndPeriods[0]);  //reads from all the PWM Channels
        canifier.getPWMInput(CANifier.PWMChannel.PWMChannel1, dutyCycleAndPeriods[1]);
        /*canifier.getPWMInput(CANifier.PWMChannel.PWMChannel2, dutyCycleAndPeriods[2]);
        canifier.getPWMInput(CANifier.PWMChannel.PWMChannel3, dutyCycleAndPeriods[3]); 
        
        ByteBuffer data = ByteBuffer.allocate(4);                              //sends CAN data
        data.asIntBuffer().put((int)(dutyCycleAndPeriods[3][0] *1000));
        byte[] newdata = new byte[4];
        data.get(newdata);
        CANJNI.FRCNetCommCANSessionMuxSendMessage(0x1E040000, newdata, 4); */
        
		return dutyCycleAndPeriods[pwmCh.value][0];   
	}
	
	public double getFrontLidarCM()
	{
		Timer.delay(.01);
		return getMeasuredPulseWidths(rearLidar)/10;
		

		
	}
	
	public double getRearLidarCM()
	{
		Timer.delay(.01);
		return	getMeasuredPulseWidths(frontLidar)/10;
			 
	}
	
	public double getRearLidarInches()
	{
		return (getRearLidarCM() / 2.54);
	}
	
	public double getFrontLidarInches()
	{
		return (getFrontLidarCM() / 2.54);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        
    }
    
    public void periodic()
    {		
    	//System.out.println("Lidar: " + getRearLidarInches() + ", " + getFrontLidarInches());
    }
}