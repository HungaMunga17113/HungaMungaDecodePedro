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
public class RedClose15LC extends NextFTCOpMode {
    public RedClose15LC() {
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
    public PathChain Shoot7;

    public void paths() {
        PedroComponent.follower().setStartingPose(new Pose(119.34102141680395, 127.09060955518945, Math.toRadians(37)));


        Shoot1 = PedroComponent.follower().pathBuilder().addPath(
                        new BezierLine(
                                new Pose(119.34102141680395, 127.09060955518945),

                                new Pose(95.900, 95.545)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(37), Math.toRadians(43))
                .build();

        Intake1 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(95.900, 95.545),
                                new Pose(103.671, 84.491),
                                new Pose(122.999, 84.491)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Shoot2 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierLine(
                                new Pose(122.999, 84.491),

                                new Pose(95.829, 95.400)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(42))

                .build();

        Intake2 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(95.829, 95.400),
                                new Pose(98.512, 56.939),
                                new Pose(111.883, 56.939),
                                new Pose(127.427, 56.939)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Gate = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(127.427, 56.939),
                                new Pose(109.567, 56.939),
                                new Pose(122.982, 69.509)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        Shoot3 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierLine(
                                new Pose(122.982, 69.509),

                                new Pose(95.829, 95.545)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(43))

                .build();

        Intake4 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(95.829, 95.545),
                                new Pose(77.080, 34.570),
                                new Pose(127.469, 34.570)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Shoot4 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierLine(
                                new Pose(127.469, 34.570),

                                new Pose(99.862, 92.936)
                        )
                ).setHeadingInterpolation(
                        HeadingInterpolator.piecewise(
                                new HeadingInterpolator.PiecewiseNode(
                                        0,
                                        0.7,
                                        HeadingInterpolator.tangent.reverse()
                                ),
                                new HeadingInterpolator.PiecewiseNode(
                                        0.7,
                                        1,
                                        HeadingInterpolator.facingPoint(new Pose(144,140.5))

                                )
                        )
                )
                .build();

        Intake5 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(99.862, 92.936),
                                new Pose(131.534, 45.700),
                                new Pose(131.534, 12.533)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Shoot6 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierLine(
                                new Pose(131.534, 12.533),

                                new Pose(91.084, 105.984)
                        )
                ).setTangentHeadingInterpolation()
                .setReversed()
                .build();

        Shoot7 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierLine(
                                new Pose(91.084, 105.984),

                                new Pose(86.112, 111.081)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(-64), Math.toRadians(22))

                .build();
    }



    private Command set_hood() {
        return new SequentialGroup(
                Hoodnf.INSTANCE.setHoodPos(0.62)
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
                Hoodnf.INSTANCE.setHoodPos(0.62)
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
                        new Delay(0.55),
                        transferUpFor(0.7),

                        //SET 3 Human Player
                        new ParallelGroup(
                                new SequentialGroup(
                                        new FollowPath(Intake2),
                                        new FollowPath(Gate),
                                        new Delay(0.5),
                                        new FollowPath(Shoot3, true)
                                )
                        ),
                        new Delay(0.55),
                        transferUpFor(0.7),
                        new ParallelGroup(
                                new SequentialGroup(
                                        new FollowPath(Intake4),
                                        new FollowPath(Shoot4,true)
                                        //new FollowPath(TurnGoal1,true)
                                )
                        ),
                        new Delay(0.55),
                        transferUpFor(0.7),
                        //SET 4
                        new SequentialGroup(
                                new SequentialGroup(
                                        new FollowPath(Intake5),
                                        new Delay(0.35),
                                        new FollowPath(Shoot6),
                                        new FollowPath(Shoot7, true)),

                                new Delay(0.45),
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
        EndPose.endPose = follower().getPose();
        EndPose.lastX = follower().getPose().getX();
        EndPose.lastY = follower().getPose().getY();
        EndPose.lastHeading = follower().getHeading();    }
}
