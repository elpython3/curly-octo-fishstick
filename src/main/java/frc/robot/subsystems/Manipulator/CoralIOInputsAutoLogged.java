package frc.robot.subsystems.Manipulator; 

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;


public class CoralIOInputsAutoLogged extends CoralIO.CoralIOInputs implements LoggableInputs {

    @Override
    public void toLog(LogTable table) {
        table.put("Proximity1", proximity1);
        table.put("Proximity2", proximity2);
        table.put("Motor1Speed", motor1speed);
        table.put("Motor2Speed", motor2speed);
    }

    @Override
    public void fromLog(LogTable table) {
        proximity1 = table.get("Proximity1", proximity1);
        proximity2 = table.get("Proximity2", proximity2);
        motor1speed = table.get("Motor1Speed", motor1speed);
        motor2speed = table.get("Motor2Speed", motor2speed);
    }
}
