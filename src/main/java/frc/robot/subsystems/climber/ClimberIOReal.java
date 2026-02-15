// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.climber;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;

import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import frc.robot.Constants;
import frc.robot.generated.TunerConstants;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;

import static frc.robot.util.PhoenixUtil.tryUntilOk;

public class ClimberIOReal implements ClimberIO {
    private final TalonFX motor1;

    public final StatusSignal<Current> climbCurrent_A;
    public final StatusSignal<Voltage> climbVolts_V;
    public final StatusSignal<AngularVelocity> climbVel_rps;
    public final StatusSignal<Angle> climbPos_r;

    private final VoltageOut climbVoltOut_V;
    private final VelocityVoltage climbVelOut;
    private final PositionVoltage climbPosCtrl_deg;

    private final PIDController pid = new PIDController(ClimberConstants.kP, ClimberConstants.kI, ClimberConstants.kD);

    public ClimberIOReal()
    {
        motor1 = new TalonFX(ClimberConstants.motor1ID, TunerConstants.kCANBus);

        climbCurrent_A = motor1.getStatorCurrent();
        climbVolts_V = motor1.getMotorVoltage();
        climbVel_rps = motor1.getVelocity();
        climbPos_r = motor1.getPosition();

        var motor1Config = new TalonFXConfiguration();
        motor1Config.CurrentLimits.SupplyCurrentLimit = 82.0;
        motor1Config.CurrentLimits.SupplyCurrentLimitEnable = true;
        motor1Config.CurrentLimits.StatorCurrentLimit = 82.0;
        motor1Config.CurrentLimits.StatorCurrentLimitEnable = true;
        motor1Config.Feedback.SensorToMechanismRatio = 1.0;
        motor1Config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
        motor1Config.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        motor1Config.OpenLoopRamps.VoltageOpenLoopRampPeriod = 0.02;
        
        motor1Config.Slot0.kP = ClimberConstants.kP;
        motor1Config.Slot0.kI = ClimberConstants.kI;
        motor1Config.Slot0.kD = ClimberConstants.kD;
        motor1Config.Slot0.kS = ClimberConstants.kS;
        motor1Config.Slot0.kV = ClimberConstants.kV;

        tryUntilOk(5, () -> motor1.getConfigurator().apply(motor1Config));

        climbVoltOut_V = new VoltageOut(0).withEnableFOC(true);
        climbVelOut = new VelocityVoltage(0.0).withEnableFOC(true);

        climbPosCtrl_deg = new PositionVoltage(0.0).withEnableFOC(true);
    }

    @Override
    public void updateInputs(ClimberIOInputs inputs) {
        BaseStatusSignal.refreshAll(
                climbCurrent_A,
                climbVolts_V,
                climbVel_rps,
                climbPos_r);

        inputs.climbCurrent_A = climbCurrent_A.getValueAsDouble();
        inputs.climbVolts_V = climbVolts_V.getValueAsDouble();
        inputs.climbPos_deg = climbPos_r.getValueAsDouble() * 360.0;
        inputs.climbVel_rps = climbVel_rps.getValueAsDouble() * 360.0; 
    }

    @Override
    public void setClimbVoltage(double volts_V, double ff_V) {
        volts_V = MathUtil.clamp(volts_V + ff_V, -12.0, 12);
        motor1.setControl(climbVoltOut_V.withOutput(volts_V));
    }

    @Override
    public void setClimbVelocity(double velocity_rps) {
        double pidCalc = pid.calculate(climbVel_rps.getValueAsDouble(), velocity_rps);
        setClimbVoltage(pidCalc, 0.0);
    }

    @Override
    public void stopClimb() {
        setClimbVoltage(0.0, 0.0);
    }

    @Override
    public void climbTo(double position_deg) {
        double pos_r = position_deg / 360.0;
        motor1.setControl(climbPosCtrl_deg.withPosition(pos_r));
    }
}