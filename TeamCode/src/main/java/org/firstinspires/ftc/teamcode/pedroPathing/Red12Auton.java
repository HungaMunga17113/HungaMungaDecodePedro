package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import com.bylazar.configurables.annotations.Configurable;
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
@Configurable
public class Red12Auton extends NextFTCOpMode {
    public Red12Auton() {
        addComponents(
                new SubsystemComponent(
                        Intakenf.INSTANCE,
                        Shooternf.INSTANCE, Transfernf.INSTANCE
                ),
                new PedroComponent(Constants::createFollower),
                BulkReadComponent.INSTANCE
        );
    }

    public static class Paths {

        public PathChain Shoot1;
        public PathChain Intake1;
        public PathChain Gate;
        public PathChain Shoot2;
        public PathChain Intake2;
        public PathChain Shoot3;
        public PathChain Intake3;
        public PathChain Shoot4;
        public PathChain End;

        public void paths() {
            PedroComponent.follower().setStartingPose(new Pose(88, 8.2, Math.toRadians(90)));


            Shoot1 = PedroComponent.follower()
                    .pathBuilder()

                    .addPath(
                            new BezierLine(new Pose(116.007, 131.664), new Pose(95.842, 95.130))
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(36), Math.toRadians(49))
                    .build();
            Intake1 = PedroComponent.follower()
                    .pathBuilder()
                    .addPath(

                            new BezierCurve(
                                    new Pose(95.842, 95.130),
                                    new Pose(101.773, 83.506),
                                    new Pose(128.817, 83.743)
                            )

                    )
                    .setTangentHeadingInterpolation()
                    .build();

            Gate = PedroComponent.follower()
                    .pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(128.817, 83.743),
                                    new Pose(122.412, 76.626),
                                    new Pose(129.054, 70.932)
                            )
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(0))
                    .build();

            Shoot2 = PedroComponent.follower()
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(129.054, 70.932), new Pose(91.572, 90.860))
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(49))
                    .build();

            Intake2 = PedroComponent.follower()
                    .pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(91.572, 90.860),
                                    new Pose(100.112, 51.479),
                                    new Pose(109.364, 60.257),
                                    new Pose(134.748, 59.071)
                            )
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(0))
                    .build();

            Shoot3 = PedroComponent.follower()
                    .pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(134.748, 59.071),
                                    new Pose(95.130, 53.140),
                                    new Pose(91.809, 90.623)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(49))
                    .build();

            Intake3 = PedroComponent.follower()
                    .pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(91.809, 90.623),
                                    new Pose(89.437, 29.654),
                                    new Pose(112.448, 35.110),
                                    new Pose(134.511, 34.636)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            Shoot4 = PedroComponent.follower()
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(134.511, 34.636), new Pose(91.572, 91.097))
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(49))
                    .build();

            End = PedroComponent.follower()
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(91.572, 91.097), new Pose(120.514, 71.170))
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(49), Math.toRadians(0))
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
                                    new FollowPath(Shoot1, true),

                                    baseState(),
                                    Shooternf.INSTANCE.close()
                            ),
                            transferUpFor(2.2),


                            //SET 2
                            new ParallelGroup(
                                    new SequentialGroup(
                                            new FollowPath(Intake1),
                                            new FollowPath(Shoot2, true)
                                    ),
                                    Shooternf.INSTANCE.close()
                            ),
                            transferUpFor(2.5),

                            //SET 3 Human Player
                            new ParallelGroup(
                                    new SequentialGroup(
                                            new FollowPath(Intake2),
                                            new FollowPath(Shoot3, true)
                                    ),
                                    Shooternf.INSTANCE.close()
                            ),
                            transferUpFor(2.5),

                            //SET 4
                            new SequentialGroup(
                                    new ParallelGroup(
                                            new SequentialGroup(
                                                    new FollowPath(Intake3),
                                                    new FollowPath(Shoot4, true)
                                            ),
                                            Shooternf.INSTANCE.close()
                                    ),
                                    transferUpFor(4),
                                    new FollowPath(End)
                            )


                    )
            );
        }
        public void onInit() {
            paths();
            set_hood().schedule();
            Shooternf.INSTANCE.idle();
        }

        public void onStartButtonPressed() {
            autonomous().schedule();

            Shooternf.INSTANCE.idle();
        }
    }
}