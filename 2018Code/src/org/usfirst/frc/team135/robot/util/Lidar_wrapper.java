package org.usfirst.frc.team135.robot.util;

import org.usfirst.frc.team135.robot.subsystems.Canifier;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class Lidar_wrapper implements PIDSource
{

	PIDSourceType type = PIDSourceType.kDisplacement;
	Canifier device;
	
	public Lidar_wrapper(Canifier canifier)
	{
		device = canifier;
	}
	
	@Override
	public void setPIDSourceType( PIDSourceType pidSource) {
		type = pidSource;

	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public double pidGet() {
		// TODO Auto-generated method stub
		return device.ReadLidarInches();
	}
	
}
