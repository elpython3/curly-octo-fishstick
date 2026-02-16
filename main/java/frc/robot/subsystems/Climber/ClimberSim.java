package frc.robot.subsystems.Climber;

import java.util.function.IntPredicate;

import org.ejml.masks.FMaskPrimitive;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class ClimberSim implements ClimberIO{
    private ClimberInputs inputs = new ClimberInputs();
    private DCMotorSim fakeMotor;
    private final LinearSystem<N2, N1, N2> plant;
    private double applyVolts = 0;

    public ClimberSim(){
        plant = LinearSystemId.createDCMotorSystem(DCMotor.getKrakenX60(1), 1.0, 1.0);
        fakeMotor = new DCMotorSim(plant, DCMotor.getKrakenX60(1));
    }
    
    @Override
    public void updateInputs(LogIn e ){
    
        inputs.voltage = applyVolts;
        inputs.current = fakeMotor.getCurrentDrawAmps();
        inputs.pos = fakeMotor.getAngularPositionRotations() * 360.0;
        inputs.velocity = fakeMotor.getAngularVelocityRadPerSec() * 180.0 / Math.PI;
    }
    

    @Override
    public void stopMotor(){
        setVolts(0, 0);
    }

    @Override
    public void setVolts(double volts_V, double ff_V) {
        applyVolts = MathUtil.clamp(volts_V + ff_V, -12.0, 12);
        fakeMotor.setInputVoltage(applyVolts);
    }


}
