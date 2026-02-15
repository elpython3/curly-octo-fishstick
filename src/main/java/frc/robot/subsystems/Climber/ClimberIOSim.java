package frc.robot.subsystems.Climber;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import frc.robot.Constants;
import frc.robot.Constants.ClimberConstants;

public class ClimberIOSim implements ClimberIO {

    private final LinearSystem plant;
    private final DCMotorSim climbMotor;
    private double volts;
    private final PIDController pid;
    public ClimberIOSim(){
        pid = new PIDController(ClimberConstants.kP, ClimberConstants.kI, ClimberConstants.kD);

        plant = LinearSystemId.createDCMotorSystem(DCMotor.getKrakenX60(1),
        1, // random values
        1
        );
        climbMotor = new DCMotorSim(plant, DCMotor.getKrakenX60(1));
        volts = 0;
    }

    public void updateInputs(ClimberIOInputs inputs){
        climbMotor.update(Constants.globalDelta_s);

        inputs.pos = climbMotor.getAngularPositionRotations();
        inputs.speed = climbMotor.getAngularVelocityRPM();
        inputs.current = climbMotor.getCurrentDrawAmps(); 
        inputs.voltage = volts;  
    }

    public void moveIn(){
        volts = MathUtil.clamp(pid.calculate(climbMotor.getAngularPositionRotations(), ClimberConstants.inAngle), -12.0, 12.0);
        climbMotor.setInputVoltage(volts);
    }

    public void moveOut(){
        volts = MathUtil.clamp(pid.calculate(climbMotor.getAngularPositionRotations(), ClimberConstants.outAngle), -12.0, 12.0);
        climbMotor.setInputVoltage(volts);
    }

    public void stopMotor(){
        climbMotor.setInputVoltage(0);
    }
    
}
