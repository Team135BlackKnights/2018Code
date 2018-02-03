package org.usfirst.frc.team135.robot.util;

import edu.wpi.first.wpilibj.PIDOutput;

public class PIDOut implements PIDOutput
{

	public double output = 0.0;
	@Override
	public void pidWrite(double output) {
		// TODO Auto-generated method stub
		this.output = output;
	}

}