package org.firstinspires.ftc.teamcode.pedroPathing;

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
public class IshaanTest extends NextFTCOpMode {
    public IshaanTest() {
        addComponents(
//                new SubsystemComponent(
//                        Intakenf.INSTANCE, Hoodnf.INSTANCE,
//                        Shooternf.INSTANCE, Transfernf.INSTANCE
//                ),
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

    private void buildPaths() {
        follower().setStartingPose(new Pose(28.231, 132.138, Math.toRadians(143)));

        Shoot1 = follower()
                .pathBuilder().addPath(
                        new BezierLine(
                                new Pose(28.231, 132.138),

                                new Pose(47.934, 95.071)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(143), Math.toRadians(134))

                .build();

        Intake1 = follower()
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(47.934, 95.071),
                                new Pose(43.998, 84.425),
                                new Pose(35.067, 83.934),
                                new Pose(16.404, 83.575)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Gate = follower()
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(16.404, 83.575),
                                new Pose(23.249, 76.863),
                                new Pose(16.371, 70.493)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        Shoot2 = follower()
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                new Pose(16.371, 70.493),

                                new Pose(47.700, 95.578)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(134))

                .build();

        Intake2 = follower()
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(47.700, 95.578),
                                new Pose(43.274, 59.608),
                                new Pose(29.007, 62.097),
                                new Pose(11.567, 59.628)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Shoot3 = follower()
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(11.567, 59.628),
                                new Pose(37.419, 54.926),
                                new Pose(50.293, 92.995)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(134))

                .build();

        Intake3 = follower()
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(50.293, 92.995),
                                new Pose(53.236, 30.054),
                                new Pose(26.988, 34.426),
                                new Pose(12.529, 35.096)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Shoot4 = follower()
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                new Pose(12.529, 35.096),

                                new Pose(48.264, 95.573)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(134))

                .build();

        End = follower().pathBuilder().addPath(
                        new BezierLine(
                                new Pose(48.264, 95.573),

                                new Pose(23.649, 71.914)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(134), Math.toRadians(180))

                .build();
    }



    private Command set_hood() {
        return new SequentialGroup(
                Hoodnf.INSTANCE.setHoodPos(0.32)
        );

    }

    private Command transferUpFor(double time) {
        return new ParallelGroup(
                Transfernf.INSTANCE.out(),
                new Delay(time)
        );
    }

    private Command baseState() {
        return new ParallelGroup(
                Transfernf.INSTANCE.idle(),
                Hoodnf.INSTANCE.setHoodPos(0.32)
        );
    }

    private Command autonomous() {
        return new ParallelGroup(
                //INTAKE ALWAYS ON
                Intakenf.INSTANCE.in(),

                //MAIN SEQUENCE
                new SequentialGroup(

                        //Preloads
                        new ParallelGroup(
                                new FollowPath(Shoot1, true)
                                //,

                                //baseState(),
                                //Shooternf.INSTANCE.close()
                        ),
                        //transferUpFor(2.2),


                        //SET 2
                        new ParallelGroup(
                                new SequentialGroup(
                                        new FollowPath(Intake1),
                                        new FollowPath(Shoot2, true)
                                )
                                //,
                                //Shooternf.INSTANCE.close()
                        ),
                        //transferUpFor(2.5),

                        //SET 3 Human Player
                        new ParallelGroup(
                                new SequentialGroup(
                                        new FollowPath(Intake2),
                                        new FollowPath(Shoot3, true)
                                )
                                //,
                                //Shooternf.INSTANCE.close()
                        ),
                        //transferUpFor(2.5),

                        //SET 4
                        new ParallelGroup(
                                new SequentialGroup(
                                        new FollowPath(Intake3),
                                        new FollowPath(Shoot4, true)
                                )
                                //,
                                // Shooternf.INSTANCE.close()
                        ),
                        //transferUpFor(4),
                        new FollowPath(End)



                )
        );
    }
    @Override
    public void onInit() {
        buildPaths();
        set_hood().schedule();
        //Shooternf.INSTANCE.idle();
    }
    @Override
    public void onStartButtonPressed() {
        autonomous().schedule();

        //Shooternf.INSTANCE.close();
    }

}
