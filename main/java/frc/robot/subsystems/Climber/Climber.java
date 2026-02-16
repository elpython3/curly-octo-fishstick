package frc.robot.subsystems.Climber;



import org.littletonrobotics.junction.Logger;

import frc.robot.subsystems.StateMachineSubsystemBase;

public class Climber extends StateMachineSubsystemBase<ClimberStates>{
    private final ClimberIO io;
    private final ClimberInputs inputs;
    private double targetDegrees;
    public Climber(ClimberIO io, ClimberInputs inputs){
        super("Climber");
        trackToAngle(90.0);
        this.io = io;
        this.inputs = inputs;
    }
    @Override
    public void inputPeriodic(){
      io.updateInputs(inputs);
      Logger.processInputs("Climber", inputs);

    }
    @Override
    public void outputPeriodic(){
      Logger.recordOutput("Climber/ClimbTargetDegrees", targetDegrees);
    }
    
    @Override
    public void handleStateMachine(){
        switch(getState()){
            case IDLE:
                io.stopMotor();
                break;
            case DISABLED:
                break;
            case HOLD:
              io.setPosition(inputs.pos);
              if (!isValueReached(2.0)) {
              queueState(ClimberStates.CLIMBING);
              } else {
              io.turningMotor();
              }
                break;
            case CLIMBING:
            if (isValueReached(2.0)) {
                queueState(ClimberStates.HOLD);
              } else {
                io.turningMotor();
              }
                break;
            case DESCENDING:
                trackToAngle(0.0);
                queueState(ClimberStates.CLIMBING);
                break;
        }


    }

    public void setTargetAngle(double degrees) {
        targetDegrees = degrees;
    }
    
      public void trackToAngle(double degrees) {
        setTargetAngle(degrees);
        queueState(ClimberStates.CLIMBING);
      }
    
      public boolean isValueReached(double toleranceDeg) {
        return Math.abs(inputs.pos - targetDegrees) < toleranceDeg;
      }
}
