package org.firstinspires.ftc.teamcode.pedroPathing.LCAutons;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.PathChain;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

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
@Autonomous
@Configurable
public class DoubleGate12Blue extends NextFTCOpMode {
    public DoubleGate12Blue() {
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
    public PathChain Gate0;
    public PathChain Shoot2;
    public PathChain Intake2;
    public PathChain Gate;
    public PathChain Shoot3;
    public PathChain Intake67;
    public PathChain Shoot4;
    public PathChain TurnGoal1;
    public PathChain Intake89;
    public PathChain FarAdjust;
    public PathChain Shoot6;
    public PathChain Shoot7;

    public void paths() {
        PedroComponent.follower().setStartingPose(new Pose(25.14698023064251, 127.39335420098847, Math.toRadians(143)));


        Shoot1 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierLine(
                                new Pose(25.147, 127.393),

                                new Pose(48.100, 95.545)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(143), Math.toRadians(137))

                .build();

        Intake1 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(48.100, 95.545),
                                new Pose(40.329, 84.491),
                                new Pose(21.001, 84.491)
                        )
                ).setTangentHeadingInterpolation()


                .build();
        Gate = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(16.573, 56.939),
                                new Pose(34.433, 56.939),
                                new Pose(21.018, 65.509)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();
        Gate0 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(16.573, 84.491),
                                new Pose(39.038, 69.509),
                                new Pose(21.018, 69.509)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
        Shoot2 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(21.018, 69.509),
                                new Pose(48.171, 95.400)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180),Math.toRadians(137))

                .build();

        Intake2 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(48.171, 95.400),
                                new Pose(45.488, 56.939),
                                new Pose(32.117, 56.939),
                                new Pose(16.573, 56.939)
                        )
                ).setTangentHeadingInterpolation()

                .build();



        Shoot3 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(21.018, 65.509),
                                new Pose(76.602, 69.509),
                                new Pose(48.171, 95.545)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(137))

                .build();

        Intake67 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(48.171, 95.545),
                                new Pose(66.920, 34.570),
                                new Pose(16.531, 34.570)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Shoot4 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierLine(
                                new Pose(16.531, 34.570),

                                new Pose(44.138, 92.936)
                        )
                ).setHeadingInterpolation(
                        HeadingInterpolator.piecewise(
                                new HeadingInterpolator.PiecewiseNode(
                                        0,
                                        0.67,
                                        HeadingInterpolator.tangent.reverse()
                                ),
                                new HeadingInterpolator.PiecewiseNode(
                                        0.67,
                                        1,
                                        HeadingInterpolator.facingPoint(new Pose(0,142.75))

                                )
                        )
                )
                .build();
        /*
        TurnGoal1 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierLine(
                                new Pose(44.138, 92.936),

                                new Pose(48.171, 95.545)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(237), Math.toRadians(135))

                .build();
        */
        Intake89 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(44.138, 92.936),
                                //  new Pose(52.746, 12.533),
                                new Pose(12.466, 45.954),
                                new Pose(12.466, 12.533)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        FarAdjust = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(12.466, 12.533),
                                new Pose(21.681, 52.458),
                                new Pose(57.083, 8.617),
                                new Pose(12.466, 12.533)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(270), Math.toRadians(180))

                .build();

        Shoot6 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierLine(
                                new Pose(12.466, 12.533),

                                new Pose(52.916, 105.984)
                        )
                ).setTangentHeadingInterpolation()
                .setReversed()
                .build();


        Shoot7 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierLine(
                                new Pose(52.916, 105.984),

                                new Pose(60.888, 108.081)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(244), Math.toRadians(156))

                .build();
    }



    private Command set_hood() {
        return new SequentialGroup(
                Hoodnf.INSTANCE.setHoodPos(0.583)
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
                Hoodnf.INSTANCE.setHoodPos(0.583)
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
                                        new FollowPath(Intake2),
                                        new FollowPath(Gate),
                                        new Delay(0.6),
                                        new FollowPath(Shoot3, true)
                                )
                        ),
                        new Delay(0.55),
                        transferUpFor(0.7),

                        //SET 3 Human Player
                        new ParallelGroup(
                                new SequentialGroup(
                                        new FollowPath(Intake1),
                                        new FollowPath(Gate0),
                                        new Delay(0.6),
                                        new FollowPath(Shoot2, true)
                                )
                        ),
                        new Delay(0.55),
                        transferUpFor(0.7),
                        new ParallelGroup(
                                new SequentialGroup(
                                        new FollowPath(Intake67),
                                        new FollowPath(Shoot4,true)
                                        //new FollowPath(TurnGoal1,true)
                                )
                        ),
                        new Delay(0.55),
                        transferUpFor(0.7)
                        //SET 4
                        /*
                        new SequentialGroup(
                                new SequentialGroup(
                                        new FollowPath(Intake89),
                                        new Delay(0.35),
                                        new FollowPath(Shoot6),
                                        new FollowPath(Shoot7, true)),

                                new Delay(0.45),
                                transferUpFor(0.75)
                        )

                         */


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
        EndPose.endPose = follower().getPose();
        EndPose.lastX = follower().getPose().getX();
        EndPose.lastY = follower().getPose().getY();
        EndPose.lastHeading = follower().getHeading();    }
}
