package org.usfirst.frc.team135.robot.extra;

import edu.wpi.first.wpilibj.PIDOutput;

public class AngleOut implements PIDOutput
{

	public double output;
	@Override
	public void pidWrite(double output) {
		// TODO Auto-generated method stub
		this.output = output;
	}

}
