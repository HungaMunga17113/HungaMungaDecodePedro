package org.firstinspires.ftc.teamcode.pedroPathing.subsystems;

import com.bylazar.configurables.annotations.Configurable;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

@Configurable
public class Shooternf implements Subsystem {
    public static final Shooternf INSTANCE = new Shooternf();
    private Shooternf() { }

    public MotorEx leftOuttake, rightOuttake;
    public MotorGroup shooter;

    private final ControlSystem closeShooterController = ControlSystem.builder()
            .velPid(2, 0, 0.001)
            .basicFF(0.002)
            .build();



    private boolean enabled = false;



    public Command close() {
        return new RunToVelocity(closeShooterController, 1250).requires(shooter);
    }



    public Command idle() {
        return new RunToVelocity(closeShooterController, 0).requires(shooter);
    }

    public Command setShooterVel(double shooterVel) {
        return new RunToVelocity(closeShooterController, shooterVel).requires(shooter);
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
        shooter.setPower(0);
    }

    @Override
    public void initialize() {
        leftOuttake = new MotorEx("leftOuttake");
        rightOuttake = new MotorEx("rightOuttake");
        leftOuttake.reverse();
        shooter = new MotorGroup(leftOuttake, rightOuttake);

        disable();
    }

    @Override
    public void periodic() {
        if (!enabled) {
            shooter.setPower(0);
            return;
        }
        shooter.setPower(closeShooterController.calculate(shooter.getState()));
    }
}