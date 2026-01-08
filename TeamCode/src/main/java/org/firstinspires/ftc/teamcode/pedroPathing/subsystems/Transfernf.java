package org.firstinspires.ftc.teamcode.pedroPathing.subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;

public class Transfernf implements Subsystem {
    public static final Transfernf INSTANCE = new Transfernf();
    private Transfernf() { }

    public MotorEx transfer;

    public enum transferStates {
        IN (1.0),
        IDLE (0),
        OUT (-1.0);

        public final double transferState;
        transferStates(double state) {
            this.transferState = state;
        }
        public double getState() {
            return transferState;
        }
    }

    public Command in() {
        return new SetPower(transfer, Transfernf.transferStates.IN.getState());
    }
    public Command idle() {
        return new SetPower(transfer, Transfernf.transferStates.IDLE.getState());
    }
    public Command out() {
        return new SetPower(transfer, Transfernf.transferStates.OUT.getState());
    }

    @Override
    public void initialize() {
        transfer = new MotorEx("transfer");
    }

    @Override
    public void periodic() {}
}


