// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.coralManipulator;

import static frc.robot.util.PhoenixUtil.tryUntilOk;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.units.measure.AngularVelocity;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Constants;
import frc.robot.generated.TunerConstants;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.*;

public class CoralManipulatorIOReal implements CoralManipulatorIO {

  private final TalonFX motor1;
  private final TalonFX motor2;

  private final DigitalInput sensor1;
  private final DigitalInput sensor2;

  public final StatusSignal<Current> motor1Current_A;
  public final StatusSignal<Voltage> motor1Volts_V;
  public final StatusSignal<AngularVelocity> motor1Vel_rps;

  public final StatusSignal<Current> motor2Current_A;
  public final StatusSignal<Voltage> motor2Volts_V;
  public final StatusSignal<AngularVelocity> motor2Vel_rps;

  private final VoltageOut motor1VoltOut_V;
  private final VoltageOut motor2VoltOut_V;

  private static final boolean INVERT_SENSORS = false;
  private final PIDController pid = new PIDController(CoralManipulatorConstants.kP, CoralManipulatorConstants.kI, CoralManipulatorConstants.kD);


  public CoralManipulatorIOReal() {

    motor1 = new TalonFX(CoralManipulatorConstants.Motor1ID, TunerConstants.kCANBus);
    motor2 = new TalonFX(CoralManipulatorConstants.Motor2ID, TunerConstants.kCANBus);

    sensor1 = new DigitalInput(CoralManipulatorConstants.Sensor1ID);
    sensor2 = new DigitalInput(CoralManipulatorConstants.Sensor2ID);

    // Signals
    motor1Current_A = motor1.getStatorCurrent();
    motor1Volts_V = motor1.getMotorVoltage();
    motor1Vel_rps = motor1.getVelocity();

    motor2Current_A = motor2.getStatorCurrent();
    motor2Volts_V = motor2.getMotorVoltage();
    motor2Vel_rps = motor2.getVelocity();
    
    // Configs (keep similar to Climber style)
    var motor1config = new TalonFXConfiguration();
    motor1config.CurrentLimits.SupplyCurrentLimit = 60.0;
    motor1config.CurrentLimits.SupplyCurrentLimitEnable = true;
    motor1config.CurrentLimits.StatorCurrentLimit = 60.0;
    motor1config.CurrentLimits.StatorCurrentLimitEnable = true;

    motor1config.Feedback.SensorToMechanismRatio = 1.0;

    motor1config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
    motor1config.MotorOutput.NeutralMode = NeutralModeValue.Brake;

    motor1config.OpenLoopRamps.VoltageOpenLoopRampPeriod = 0.02;

    // Apply to motor1
    tryUntilOk(5, () -> motor1.getConfigurator().apply(motor1config));

    // motor2 config (copy + maybe invert differently if needed)
    var motor2config = new TalonFXConfiguration();
    motor2config.CurrentLimits.SupplyCurrentLimit = 60.0;
    motor2config.CurrentLimits.SupplyCurrentLimitEnable = true;
    motor2config.CurrentLimits.StatorCurrentLimit = 60.0;
    motor2config.CurrentLimits.StatorCurrentLimitEnable = true;

    motor2config.Feedback.SensorToMechanismRatio = 1.0;

    // If motor2 needs opposite direction, change this to Clockwise_Positive
    motor2config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
    motor2config.MotorOutput.NeutralMode = NeutralModeValue.Brake;

    motor2config.OpenLoopRamps.VoltageOpenLoopRampPeriod = 0.02;

    tryUntilOk(5, () -> motor2.getConfigurator().apply(motor2config));

    // Status update rate
    BaseStatusSignal.setUpdateFrequencyForAll(
        Constants.globalDelta_Hz,
        motor1Current_A, motor1Volts_V, motor1Vel_rps,
        motor2Current_A, motor2Volts_V, motor2Vel_rps
    );

    // Outputs
    motor1VoltOut_V = new VoltageOut(0.0).withEnableFOC(true);
    motor2VoltOut_V = new VoltageOut(0.0).withEnableFOC(true);
  }

  @Override
  public void updateInputs(CoralManipulatorIOInputs inputs) {

    BaseStatusSignal.refreshAll(
        motor1Current_A, motor1Volts_V, motor1Vel_rps,
        motor2Current_A, motor2Volts_V, motor2Vel_rps
    );

    inputs.motor1Current_A = motor1Current_A.getValueAsDouble();
    inputs.motor1Volts_V = motor1Volts_V.getValueAsDouble();
    // NOTE: This is rotor velocity in rps; your variable name says mps.
    inputs.motor1Vel_mps = motor1Vel_rps.getValueAsDouble();

    inputs.motor2Current_A = motor2Current_A.getValueAsDouble();
    inputs.motor2Volts_V = motor2Volts_V.getValueAsDouble();
    inputs.motor2Vel_mps = motor2Vel_rps.getValueAsDouble();

    boolean s1 = sensor1.get();
    boolean s2 = sensor2.get();

    inputs.sensor1Triggered = INVERT_SENSORS ? !s1 : s1;
    inputs.sensor2Triggered = INVERT_SENSORS ? !s2 : s2;
  }

  @Override
  public void setMotorVoltage(double volts_V, double ff_V) {
    volts_V = MathUtil.clamp(volts_V + ff_V, -12.0, 12.0);
    motor1.setControl(motor1VoltOut_V.withOutput(volts_V));
    motor2.setControl(motor2VoltOut_V.withOutput(volts_V));
  }

  @Override
  public void setMotorVelocity(double velocity_rps) {
    double pidCalc = pid.calculate(motor1Vel_rps.getValueAsDouble(), velocity_rps);
    setMotorVoltage(pidCalc, 0.0);
    
  }

  @Override
  public void stopMotors() {
    setMotorVoltage(0.0, 0.0);
  }
}
