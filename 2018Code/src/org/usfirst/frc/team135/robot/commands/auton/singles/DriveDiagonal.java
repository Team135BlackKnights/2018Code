package org.usfirst.frc.team135.robot.commands.auton.singles;

import org.usfirst.frc.team135.robot.Robot;
import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.RobotMap.COMPETITION;
import org.usfirst.frc.team135.robot.util.FunctionalDoubleManager;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class DriveDiagonal extends InstantCommand {

	//private PIDController _driveController;

	//private PIDIn pidIn;
	//private PIDOut buffer;

	private FunctionalDoubleManager _angleSensor;
	private double _power;
	private double _angle;

	private double _timeout;

	private boolean _searching = false;

	public DriveDiagonal(double power, double initAngle, FunctionalDoubleManager angleSensor, boolean searching, double timeout) {
		super();
		this._power = power;
		this._angle =  initAngle + 90;
		this._angleSensor = angleSensor;
		this._timeout = timeout;

		this._searching = searching;
		
		Robot.camera.setTrackingMode(COMPETITION.CAMERA.REFLECTIVE_TAPE_MODE);

	}

	 protected void initialize() {
		Timer timer = new Timer();
		timer.start();
		
		while (DriverStation.getInstance().isAutonomous() && timer.get() < this._timeout && Robot.ultrasonic.getRightSonarValue() > 5) 
		{
			System.out.println(this._angleSensor.get());
			if (this._searching == true) 
			{
				double x = this._power * Math.cos(this._angle * (Math.PI / 180.0));
				double y = this._power * Math.sin(this._angle * (Math.PI / 180.0));

				while ((!Robot.camera.isTargetVisible() || this._angleSensor.get() > 0 && Robot.ultrasonic.getLeftSonarValue() < 40) 
						&& timer.get() < this._timeout) 
				{
					Robot.drivetrain.driveCartesian(x, y, 0);
				}

				this._searching = false;
			}

			if (this._angleSensor.get() == RobotMap.COMPETITION.CAMERA.INVALID_ANGLE) 
			{
				while (Robot.ultrasonic.getRightSonarValue() > 5 && timer.get() < this._timeout) 
				{
					Robot.drivetrain.driveCartesian(0, .6, 0);
				}
				
				break;
			}

			this._angle = this._angleSensor.get();
			
			/* if (this._searching == false)
			{
				this._angle *= 1.05;
			}*/

			double x = this._power * Math.cos(this._angle * (Math.PI / 180.0));
			double y = this._power * Math.sin(this._angle * (Math.PI / 180.0));

			Robot.drivetrain.driveCartesian(x, y, 0);
		}

		Robot.drivetrain.stopMotors();

	}

}
