package frc.robot.subsystems.Climber;


import frc.robot.subsystems.StateMachineSubsystemBase;

public class Climber extends StateMachineSubsystemBase<ClimberStates>{
    
    private ClimberIO.LogInputs input;
    private ClimberIO io;
    private ClimberStates currentState;

    public Climber(ClimberIO io){
      super("Climber");
      this.io = io;
      currentState = ClimberStates.IDLE;
    }

    @Override
    public void outputPeriodic() {
     
    }
        
    @Override
    public void inputPeriodic(){
      handleStateMachine();
    }
    

    public void handleStateMachine(){
      switch(currentState){
        case IDLE:
          io.stop();
        case RESET:
          io.stop();
        case HOLDING:
          io.stop();
        case CLIMBING:
          io.turn(1);//placeholder
          if(input.position == 1){
            queueState(ClimberStates.HOLDING);
          }
        case DESCENDING:
          io.turn(0);//placeholder
          if(input.position == 0){
            queueState(ClimberStates.IDLE);
          }
    }
  }
}
