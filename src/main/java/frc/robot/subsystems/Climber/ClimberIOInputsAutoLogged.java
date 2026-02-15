package frc.robot.subsystems.Climber; // <-- CHANGE to match your real package

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;


public class ClimberIOInputsAutoLogged extends ClimberIO.ClimberIOInputs implements LoggableInputs {

    @Override
    public void toLog(LogTable table) {
        table.put("Pos", pos);
        table.put("Speed", speed);
        table.put("Voltage", voltage);
        table.put("Current", current);
    }

    @Override
    public void fromLog(LogTable table) {
        pos = table.get("Pos", pos);
        speed = table.get("Speed", speed);
        voltage = table.get("Voltage", voltage);
        current = table.get("Current", current);
    }
}
