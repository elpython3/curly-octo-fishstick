package frc.robot.subsystems.Climber;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.Constants;
import frc.robot.Constants.ClimberConstants;

public class ClimberIOReal implements ClimberIO{
    private TalonFX climbMotor;
    private PositionVoltage request;
    public ClimberIOReal(){
        climbMotor = new TalonFX(0);
        TalonFXConfiguration config = new TalonFXConfiguration();
        config.Slot0.kP = ClimberConstants.kP;
        config.Slot0.kI = ClimberConstants.kI;
        config.Slot0.kD = ClimberConstants.kD;
        climbMotor.getConfigurator().apply(config);

        request = new PositionVoltage(0).withSlot(0);
    }

    public void updateInputs(ClimberIOInputs inputs){
        inputs.pos = climbMotor.getPosition().getValueAsDouble();
        inputs.speed = climbMotor.getVelocity().getValueAsDouble();
        inputs.voltage = climbMotor.getMotorVoltage().getValueAsDouble();
        inputs.current = climbMotor.getStatorCurrent().getValueAsDouble();
    }
    public void stopMotor(){
        climbMotor.setControl(request.withPosition(0));
    }

    public void moveIn(){
        climbMotor.setControl(request.withPosition(ClimberConstants.inAngle));
    }

    public void moveOut(){
        climbMotor.setControl(request.withPosition(ClimberConstants.outAngle));
    }
}
