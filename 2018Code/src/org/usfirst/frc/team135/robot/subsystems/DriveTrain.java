package org.usfirst.frc.team135.robot.subsystems;

import org.usfirst.frc.team135.robot.commands.DriveJ;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;




import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

/**
 *
 */
public class DriveTrain extends Subsystem {

	private static DriveTrain instance;
	
	private WPI_TalonSRX FRONT_RIGHT_TALON, FRONT_LEFT_TALON, REAR_RIGHT_TALON, REAR_LEFT_TALON;
	
	private MecanumDrive chassis;
	
	public static final int
		FRONT_LEFT_ID = 4,
		REAR_LEFT_ID = 1,
		FRONT_RIGHT_ID = 5,
		REAR_RIGHT_ID = 2;
	
	private static final double TICK2REV = (1/4096.0);
	
	private DriveTrain()
	{
			FRONT_RIGHT_TALON = new WPI_TalonSRX(FRONT_RIGHT_ID);
			FRONT_RIGHT_TALON.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
			FRONT_RIGHT_TALON.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 10, 10);
			FRONT_RIGHT_TALON.setSelectedSensorPosition(0, 0, 10);
			
			FRONT_LEFT_TALON = new WPI_TalonSRX(FRONT_LEFT_ID);
			FRONT_LEFT_TALON.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
			FRONT_LEFT_TALON.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 10, 10);
			FRONT_LEFT_TALON.setSelectedSensorPosition(0, 0, 10);
			
			REAR_RIGHT_TALON = new WPI_TalonSRX(REAR_RIGHT_ID);
			REAR_RIGHT_TALON.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
			REAR_RIGHT_TALON.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 10, 10);
			REAR_RIGHT_TALON.setSelectedSensorPosition(0, 0, 10);
			
			REAR_LEFT_TALON = new WPI_TalonSRX(REAR_LEFT_ID);
			REAR_LEFT_TALON.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
			REAR_LEFT_TALON.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 10, 10);
			REAR_LEFT_TALON.setSelectedSensorPosition(0, 0, 10);
		
			
			//talons[i].configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_10Ms, 10);
			//talons[i].configVelocityMeasurementWindow(16, 10); //Might want to check this later
			//talons[i].config_kP(0, .01, 10);

		InitializeDriveTrain();
	}
	
	public void InitializeDriveTrain()
	{
		
		chassis = new MecanumDrive(FRONT_LEFT_TALON, REAR_LEFT_TALON,
				FRONT_RIGHT_TALON, REAR_RIGHT_TALON);
		chassis.setDeadband(.15);
		chassis.setSafetyEnabled(false);
	}
	public static DriveTrain getInstance()
	{
		if (instance == null)
		{
			instance = new DriveTrain();
		}
		return instance;
	}
	
	/*public double getEncoderCounts(int side)
	{
		return ((double)talons[side].getSelectedSensorPosition(0)) ;
	}
	
	public double getEncoderSpeed(int side)
	{
		return ((double)talons[side].getSelectedSensorVelocity(0));
	}*/
	
	public void driveCartesianWorld(double y, double x, double rotationalRate, double fieldOrientation)
	{
		chassis.driveCartesian(y, x, rotationalRate, fieldOrientation);
		
	}
	
	public void driveCartesianLocal(double y, double x, double rotationalRate, double fieldOrientation)
	{
		chassis.driveCartesian(y, x, rotationalRate);
	}
	
	public void drivePolar(double magnitude, double angle, double rotationalRate)
	{
		chassis.drivePolar(magnitude, angle, rotationalRate);
	}

	
    public void initDefaultCommand() 
    {
    	setDefaultCommand(new DriveJ());
    }
}