package frc.robot.subsystems.Manipulator;

import org.littletonrobotics.junction.Logger;

import frc.robot.Constants;
import frc.robot.Constants.CoralConstants;
import frc.robot.subsystems.StateMachineSubsystemBase;


public class CoralManipulator extends StateMachineSubsystemBase<CoralManipulatorStates>{
    private CoralIO io;
    private static CoralManipulator instance;
    private CoralIOInputsAutoLogged inputs = new CoralIOInputsAutoLogged();

    public CoralManipulator(CoralIO io){
        super("coral manipulator");
        this.io = io;
    }

    public static CoralManipulator getInstance(){
        if (instance == null){
            switch (Constants.currentMode){
                case SIM:
                    instance = new CoralManipulator(new CoralIOSim());
                    break;
                case REAL:
                    instance = new CoralManipulator(new CoralIOReal());
                    break;
             }
        }
      return instance;
    }
    public void outputPeriodic(){

    }

    public void inputPeriodic(){
        io.updateInputs(inputs);
        Logger.processInputs("Coral Manipulator", inputs);
    }

    public void handleStateMachine(){
        switch (getState()){
            case STOPPED:
                if (io.seen()){
                    queueState(CoralManipulatorStates.INTAKING);
                }
            case INTAKING:   
                if (io.entered()){
                    queueState(CoralManipulatorStates.STOPPED);
                }else{
                    io.spinMotors(CoralConstants.intakeSpeed);
                }
            case OUTTAKING:
                if (io.seen()){
                    queueState(CoralManipulatorStates.STOPPED);
                }else{
                    io.spinMotors(CoralConstants.outtakeSpeed);
                }               
        }
    }
}
