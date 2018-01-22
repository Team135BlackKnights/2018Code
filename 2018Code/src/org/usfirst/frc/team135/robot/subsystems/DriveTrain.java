package org.usfirst.frc.team135.robot.subsystems;

import org.usfirst.frc.team135.robot.commands.DriveJ;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc.team135.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

/**
 *
 */
public class DriveTrain extends Subsystem implements RobotMap{

	private static DriveTrain instance;
	private WPI_TalonSRX frontRightTalon, frontLeftTalon, rearRightTalon, rearLeftTalon;
	private MecanumDrive chassis;
	private static final double TICK2REV = (1/4096.0);
	
	private DriveTrain()
	{
		ConfigureTalons();
	}
	
	public void ConfigureTalons()
	{
		frontRightTalon = new WPI_TalonSRX(FRONT_RIGHT_TALON_ID);
		frontRightTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		frontRightTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 10, 10);
		frontRightTalon.setSelectedSensorPosition(0, 0, 10);
			
		frontLeftTalon = new WPI_TalonSRX(FRONT_LEFT_TALON_ID);
		frontLeftTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		frontLeftTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 10, 10);
		frontLeftTalon.setSelectedSensorPosition(0, 0, 10);
			
		rearRightTalon = new WPI_TalonSRX(REAR_RIGHT_TALON_ID);
		rearRightTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		rearRightTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 10, 10);
		rearRightTalon.setSelectedSensorPosition(0, 0, 10);
			
		rearLeftTalon = new WPI_TalonSRX(REAR_LEFT_TALON_ID);
		rearLeftTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		rearLeftTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 10, 10);
		rearLeftTalon.setSelectedSensorPosition(0, 0, 10);
		//talons[i].configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_10Ms, 10);
		//talons[i].configVelocityMeasurementWindow(16, 10); //Might want to check this later
		//talons[i].config_kP(0, .01, 10);
		InitializeDriveTrain();
			
	}
	
	public void InitializeDriveTrain()
	{
		chassis = new MecanumDrive(frontLeftTalon, rearLeftTalon,
				frontRightTalon, rearRightTalon);
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
	
	public double getEncoderCounts(WPI_TalonSRX talon)
	{
		return ((double)talon.getSelectedSensorPosition(0)) ;
	}
	
	public double getEncoderSpeed(WPI_TalonSRX talon)
	{
		return ((double)talon.getSelectedSensorVelocity(0));
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