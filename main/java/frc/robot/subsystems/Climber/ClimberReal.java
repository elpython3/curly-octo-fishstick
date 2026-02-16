package frc.robot.subsystems.Climber;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;

public class ClimberReal implements ClimberIO{
    private TalonFX mainMotor;

    private StatusSignal<Voltage> volts;
    private StatusSignal<AngularVelocity> angle_Vel;
    private StatusSignal<Current> curr;
    private StatusSignal<Angle> rad;

    private double angle, velocity;
    private final VoltageOut v_Out;

    private final PositionVoltage posVoltage; // this is our object that contains the voltage needed to get to a certain pos
    private final PIDController pid = new PIDController(0, 0, 0);

    public ClimberReal(){
        mainMotor = new TalonFX(0);
        angle = 0;
        velocity = 0;
        curr = mainMotor.getStatorCurrent();
        volts = mainMotor.getMotorVoltage();
        angle_Vel = mainMotor.getVelocity();
        rad = mainMotor.getPosition();


        v_Out = new VoltageOut(0).withEnableFOC(true);
        posVoltage = new PositionVoltage(0.0).withEnableFOC(true);
    }

    @Override
    public void updateInputs(LogIn e){
        e.current = curr.getValueAsDouble();
        e.voltage = volts.getValueAsDouble();
        e.velocity = angle_Vel.getValueAsDouble() * 360; // converts to degree
        e.pos = rad.getValueAsDouble() * 360; // converts to degree
    }

    @Override
    public void stopMotor(){
        setVolts(0.0, 0.0); // stops the motor indefinitely
    }

    @Override
    public void turningMotor(){
        setPosition(angle);
        setVelocity(velocity);
    }
    @Override
    public void setVolts(double volts_V, double ff_V) {
        volts_V = MathUtil.clamp(volts_V + ff_V, -12.0, 12);
        mainMotor.setControl(v_Out.withOutput(volts_V));
    }

    @Override
    public void setVelocity(double velocity_rps) {
        double pidCalc = pid.calculate(angle_Vel.getValueAsDouble(), velocity_rps);
        setVolts(pidCalc, 0.0);
    }

    @Override
    public void setPosition(double position_deg) {
        double pos_r = position_deg / 360.0;
        mainMotor.setControl(posVoltage.withPosition(pos_r));
    }
}
