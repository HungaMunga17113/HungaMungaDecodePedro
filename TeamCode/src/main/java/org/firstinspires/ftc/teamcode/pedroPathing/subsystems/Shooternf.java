package org.firstinspires.ftc.teamcode.pedroPathing.subsystems;

import com.bylazar.configurables.annotations.Configurable;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.positionable.SetPosition;
import dev.nextftc.hardware.powerable.SetPower;
@Configurable
public class Shooternf implements Subsystem {
    public static final Shooternf INSTANCE = new Shooternf();
    private Shooternf() { }

    public MotorEx leftOuttake;
    public MotorEx rightOuttake;
    public MotorGroup shooter;

    private static final ControlSystem closeShooter = ControlSystem.builder()
            .velPid(0.345, 0, 0.001)
            .basicFF(0.002)
            .build();

    private static final ControlSystem farShooter = ControlSystem.builder()
            .velPid(0.345, 0, 0.001)
            .basicFF(0.002)
            .build();
    private static final ControlSystem setShooter = ControlSystem.builder()
            .velPid(0.345, 0, 0.001)
            .basicFF(0.002)
            .build();
    private enum ShooterControllerMode {
        CLOSE,
        FAR,
        SET
    }

    private ShooterControllerMode currentControllerMode = ShooterControllerMode.CLOSE;
    public Command close() {
        currentControllerMode = ShooterControllerMode.CLOSE;
        return new RunToVelocity(closeShooter, 1250).requires(shooter);
    }
    public Command far() {
        currentControllerMode = ShooterControllerMode.CLOSE;
        return new RunToVelocity(farShooter, 1500).requires(shooter);    }
    public Command idle() {
        currentControllerMode = ShooterControllerMode.CLOSE;
        return new RunToVelocity(closeShooter, 0).requires(shooter);    }
    public Command setVelocity(double velocity) {
        currentControllerMode = ShooterControllerMode.SET;
        return new RunToVelocity(setShooter, velocity).requires(shooter);
    }
    @Override
    public void initialize() {
        leftOuttake = new MotorEx("leftOuttake");
        rightOuttake = new MotorEx("rightOuttake");
    }

    @Override
    public void periodic() {}
}


