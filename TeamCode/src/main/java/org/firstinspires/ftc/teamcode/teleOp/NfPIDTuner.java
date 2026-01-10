package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.impl.MotorEx;

import org.firstinspires.ftc.teamcode.pedroPathing.subsystems.Hoodnf;
import org.firstinspires.ftc.teamcode.pedroPathing.subsystems.Intakenf;
import org.firstinspires.ftc.teamcode.pedroPathing.subsystems.Shooternf;
import org.firstinspires.ftc.teamcode.pedroPathing.subsystems.Transfernf;

@TeleOp
public class NfPIDTuner extends NextFTCOpMode {
    public void onInit() {



    }

    public void NFPIDTUNER() {
        addComponents(
                new SubsystemComponent(Hoodnf.INSTANCE, Shooternf.INSTANCE, Transfernf.INSTANCE, Intakenf.INSTANCE),
                BulkReadComponent.INSTANCE,

                BindingsComponent.INSTANCE
        );
    }

    @Override
    public void onStartButtonPressed() {
        Shooternf.INSTANCE.close();
        Gamepads.gamepad1().rightBumper().whenBecomesTrue(
                Transfernf.INSTANCE.out()
        );
        Gamepads.gamepad1().rightTrigger().greaterThan(0.2).whenBecomesTrue(
                Intakenf.INSTANCE.in()
        );
    }
}
