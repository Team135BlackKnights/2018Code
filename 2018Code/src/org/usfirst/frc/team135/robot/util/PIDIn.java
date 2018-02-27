package org.usfirst.frc.team135.robot.util;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class PIDIn implements PIDSource
{

	private FunctionalDoubleManager _source;
	private PIDSourceType _pidSourceType;
	public PIDIn(FunctionalDoubleManager source, PIDSourceType pidSource)
	{
		this._source = source;
		this._pidSourceType = pidSource;
	}
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSourceType) 
	{	
		this._pidSourceType = pidSourceType;
	}

	@Override
	public PIDSourceType getPIDSourceType() 
	{
		return this._pidSourceType;
	}

	@Override
	public double pidGet() 
	{
		return this._source.get();
	}

}
