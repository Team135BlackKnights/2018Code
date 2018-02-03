package org.usfirst.frc.team135.robot.commands;

import org.usfirst.frc.team135.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoMiddleStationLeftSwitchPlate extends CommandGroup {

    public AutoMiddleStationLeftSwitchPlate() {
    	addSequential(new AutoDriveDiagonal(RobotMap.midLeftSwitch, RobotMap.leftSwitchAngle)); //can we call the specific angle we want? Like C++
    	//addSequential(new AutoLift()); 
    	addSequential(new AutoDriveStraight(RobotMap.midLeftSwitchStraight));
    	//addSequential(new AutoCubeRelease());
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
