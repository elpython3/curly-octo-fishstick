// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.climber;

import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.inputs.LoggableInputs;

import edu.wpi.first.wpilibj.util.Color8Bit;
import frc.robot.Constants;
import frc.robot.subsystems.StateMachineSubsystemBase;

public class Climber extends StateMachineSubsystemBase<ClimberStates> {private static Climber instance;
private final ClimberIO io;
private final ClimberIO.ClimberIOInputs inputs =
    new ClimberIO.ClimberIOInputs();
private double targetDegrees=0.0;

Climber(ClimberIO io) {
    super("Climber");
    this.io = io;
    queueState(ClimberStates.IDLE);
}

public static Climber getInstance() {
    if (instance == null) {
    switch (Constants.currentMode) {
        case REAL:
        instance = new Climber(new ClimberIOReal());
        break;
        case REPLAY:
        instance = new Climber(new ClimberIO() {});
        break;
        default:
        break;
    }
    }
    return instance;
}

@Override
public void handleStateMachine() {switch (getState()) {
    case DISABLED:
        io.stopClimb();
        break;
    case IDLE:
        io.stopClimb();
        break;
    case MOVING:
        if ((inputs.climbPos_deg - targetDegrees) <= 1.0) 
        queueState(ClimberStates.WAITING);
        else 
        io.climbTo(targetDegrees);
        
        break;
    case WAITING:
        if ((inputs.climbPos_deg - targetDegrees) > 1.0) 
        queueState(ClimberStates.MOVING);
        else
        io.climbTo(targetDegrees);
        
        break;
        
    default:
        io.stopClimb();
        break;
    }
}

public void inputPeriodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Climber", (LoggableInputs) inputs);
}
@Override
protected void outputPeriodic() {
    Logger.recordOutput("Climber/ClimbTargetDegrees", targetDegrees);
}


}
