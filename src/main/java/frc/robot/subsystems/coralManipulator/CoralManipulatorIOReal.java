package frc.robot.subsystems.coralManipulator;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;

import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.generated.TunerConstants;

public class CoralManipulatorIOReal implements CoralManipulatorIO{
    private TalonFX m1;
    private TalonFX m2;
    private DigitalInput sensor1;
    private DigitalInput sensor2;

    private StatusSignal<Voltage> m1V;
    private StatusSignal<Current> m1A;
    private StatusSignal<AngularVelocity> m1Vel;
    
    private StatusSignal<Voltage> m2V;
    private StatusSignal<Current> m2A;
    private StatusSignal<AngularVelocity> m2Vel;

    private PIDController pid;


    public CoralManipulatorIOReal(){
        m1 = new TalonFX(0, TunerConstants.kCANBus);
        m2 = new TalonFX(1,TunerConstants.kCANBus);

        sensor1 = new DigitalInput(0);
        sensor2 = new DigitalInput(1);

        m1V = m1.getMotorVoltage();
        m1A = m1.getStatorCurrent();
        m1Vel = m1.getVelocity();
        m2V = m2.getMotorVoltage();
        m2A = m2.getStatorCurrent();
        m2Vel = m2.getVelocity();

        pid = new PIDController(40, 0, 0);
    }
    @Override
    public void updateInputs(LogInputs inputs){
        BaseStatusSignal.refreshAll(m1A,m1V,m1Vel,m2A,m2V,m2Vel);
        inputs.motor1_A = m1A.getValueAsDouble();
        inputs.motor1_V = m1V.getValueAsDouble();
        inputs.motor1_vel = m1Vel.getValueAsDouble();

        inputs.motor2_A = m2A.getValueAsDouble();
        inputs.motor2_V = m2V.getValueAsDouble();
        inputs.motor2_vel = m2Vel.getValueAsDouble();

        inputs.sensor1Input = sensor1.get();
        inputs.sensor2Input = sensor2.get();
    }

    @Override
    public void spin(double speed){
        double pidCalc = pid.calculate(m1Vel.getValueAsDouble(), speed);
        setVoltage(m1,pidCalc, 0);
        pidCalc = pid.calculate(m2Vel.getValueAsDouble(),speed);
        setVoltage(m2, pidCalc, 0);
    }
    @Override
    public void stop(){
        setVoltage(m1,0,0);
        setVoltage(m2,0,0);
    }

    public void setVoltage(TalonFX motor, double volts, double feedForward){
        volts = MathUtil.clamp(volts+feedForward, -12, 12);
        motor.setVoltage(volts);
    }

}
