package frc.robot.subsystems.Climber;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.units.measure.AngularVelocity;

import frc.robot.Constants;

public class ClimberIOReal implements ClimberIO{

    private StatusSignal<Voltage> motorVoltage;
    private StatusSignal<Current> motorCurrent;
    private StatusSignal<AngularVelocity> motorVelocity;
    
    private PositionVoltage posWithV;
    private VoltageOut outV;

    private TalonFX motor = new TalonFX(0);

    public ClimberIOReal(){
        outV = new VoltageOut(0).withEnableFOC(true);
        posWithV = new PositionVoltage(0.0).withEnableFOC(true);
        motorCurrent = motor.getStatorCurrent();
        motorVoltage = motor.getMotorVoltage();
        motorVelocity = motor.getVelocity();
    }

    @Override
    public void updateInputs(LogInputs inputs){
        BaseStatusSignal.refreshAll(
            motorCurrent,
            motorVoltage,
            motorVelocity);
        inputs.current = motorCurrent.getValueAsDouble();
        inputs.voltage = motorVoltage.getValueAsDouble();
        inputs.velocity = motorVelocity.getValueAsDouble() * 360.0;
        inputs.position = motor.getPosition().getValueAsDouble();
    }

    @Override
    public void turn(double angle){
        double position = angle/360.0;
        motor.setControl(posWithV.withPosition(position));
    }

    @Override
    public void goToEncoder(){
        turn(Constants.ClimberConstants.climberResetVal);
    }

    @Override
    public void stop(){
        setVoltage(0.0,0.0);
    }

    @Override
    public void setVoltage(double volts, double feedForward) {
        volts = MathUtil.clamp(volts + feedForward, -12.0, 12);
        motor.setControl(outV.withOutput(volts));
    }

}
