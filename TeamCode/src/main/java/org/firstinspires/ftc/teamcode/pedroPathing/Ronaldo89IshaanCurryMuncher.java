package org.firstinspires.ftc.teamcode.pedroPathing;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;
import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

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
public class Ronaldo89IshaanCurryMuncher extends NextFTCOpMode {
    public Ronaldo89IshaanCurryMuncher() {
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
    public PathChain Gate;
    public PathChain Shoot3;
    public PathChain Intake4;
    public PathChain Shoot4;
    public PathChain Intake5;
    public PathChain Shoot6;

    public void paths() {
        PedroComponent.follower().setStartingPose(new Pose(118.853, 127.393, Math.toRadians(37)));


        Shoot1 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierLine(
                                new Pose(118.853, 127.393),

                                new Pose(95.900, 95.545)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(37), Math.toRadians(45))
                .build();

        Intake1 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(95.900, 95.545),
                                new Pose(103.671, 84.491),
                                new Pose(127.699, 84.491)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Shoot2 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierLine(
                                new Pose(127.699, 84.491),

                                new Pose(95.829, 95.400)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(45))

                .build();

        Intake2 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(95.829, 95.400),
                                new Pose(98.512, 56.939),
                                new Pose(111.883, 56.939),
                                new Pose(132.427, 56.939)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Gate = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(132.427, 56.939),
                                new Pose(109.567, 56.939),
                                new Pose(126.682, 69.509)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        Shoot3 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierLine(
                                new Pose(126.682, 69.509),

                                new Pose(95.829, 95.545)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(45))

                .build();

        Intake4 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(95.829, 95.545),
                                new Pose(82.239, 34.570),
                                new Pose(133.169, 34.570)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Shoot4 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(133.169, 34.570),
                                new Pose(97.729, 97.991),
                                new Pose(95.829, 95.545)
                        )
                ).setTangentHeadingInterpolation()
                .setReversed()
                .build();

        Intake5 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(95.829, 95.545),
                                new Pose(100.002, 24.869),
                                new Pose(131.137, 13.206)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Shoot6 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(131.137, 13.206),
                                new Pose(89.124, 111.729),
                                new Pose(87.206, 109.972)
                        )
                ).setTangentHeadingInterpolation()
                .setReversed()
                .build();
    }



    private Command set_hood() {
        return new SequentialGroup(
                Hoodnf.INSTANCE.setHoodPos(0.41)
        );

    }

    private Command transferUpFor(double time) {
        return new SequentialGroup(
                Transfernf.INSTANCE.out(),
                new Delay(time),
                Transfernf.INSTANCE.idle()
        );
    }

    private Command baseState() {
        return new ParallelGroup(
                Transfernf.INSTANCE.idle(),
                Hoodnf.INSTANCE.setHoodPos(0.42)
        );
    }
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
                                baseState()
                        ),
                        new Delay(1),
                        transferUpFor(0.7),


                        //SET 2
                        new ParallelGroup(
                                new SequentialGroup(
                                        new FollowPath(Intake1),
                                        new FollowPath(Shoot2, true)
                                )
                        ),
                        new Delay(0.4),
                        transferUpFor(0.7),

                        //SET 3 Human Player
                        new ParallelGroup(
                                new SequentialGroup(
                                        new FollowPath(Intake2),
                                        new FollowPath(Gate),
                                        new Delay(0.4),
                                        new FollowPath(Shoot3, true)
                                )
                        ),
                        new Delay(0.4),
                        transferUpFor(0.7),
                        new ParallelGroup(
                                new SequentialGroup(
                                        new FollowPath(Intake4),
                                        new FollowPath(Shoot4, true)
                                )
                        ),
                        new Delay(0.4),
                        transferUpFor(0.7),
                        //SET 4
                        new SequentialGroup(
                                new SequentialGroup(
                                        new FollowPath(Intake5),
                                        new FollowPath(Shoot6, true)
                                ),
                                new Delay(0.4),
                                transferUpFor(0.75)
                        )


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

