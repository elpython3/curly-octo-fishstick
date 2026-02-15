package frc.robot.subsystems.coralManipulator;

import frc.robot.subsystems.StateMachineSubsystemBase;
import frc.robot.subsystems.coralManipulator.CoralManipulatorIO.LogInputs;

public class CoralManipulator extends StateMachineSubsystemBase<CoralManipulatorStates>{

    private CoralManipulatorIO io;
    private LogInputs log = new LogInputs();

    public CoralManipulator(CoralManipulatorIO io){
        super("CoralManipulator");
        this.io = io;
    }

    public void handleStateMachine(){
        switch (getState()) {
            case FULL:
                break;
            case EMPTY:
                if(log.sensor1Input){
                    queueState(CoralManipulatorStates.INTAKE);
                }
                break;

            case INTAKE:
                if(log.sensor2Input){
                    queueState(CoralManipulatorStates.FULL);
                }
                break;
            case OUTTAKE:
                if(!(log.sensor1Input&&log.sensor2Input))
                queueState(CoralManipulatorStates.EMPTY);
                break;
        }
    }

    public void outputPeriodic(){

    }
}
