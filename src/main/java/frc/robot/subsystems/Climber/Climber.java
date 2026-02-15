// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Climber;

import org.littletonrobotics.junction.Logger;

import frc.robot.Constants;
import frc.robot.subsystems.StateMachineSubsystemBase;
import frc.robot.subsystems.Climber.ClimberIO.ClimberIOInputs;
import frc.robot.subsystems.Climber.ClimberStates;

public class Climber extends StateMachineSubsystemBase<ClimberStates> {
  private ClimberIO io;
  //private ClimberIOInputsAutoLogged inputs = new ClimberIOInputsAutoLogged();
  private static Climber instance;
    /** Creates a new Climber. */
    public static Climber getInstance(){
      if (instance == null){
        switch (Constants.currentMode){
          case SIM:
            instance = new Climber(new ClimberIOSim());
            break;
          case REAL:
            instance = new Climber(new ClimberIOReal());
            break;
        }
      }
      return instance;
    }
    
    public Climber(ClimberIO io) {
      super("climber");
      this.io = io;
      queueState(ClimberStates.IDLE);
  }

  @Override
  public void inputPeriodic() {
    //io.updateInputs(inputs);
    //Logger.processInputs("Climber", inputs);
    // This method will be called once per scheduler run
  }


  public void outputPeriodic() {
  }


  @Override
  public void handleStateMachine() {
    switch (getState()){
      case IDLE: //stay at position or whatever
        break;
      case STOPPED:
        io.stopMotor();
        break;
      case MOVINGIN:
        io.moveIn();
        break;
      case MOVINGOUT:
        io.moveOut();
        break;
  }
 }
}
