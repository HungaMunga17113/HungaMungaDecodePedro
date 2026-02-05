package org.firstinspires.ftc.teamcode.pedroPathing.EMAutons;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.EndPose;
import org.firstinspires.ftc.teamcode.pedroPathing.subsystems.Hoodnf;
import org.firstinspires.ftc.teamcode.pedroPathing.subsystems.Intakenf;
import org.firstinspires.ftc.teamcode.pedroPathing.subsystems.Shooternf;
import org.firstinspires.ftc.teamcode.pedroPathing.subsystems.Transfernf;


import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
//
@Disabled
@Autonomous
@Configurable
public class BlueFar9EM extends NextFTCOpMode {
    public BlueFar9EM() {
        addComponents(
                new SubsystemComponent(
                        Intakenf.INSTANCE, Hoodnf.INSTANCE,
                        Shooternf.INSTANCE, Transfernf.INSTANCE
                ),
                new PedroComponent(Constants::createFollower),
                BulkReadComponent.INSTANCE
        );
    }

    public PathChain Shoot1;
    public PathChain Intake1;

    public PathChain Shoot2;
    public PathChain Intake2;
    public PathChain Shoot3;



    private void paths() {
        PedroComponent.follower().setStartingPose(new Pose(57.288, 7.742, Math.toRadians(90)));

        Shoot1 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierLine(
                                new Pose(57.288, 7.742),

                                new Pose(58.976, 18.641)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(120))

                .build();

        Intake1 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(58.976, 18.641),
                                new Pose(45.291, 38.397),
                                new Pose(9.517, 35.732)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(120), Math.toRadians(180))

                .build();

        Shoot2 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierLine(
                                new Pose(9.517, 35.732),

                                new Pose(58.976, 18.641)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(120))

                .build();

        Intake2 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(58.976, 18.641),
                                new Pose(64.608, 77.978),
                                new Pose(10.094, 54.187)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(120), Math.toRadians(180))

                .build();

        Shoot3 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierLine(
                                new Pose(10.094, 54.187),

                                new Pose(58.976, 18.641)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(120))

                .build();


    }



    private Command set_hood() {
        return new SequentialGroup(
                Hoodnf.INSTANCE.setHoodPos(0.393)
        );

    }

    private Command transferUpFor(double time) {
        return new SequentialGroup(
                Transfernf.INSTANCE.out(),
                new Delay(time),
                Transfernf.INSTANCE.idle()
        );
    }

//    private Command baseState() {
//        return new ParallelGroup(
//                Transfernf.INSTANCE.idle(),
//                Hoodnf.INSTANCE.setHoodPos(0.393)
//        );
//    }

    private Command autonomous() {
        return new ParallelGroup(
                //INTAKE ALWAYS ON
                Intakenf.INSTANCE.in(),
                Shooternf.INSTANCE.close(),
                //MAIN SEQUENCE
                new SequentialGroup(

                        //Preloads
                        new ParallelGroup(
                                new FollowPath(Shoot1, true),
                                Hoodnf.INSTANCE.setHoodPos(0.355),
                                Shooternf.INSTANCE.setShooterVel(1500)),
                        new Delay(1),
                        transferUpFor(0.76),

                        //SET 2
                        new ParallelGroup(
                                new SequentialGroup(
                                        new FollowPath(Intake1),
                                        new Delay(0.4),
                                        new FollowPath(Shoot2, true)
                                )
                        ),
                        new Delay(0.25),
                        transferUpFor(0.76),

                        //SET 3 Human Player
                        new ParallelGroup(
                                new SequentialGroup(
                                        new FollowPath(Intake2),
                                        new Delay(0.4),
                                        new FollowPath(Shoot3, true)
                                )
                        ),
                        new Delay(0.25),
                        transferUpFor(0.76)
                        )



        );
    }
    @Override
    public void onInit() {
        paths();
        set_hood().schedule();
        Shooternf.INSTANCE.disable();
    }
    @Override
    public void onStartButtonPressed() {
        Shooternf.INSTANCE.enable();

        autonomous().schedule();

    }
    @Override
    public void onStop() {
        EndPose.lastX = follower().getPose().getX();
        EndPose.lastY = follower().getPose().getY();
        EndPose.lastHeading = follower().getHeading();    }
}
