package org.firstinspires.ftc.teamcode.pedroPathing.EMAutons;

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
public class Blue18EMAutonClose extends NextFTCOpMode {
    public Blue18EMAutonClose() {
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
    public PathChain Intake3;
    public PathChain Shoot4;
    public PathChain Intake45;
    public PathChain FarAdjust;
    public PathChain Shoot5;
    public PathChain Intake54;
    public PathChain Shoot6;



    private void paths() {
        PedroComponent.follower().setStartingPose(new Pose(26.360, 128.700, Math.toRadians(143)));

        Shoot1 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierLine(
                                new Pose(25.147, 127.393),

                                new Pose(48.100, 95.545)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(143), Math.toRadians(133))

                .build();

        Intake1 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(48.100, 95.545),
                                new Pose(90.388, 60.893),
                                new Pose(16.908, 62.588)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(133), Math.toRadians(180))

                .build();

//        Gate1 = PedroComponent.follower()
//                .pathBuilder().addPath(
//                        new BezierCurve(
//                                new Pose(10.791, 57.628),
//                                new Pose(48.385, 60.862),
//                                new Pose(16.498, 70.016)
//                        )
//                ).setConstantHeadingInterpolation(Math.toRadians(180))
//
//                .build();

        Shoot2 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(16.908, 62.588),
                                new Pose(74.914, 65.918),
                                new Pose(48.100, 95.545)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(133))

                .build();

        Intake2 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(48.100, 95.545),
                                new Pose(58.515, 83.562),
                                new Pose(15.881, 84.491)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(133), Math.toRadians(180))

                .build();

        Shoot3 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierLine(
                                new Pose(15.881, 84.491),

                                new Pose(48.100, 95.545)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(133))

                .build();

        Intake3 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(48.100, 95.545),
                                new Pose(69.798, 27.874),
                                new Pose(11.592, 35.296)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(133), Math.toRadians(172))

                .build();

        Shoot4 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierLine(
                                new Pose(11.592, 35.296),

                                new Pose(48.100, 95.545)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(172), Math.toRadians(133))

                .build();

        Intake45 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(48.100, 95.545),
                                new Pose(61.327, 39.628),
                                new Pose(14.326, 13.736)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(133), Math.toRadians(209))

                .build();


        Shoot5 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierLine(
                                new Pose(14.326, 13.736),

                                new Pose(48.100, 95.545)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(209), Math.toRadians(133))

                .build();

        Intake54 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(48.100, 95.545),
                                new Pose(61.327, 39.628),
                                new Pose(14.326, 13.736)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(133), Math.toRadians(209))

                .build();

        Shoot6 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierLine(
                                new Pose(14.326, 13.736),

                                new Pose(55.539, 107.614)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(209), Math.toRadians(145))

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
                        new Delay(1),
                        transferUpFor(0.76),


                        //SET 2
                        new ParallelGroup(
                                new SequentialGroup(
                                        new FollowPath(Intake1),
//                                        new FollowPath(Gate1),
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
                        transferUpFor(0.76),

                        //SET 4
                        new ParallelGroup(
                                new SequentialGroup(
                                        new FollowPath(Intake3),
                                        new FollowPath(Shoot4)
                                )
                        ),
                        new Delay(0.25),
                        transferUpFor(0.76),

                        //SET 5
                        new SequentialGroup(
                                new SequentialGroup(
                                        new FollowPath(Intake45),
                                        new Delay(0.5),
//                                        new FollowPath(FarAdjust),
                                        new FollowPath(Shoot5, true)),

                                new Delay(0.25),
                                transferUpFor(0.76)
                        ),

                        //SET 6
                        new SequentialGroup(
                                new SequentialGroup(
                                        new FollowPath(Intake54),
                                        new Delay(0.5),
//                                        new FollowPath(FarAdjust),
                                        new FollowPath(Shoot6, true)),

                                new Delay(0.25),
                                transferUpFor(0.76)

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
