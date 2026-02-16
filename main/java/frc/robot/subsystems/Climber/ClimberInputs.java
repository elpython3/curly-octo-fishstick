package frc.robot.subsystems.Climber;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public class ClimberInputs extends ClimberIO.LogIn implements LoggableInputs{
   @Override
  public void toLog(LogTable table) {
    table.put("pos", pos);
    table.put("velocity", velocity);
    table.put("voltage", voltage);
    table.put("current", current);
  }

  @Override
  public void fromLog(LogTable table) {
    voltage = table.get("voltage", voltage);
    pos  = table.get("pos", pos);
    current  = table.get("current", current);
    velocity = table.get("velocity", velocity);
  }

  
}
