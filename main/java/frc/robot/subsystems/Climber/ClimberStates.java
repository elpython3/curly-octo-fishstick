package frc.robot.subsystems.Climber;

import frc.robot.util.IState;

public enum ClimberStates implements IState{
    CLIMBING, // climber ascends
    HOLD, // holds pos
    DISABLED, 
    DESCENDING, // climber descends
    IDLE // case 0
}
