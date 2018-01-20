package org.usfirst.frc.team135.robot.subsystems;

import org.usfirst.frc.team135.robot.commands.DriveJ;

import com.ctre.CANTalon.FeedbackDevice;
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
	
	private WPI_TalonSRX[] talons = new WPI_TalonSRX[4];
	
	private MecanumDrive chassis;
	
	public static final int
		FRONT_LEFT = 0,
		REAR_LEFT = 1,
		FRONT_RIGHT = 2,
		REAR_RIGHT = 3;
	
	private static final double TICK2REV = (1/4096.0);
	
	private DriveTrain()
	{
		for (int i = 0; i < 4; i++)
		{
			talons[i] = new WPI_TalonSRX(i);
			talons[i].setSafetyEnabled(false);
			talons[i].configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
			talons[i].setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 10, 10);
			talons[i].setSelectedSensorPosition(0, 0, 10);
			
			talons[i].configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_10Ms, 10);
			talons[i].configVelocityMeasurementWindow(16, 10); //Might want to check this later
			talons[i].config_kP(0, .01, 10);
		}
		
		chassis = new MecanumDrive(talons[FRONT_LEFT], talons[REAR_LEFT],
									talons[FRONT_RIGHT], talons[REAR_RIGHT]);
		chassis.setDeadband(.15);
		chassis.setSafetyEnabled(true);
	}
	
	public static DriveTrain getInstance()
	{
		if (instance == null)
		{
			instance = new DriveTrain();
		}
		return instance;
	}
	
	
	public double getEncoderCounts(int side)
	{
		return ((double)talons[side].getSelectedSensorPosition(0)) ;
	}
	
	public double getEncoderSpeed(int side)
	{
		return ((double)talons[side].getSelectedSensorVelocity(0));
	}
	
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