package frc.robot.subsystems.Climber;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import frc.robot.Constants;
import frc.robot.util.MTimer;

public class ClimberIOSim implements ClimberIO{
    private LogInputs log = new LogInputs();

    private final DCMotorSim motorSim;
    private final LinearSystem<N2,N1,N2> climbSystem; //effectively 2 states, 1 input, 2 outputs

    private double climbVoltage;

    private final PIDController climbPID = new PIDController(40.0, 0.0, 0.0);

    private final TrapezoidProfile.Constraints climbConstraints
     = new TrapezoidProfile.Constraints
     (Constants.ClimberConstants.maxVel, Constants.ClimberConstants. maxAcc);
    private final TrapezoidProfile climbProfile = new TrapezoidProfile(climbConstraints);

    private TrapezoidProfile.State start = new TrapezoidProfile.State(0,0);
    private TrapezoidProfile.State end = new TrapezoidProfile.State(0,0);//placeholder
    private TrapezoidProfile.State current = new TrapezoidProfile.State(0.0, 0.0);


    private final MTimer timer = new MTimer(); 

    public ClimberIOSim(){
        climbSystem = LinearSystemId.createDCMotorSystem
        (DCMotor.getKrakenX60Foc(1), 1.0,1.0);

        motorSim = new DCMotorSim(climbSystem, DCMotor.getKrakenX60Foc(1));
    }

    @Override
    public void updateInputs(LogInputs inputs){
        motorSim.update(Constants.globalDelta_s);

        inputs.voltage = climbVoltage;
        inputs.current = motorSim.getCurrentDrawAmps();
        inputs.velocity = motorSim.getAngularPositionRotations() * 360.0;
        inputs.position = motorSim.getAngularVelocityRadPerSec() * 180.0 / Math.PI;
    }

    @Override
    public void stop(){
        setVoltage(0,0);
    }

    @Override
    public void setVoltage(double volts, double feedForward) {
        volts = MathUtil.clamp(volts + feedForward, -12.0, 12);
        motorSim.setInputVoltage(volts);
    }

    @Override
    public void turn(double angle){
    
        timer.reset();


        start =
            new TrapezoidProfile.State(log.position, log.velocity);
        end =
            new TrapezoidProfile.State(angle, 0.0);

        current = climbProfile.calculate(timer.time(), start, end);

        climbPID.setSetpoint(current.position);

        double voltsToSet = climbPID.calculate(motorSim.getAngularPositionRotations());

        setVoltage(voltsToSet, 0);

    }
}

