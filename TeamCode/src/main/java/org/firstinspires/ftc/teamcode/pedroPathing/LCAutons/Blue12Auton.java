package org.firstinspires.ftc.teamcode.pedroPathing.LCAutons;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
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
public class Blue12Auton extends NextFTCOpMode {
    public Blue12Auton() {
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
    public PathChain Gate;
    public PathChain Shoot2;
    public PathChain Intake2;
    public PathChain Shoot3;
    public PathChain Intake3;
    public PathChain Shoot4;
    public PathChain End;

        private void paths() {
            PedroComponent.follower().setStartingPose(new Pose(28.231, 132.138, Math.toRadians(143)));

            Shoot1 = PedroComponent.follower()
                    .pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(28.231, 132.138),

                                    new Pose(49.495, 93.645)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(143), Math.toRadians(134))


                    .build();

            Intake1 = PedroComponent.follower()
                    .pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(49.495, 93.645),
                                    new Pose(36.112, 89.467),
                                    new Pose(16.630, 88.500)
                            )
                    ).setTangentHeadingInterpolation()

                    .build();

            Gate = PedroComponent.follower()
                    .pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(16.630, 88.500),
                                    new Pose(30, 84.000),
                                    new Pose(30, 76.152),
                                    new Pose(16.2, 76)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                    .build();

            Shoot2 = PedroComponent.follower()
                    .pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(16.2, 76),
                                    new Pose(49.794, 93.757)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(134))

                    .build();

            Intake2 = PedroComponent.follower()
                    .pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(49.794, 93.757),
                                    new Pose(40.185, 60.715),
                                    new Pose(32.738, 62.155),
                                    new Pose(10.442, 58.891)
                            )
                    ).setTangentHeadingInterpolation()
                    .build();

            Shoot3 = PedroComponent.follower()
                    .pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(10.916, 61.738),
                                    new Pose(42.014, 54.682),
                                    new Pose(50.028, 93.895)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(134))

                    .build();

            Intake3 = PedroComponent.follower()
                    .pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(50.028, 93.895),
                                    new Pose(40.847, 38.392),
                                    new Pose(31.766, 40.527),
                                    new Pose(11.332, 37.917)
                            )
                    ).setTangentHeadingInterpolation()

                    .build();

            Shoot4 = PedroComponent.follower()
                    .pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(13.332, 40.764),

                                    new Pose(45.623, 93.580)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(134))

                    .build();

            End = PedroComponent.follower()
                    .pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(45.623, 93.580),

                                    new Pose(30.379736408566725, 77.0608072487644)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(134), Math.toRadians(180))

                    .build();
        }



    private Command set_hood() {
        return new SequentialGroup(
                Hoodnf.INSTANCE.setHoodPos(0.593)
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
                Hoodnf.INSTANCE.setHoodPos(0.593)
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
                        transferUpFor(1),


                        //SET 2
                        new ParallelGroup(
                                new SequentialGroup(
                                        new FollowPath(Intake1),
                                        new FollowPath(Gate),
                                        new Delay(0.3),
                                        new FollowPath(Shoot2, true)
                                )
                        ),
                        new Delay(0.2),
                        transferUpFor(1),

                        //SET 3 Human Player
                        new ParallelGroup(
                                new SequentialGroup(
                                        new FollowPath(Intake2),
                                        new FollowPath(Shoot3, true)
                                )
                        ),
                        new Delay(0.2),
                        transferUpFor(1),

                        //SET 4
                        new SequentialGroup(
                                new ParallelGroup(
                                        new SequentialGroup(
                                                new FollowPath(Intake3),
                                                new FollowPath(Shoot4, true)
                                        )
                                ),
                                new Delay(0.2),
                                transferUpFor(1),
                                new FollowPath(End)
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