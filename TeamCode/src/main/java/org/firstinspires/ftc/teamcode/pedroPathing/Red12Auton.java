package org.firstinspires.ftc.teamcode.pedroPathing;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.pedroPathing.subsystems.Hoodnf;
import org.firstinspires.ftc.teamcode.pedroPathing.subsystems.Intakenf;
import org.firstinspires.ftc.teamcode.pedroPathing.subsystems.Shooternf;
import org.firstinspires.ftc.teamcode.pedroPathing.subsystems.Transfernf;


import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.ParallelRaceGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
//
@Autonomous
@Configurable
public class Red12Auton extends NextFTCOpMode {
    public Red12Auton() {
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
    public PathChain Intake6or7idk;
    public PathChain Intake7or6idk;
    public PathChain Shoot4;
    public PathChain Shoot5;
    public PathChain Turn;
    public PathChain End;

        public void paths() {
            PedroComponent.follower().setStartingPose(new Pose(119.34102141680395, 127.09060955518945, Math.toRadians(40)));


            Shoot1 = PedroComponent.follower()
                    .pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(119.341, 127.091),

                                    new Pose(96.092, 96.020)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(40), Math.toRadians((40)))
                    .build();
            Intake1 = PedroComponent.follower()
                    .pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(96.092, 96.020),
                                    new Pose(109.732, 82.904),
                                    new Pose(127.577, 83.921)
                            )
                    ).setTangentHeadingInterpolation()
                    .build();

            Gate = PedroComponent.follower()
                    .pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(127.577, 83.921),
                                    new Pose(113.184, 71.790),
                                    new Pose(127.577, 69.933)
                            )
                    ).setConstantHeadingInterpolation(Math.toRadians(0))
                    .build();

            Shoot2 = PedroComponent.follower()
                    .pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(127.577, 69.933),

                                    new Pose(96.092, 96.020)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians((40)))
                    .build();

            Intake2 = PedroComponent.follower()
                    .pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(96.092, 96.020),
                                    new Pose(103.150, 60.339),
                                    new Pose(114.011, 60.050),
                                    new Pose(129.540, 58.657)
                            )
                    ).setTangentHeadingInterpolation()
                    .build();

            Shoot3 = PedroComponent.follower()
                    .pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(129.540, 58.657),
                                    new Pose(102.722, 54.089),
                                    new Pose(96.092, 96.020)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians((40)))
                    .build();

            Intake3 = PedroComponent.follower()
                    .pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(96.092, 96.020),

                                    new Pose(96.092, 36.600)
                            )
                    ).setTangentHeadingInterpolation()

                    .build();

            Intake6or7idk = PedroComponent.follower()
                    .pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(96.092, 36.600),

                                    new Pose(135.901, 36.600)
                            )
                    ).setTangentHeadingInterpolation()

                    .build();


            Shoot4 = PedroComponent.follower()
                    .pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(135.901, 36.600),

                                    new Pose(96.092, 96.020)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians((40)))

                    .build();
            Intake7or6idk = PedroComponent.follower()
                    .pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(96.092, 96.020),
                                    new Pose(133.914, 49.382),
                                    new Pose(133.914, 11.216)
                            )
                    ).setTangentHeadingInterpolation()

                    .build();
            Shoot5 = PedroComponent.follower()
                    .pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(133.914, 11.216),

                                    new Pose(96.092, 95.020)
                            )
                    ).setTangentHeadingInterpolation()
                    .build();
            Turn = PedroComponent.follower()
                    .pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(96.092, 95.020),

                                    new Pose(96.092, 96.020)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(114),Math.toRadians(40))
                    .build();

            End = PedroComponent.follower()
                    .pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(96.092, 96.020),

                                    new Pose(120.072, 71.829)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians((40)), Math.toRadians(0))
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

    private Command baseState() {
        return new ParallelGroup(
                Transfernf.INSTANCE.idle(),
                Hoodnf.INSTANCE.setHoodPos(0.393)
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
                        new Delay(0.9),
                        transferUpFor(0.76),


                        //SET 2
                        new ParallelGroup(
                                new SequentialGroup(
                                        new FollowPath(Intake1),
                                        new FollowPath(Gate),
                                        new Delay(0.4),
                                        new FollowPath(Shoot2, true)
                                )
                        ),
                        new Delay(0.2),
                        transferUpFor(0.76),

                        //SET 3 Human Player
                        new ParallelGroup(
                                new SequentialGroup(
                                        new FollowPath(Intake2),
                                        new FollowPath(Shoot3, true)
                                )
                        ),
                        new Delay(0.2),
                        transferUpFor(0.76),
                        new ParallelGroup(
                                new SequentialGroup(
                                        new FollowPath(Intake3),
                                        new FollowPath(Intake6or7idk),
                                        new FollowPath(Shoot4, true)
                                )
                        ),
                        new Delay(0.2),
                        transferUpFor(0.76),
                        //SET 4
                        new SequentialGroup(
                                new ParallelGroup(
                                        new SequentialGroup(
                                                new FollowPath(Intake7or6idk),
                                                new FollowPath(Shoot5, true)
                                        )
                                ),
                                new Delay(0.2),
                                transferUpFor(0.76),
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
        EndPose.lastX = follower().getPose().getX();
        EndPose.lastY = follower().getPose().getY();
        EndPose.lastHeading = follower().getHeading();    }
}