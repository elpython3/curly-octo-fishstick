// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.coralManipulator;

import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.inputs.LoggableInputs;

import edu.wpi.first.wpilibj.util.Color8Bit;
import frc.robot.Constants;
import frc.robot.subsystems.StateMachineSubsystemBase;

public class CoralManipulator extends StateMachineSubsystemBase<CoralManipulatorStates> {
  private static CoralManipulator instance;
  private final CoralManipulatorIO io;
  private final CoralManipulatorIO.CoralManipulatorIOInputs inputs =
    new CoralManipulatorIO.CoralManipulatorIOInputs();
  
  /** Creates a new CoralManipulator. */
  public CoralManipulator(CoralManipulatorIO io) {
    super("CoralManipulator");
    this.io = io;
    queueState(CoralManipulatorStates.IDLE);
  }

  public static CoralManipulator getInstance() {
    if (instance == null) {
      switch (Constants.currentMode) {
        case REAL:
          instance = new CoralManipulator(new CoralManipulatorIOReal());
          break;
        case REPLAY:
          instance = new CoralManipulator(new CoralManipulatorIO() {});
          break;
        default:
          break;
      }
    }
    return instance;
  }

  @Override
  protected void inputPeriodic() {
    io.updateInputs(inputs);
    Logger.processInputs("CoralManipulator", (LoggableInputs) inputs);
  }

  @Override
  public void handleStateMachine() {
    switch (getState()) {
      case DISABLED:
        io.stopMotors();
        break;
      case IDLE:
        io.stopMotors();
        //Mode 1 detection
        if ((inputs.sensor1Triggered && !inputs.sensor2Triggered) || (!inputs.sensor1Triggered && inputs.sensor2Triggered)) {
          queueState(CoralManipulatorStates.MODE1MOVING);
        }
        break;
      case MODE1MOVING:
        if (inputs.sensor1Triggered && inputs.sensor2Triggered){
          io.stopMotors();
          queueState(CoralManipulatorStates.IDLE);}
        break;
      case MODE2MOVING:
        if (!inputs.sensor1Triggered && !inputs.sensor2Triggered) {
          io.stopMotors();
          queueState(CoralManipulatorStates.IDLE);
        }
        break;
      default:
        io.stopMotors();
        break;
    }
  }

  @Override
  protected void outputPeriodic() {
    Logger.recordOutput("CoralManipulator/Sensor1", inputs.sensor1Triggered);
    Logger.recordOutput("CoralManipulator/Sensor2", inputs.sensor2Triggered);
  }
}
