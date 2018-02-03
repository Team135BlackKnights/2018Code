package org.usfirst.frc.team135.robot.util;

import org.usfirst.frc.team135.robot.subsystems.NavX;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class NavX_wrapper implements PIDSource
{
	PIDSourceType type = PIDSourceType.kDisplacement;
	NavX device;
	
	public NavX_wrapper(NavX navx)
	{
		device = navx;
	}
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
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
		return device.getFusedAngle();
	}

}
