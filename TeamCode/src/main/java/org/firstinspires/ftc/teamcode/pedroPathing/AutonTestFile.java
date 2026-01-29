package org.firstinspires.ftc.teamcode.pedroPathing;

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
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
//
@Autonomous
@Configurable
public class AutonTestFile extends NextFTCOpMode {
    public AutonTestFile() {
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
    public PathChain Gate1;
    public PathChain Shoot2;
    public PathChain Intake2;
    public PathChain Shoot3;

//    public PathChain End;

    private void paths() {
        PedroComponent.follower().setStartingPose(new Pose(26.360, 128.700, Math.toRadians(143)));

        Shoot1 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierLine(
                                new Pose(26.360, 128.700),

                                new Pose(59.141, 84.276)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(143), Math.toRadians(135))

                .build();

        Intake1 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(59.141, 84.276),
                                new Pose(63.527, 59.889),
                                new Pose(10.181, 56.503)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(135),Math.toRadians(180))

                .build();

        Gate1 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(10.181, 56.503),
                                new Pose(59.379, 64.247),
                                new Pose(16.748, 67.293)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180),Math.toRadians(90))

                .build();

        Shoot2 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(16.748, 67.293),
                                new Pose(44.097, 66.799),
                                new Pose(59.100, 84.276)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(90),Math.toRadians(180))

                .build();

        Intake2 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(59.100, 84.276),
                                new Pose(16.372, 84.027)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Shoot3 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(16.372, 84.027),
                                new Pose(59.100, 84.276)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180),Math.toRadians(135))

                .build();

//        Shoot4 = PedroComponent.follower()
//                .pathBuilder().addPath(
//                        new BezierCurve(
//                                new Pose(11.730, 59.790),
//                                new Pose(47.175, 62.816),
//                                new Pose(59.442, 84.241)
//                        )
//                ).setLinearHeadingInterpolation(Math.toRadians(180),Math.toRadians(135))
//
//                .build();
//
//        Intake4 = PedroComponent.follower()
//                .pathBuilder().addPath(
//                        new BezierCurve(
//                                new Pose(59.442, 84.241),
//                                new Pose(15.049, 84.348)
//                        )
//                ).setLinearHeadingInterpolation(Math.toRadians(135),Math.toRadians(180))
//
//                .build();
//
//        Shoot5 = PedroComponent.follower()
//                .pathBuilder().addPath(
//                        new BezierCurve(
//                                new Pose(15.049, 84.348),
//                                new Pose(59.442, 84.241)
//                        )
//                ).setLinearHeadingInterpolation(Math.toRadians(180),Math.toRadians(135))
//
//                .build();

//        Intake5 = PedroComponent.follower()
//                .pathBuilder().addPath(
//                        new BezierCurve(
//                                new Pose(59.442, 84.241),
//                                new Pose(64.386, 28.607),
//                                new Pose(12.574, 35.749)
//                        )
//                ).setLinearHeadingInterpolation(Math.toRadians(135),Math.toRadians(180))
//
//                .build();

//        End = PedroComponent.follower()
//                .pathBuilder().addPath(
//                        new BezierLine(
//                                new Pose(45.623, 93.580),
//
//                                new Pose(17.5, 71.12)
//                        )
//                ).setLinearHeadingInterpolation(Math.toRadians(134), Math.toRadians(180))
//
//                .build();
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


    private Command autonomous() {
        return new ParallelGroup(
                //INTAKE ALWAYS ON
                Intakenf.INSTANCE.in(),
                //MAIN SEQUENCE
                new SequentialGroup(
                        new ParallelGroup(
                                new FollowPath(Shoot1, true),
                                Shooternf.INSTANCE.close(),
                                Hoodnf.INSTANCE.setHoodPos(0.393),
                                transferUpFor(5)
                        )




//
//                        //SET 3 Human Player
//                        new ParallelGroup(
//                                new SequentialGroup(
//                                        new FollowPath(Intake2),
//                                        new FollowPath(Shoot3, true)
//                                )
//
////                                    Shooternf.INSTANCE.close()
//                        ),
//                        transferUpFor(1),
//
//                        //SET 4
//                        new SequentialGroup(
//                                new ParallelGroup(
//                                        new SequentialGroup(
//                                                new FollowPath(Intake3),
//                                                new FollowPath(Shoot4, true)
//                                        )
//
////                                            Shooternf.INSTANCE.close()
//                                ),
//                                transferUpFor(3)
////                                new FollowPath(End)
//                        ),
//                        new ParallelGroup(
//                                new SequentialGroup(
//                                        new FollowPath(Intake4),
//                                        new FollowPath(Shoot5, true)
//                                )
//
////                                    Shooternf.INSTANCE.close()
//                        ),
//                        transferUpFor(1)

//                        new ParallelGroup(
//                                new SequentialGroup(
//                                        new FollowPath(Intake5)
//                                        new FollowPath(Shoot5, true)
                )

//                                    Shooternf.INSTANCE.close()
//                        )


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
    public void onUpdate() {
        telemetry.addData("Left Velocity", Shooternf.INSTANCE.leftOuttake.getVelocity());
        telemetry.addData("Right Velocity", Shooternf.INSTANCE.rightOuttake.getVelocity());
        telemetry.update();
    }

}
