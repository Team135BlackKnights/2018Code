package org.usfirst.frc.team135.robot.util;

import org.usfirst.frc.team135.robot.subsystems.Canifier;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class Lidar_wrapper implements PIDSource
{

	PIDSourceType type = PIDSourceType.kDisplacement;
	FunctionalDoubleManager lidar;
	
	public Lidar_wrapper(FunctionalDoubleManager lidar)
	{
		this.lidar = lidar;
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
		return lidar.get();
	}
	
}
