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
public class RedFar9 extends NextFTCOpMode {
    public RedFar9() {
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
    public PathChain Park;

    public void paths() {
        PedroComponent.follower().setStartingPose(new Pose(86.355, 8.523, Math.toRadians(360)));


        Shoot1 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierLine(
                                new Pose(86.355, 8.523),

                                new Pose(82.093, 15.477)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(62))
                .build();

        Intake1 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(82.093, 15.477),
                                new Pose(89.397, 35.673),
                                new Pose(132.607, 38.141)
                        )
                ).setTangentHeadingInterpolation()

                .build();


        Shoot2 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierLine(
                                new Pose(132.607, 38.141),

                                new Pose(82.093, 15.477)
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
                                        1.0,
                                        HeadingInterpolator.facingPoint(144,142)
                                )
                        )
                )
                .build();
        Intake2 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(82.093, 15.477),
                                new Pose(134.355, 47.776),
                                new Pose(133.653, 10.735)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Shoot3 = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierLine(
                                new Pose(133.244, 12.515),

                                new Pose(82.093, 15.477)
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
                                        HeadingInterpolator.facingPoint(new Pose(144,142))

                                )
                        )
                )
                .build();


        Park = PedroComponent.follower()
                .pathBuilder().addPath(
                        new BezierLine(
                                new Pose(82.093, 15.477),

                                new Pose(107.796, 15.641)
                        )
                ).setTangentHeadingInterpolation()

                .build();
    }




    private Command set_hood() {
        return new SequentialGroup(
                Hoodnf.INSTANCE.setHoodPos(0.57)
        );

    }

    private Command transferUpFor(double time) {
        return new SequentialGroup(
                Transfernf.INSTANCE.outSlow(),
                new Delay(time),
                Transfernf.INSTANCE.idle()
        );
    }

    private Command baseState() {
        return new ParallelGroup(
                Transfernf.INSTANCE.idle(),
                Hoodnf.INSTANCE.setHoodPos(0.57)
        );
    }
    private Command slowShoot() {
        return new SequentialGroup(
                Transfernf.INSTANCE.outSlow(),
                new Delay(0.2),
                Transfernf.INSTANCE.idle() ,
                new Delay(0.3),
                Transfernf.INSTANCE.outSlow(),
                new Delay(0.3),
                Transfernf.INSTANCE.idle(),
                new Delay(0.3),
                Transfernf.INSTANCE.outSlow(),
                new Delay(0.8),
                Transfernf.INSTANCE.idle()

        );
    }
    private Command autonomous() {
        return new ParallelGroup(
//                //INTAKE ALWAYS ON
//                Intakenf.INSTANCE.in(),
//                Shooternf.INSTANCE.far(),
//                //MAIN SEQUENCE
                new SequentialGroup(
                        Intakenf.INSTANCE.in(),
                        Shooternf.INSTANCE.far(),
                        //Preloads
                        new ParallelGroup(
                                new FollowPath(Shoot1, true),
                                baseState()
                        ),
                        new Delay(0.8),
                        slowShoot(),
/*

                        //SET 2
                        new ParallelGroup(
                                new SequentialGroup(
                                        new FollowPath(Intake1),
                                        new FollowPath(Shoot2, true)
                                )
                        ),
                        new Delay(0.6),
                        slowShoot(),
                        //SET 3 Human Player
                        new ParallelGroup(
                                new SequentialGroup(
                                        new FollowPath(Intake2),
                                        new FollowPath(Shoot3, true)
                                )
                        ),
                        new Delay(0.6),
                        slowShoot(),

 */
                        new FollowPath(Park, true)



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