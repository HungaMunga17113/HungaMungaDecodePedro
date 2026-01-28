package org.firstinspires.ftc.teamcode.teleOp;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.pedroPathing.EndPose;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.pedropathing.paths.PathChain;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Configurable
@TeleOp
public class MyMomsKindaHomeless extends OpMode {
    Deadline gamepadRateLimit = new Deadline(250, TimeUnit.MILLISECONDS);
    private Follower follower;
    //Sloth
    DcMotor intake;
    DcMotor transfer;
    DcMotorEx leftOuttake, rightOuttake;
    DcMotor leftFront, leftBack, rightFront, rightBack;
    public Servo servo;
    //Initialize Variables
    /*
    (Button) Initialize Period, before you press start on your program.
     */
    ElapsedTime transferTime = new ElapsedTime();
    public static double ticksPerSecond = 1225;
    //1500 is far
    //1250 is close
    public static double servoPos = 0.395;
    //0.335 is far
    //0.393 is close
    public static double minimum = 0;
    //0 is close
    //1480 is far
    public static double transferPower = 0.775;
    //1 is close
    //0.85 is far
    double maxHood = 0.58;
    double minHood = 0.31;
    private Supplier<PathChain> pathChain;

    static final double targetX = 0;
    static final double targetY = 144;
    double minVelocity = 935;
    double maxVelocity = 1765;
    private boolean automatedDrive;

    double minDistance = 33.941125497;
    double maxDistance = 160.91883092;
    public static PIDFCoefficients coeffs = new PIDFCoefficients(465, 0, 0.02, 11.7);
    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        //follower.setStartingPose(endPose == null ? new Pose() : endPose);
        //follower.setPose(new Pose(EndPose.lastX, EndPose.lastY, EndPose.lastHeading));
        follower.setStartingPose(EndPose.endPose);
        follower.setStartingPose(new Pose(30.379736408566725, 77.0608072487644, Math.toRadians(180)));
        follower.update();
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        leftFront  = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack   = hardwareMap.get(DcMotor.class, "leftBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack  = hardwareMap.get(DcMotor.class, "rightBack");

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.FORWARD);
        //set hardware map names (aka what the controller understands)
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        transfer = hardwareMap.get(DcMotorEx.class, "transfer");

        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        transfer.setDirection(DcMotorSimple.Direction.REVERSE);

        //0.44 low limit
        //0.1 high limit
        leftOuttake = hardwareMap.get(DcMotorEx.class, "leftOuttake");
        rightOuttake = hardwareMap.get(DcMotorEx.class, "rightOuttake");

        leftOuttake.setDirection(DcMotorSimple.Direction.REVERSE);
        rightOuttake.setDirection(DcMotorSimple.Direction.FORWARD);

        leftOuttake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightOuttake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        servo = hardwareMap.get(Servo.class, "Axon");
    }
    @Override
    public void start() {
        follower.startTeleopDrive(true);
    }
    @Override
    public void loop() {
        follower.update();
        Drive();
        shootTest();
    }
    private void Drive() {
        double slowModeMultiplier = 1 - (0.6 * gamepad1.left_trigger);
        double robotX = follower.getPose().getX();
        double robotY = follower.getPose().getY();
        double alignX = targetX + robotX;
        double alignY = targetY - robotY;
        double angle = Math.atan(alignX/alignY);
        double turnTowards =  85+Math.toDegrees(angle);
        double max;

            double axial = -gamepad1.left_stick_y;
            double lateral = gamepad1.left_stick_x;
            double yaw = gamepad1.right_stick_x;
            double drivePower = 0.95 - (0.6 * gamepad1.left_trigger);

            double leftFrontPower = axial + lateral + yaw;
            double rightFrontPower = axial - lateral - yaw;
            double leftBackPower = axial - lateral + yaw;
            double rightBackPower = axial + lateral - yaw;

            max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));

            if (max > 1.0) {
                leftFrontPower /= max;
                rightFrontPower /= max;
                leftBackPower /= max;
                rightBackPower /= max;
            }
            rightFront.setPower(rightFrontPower*drivePower);
            rightBack.setPower(rightBackPower*drivePower);
            leftFront.setPower(leftFrontPower*drivePower);
            leftBack.setPower(leftBackPower*drivePower);

        /*
        //Automated PathFollowing
        if (gamepad1.a) {
            follower.turnToDegrees(turnTowards);

            /*
            pathChain = () -> follower.pathBuilder() //Lazy Curve Generation
                    .addPath(new Path(new BezierLine(follower::getPose, new Pose(follower.getPose().getX(),follower.getPose().getY()))))
                    .setHeadingInterpolation(
                            HeadingInterpolator.linearFromPoint(
                                    () -> follower.getPose() != null
                                            ? follower.getPose().getHeading()
                                            : 0,
                                    Math.toRadians(turnTowards),
                                    0.2))
                    .build();
            follower.followPath(pathChain.get());


        } else {
            follower.breakFollowing();
        }
        */
    }

    public void shootTest() {
        double robotX = follower.getPose().getX();
        double robotY = follower.getPose().getY();
        double dx = targetX - robotX;
        double dy = targetY - robotY;
        double distance = Math.hypot(dx, dy);


        double hoodPosition = Range.clip(
                maxHood -
                        (distance - minDistance) * (maxHood - minHood) / (maxDistance - minDistance),
                minHood,
                maxHood
        );

        servo.setPosition(hoodPosition);

        double shooterVelocity = Range.clip(
                minVelocity +
                        (distance - minDistance) * (maxVelocity - minVelocity) / (maxDistance - minDistance),
                minVelocity,
                maxVelocity
        );

        /*
        double shooterVelocity =
                0.8 * distance * distance +
                        20 * distance +
                        1600;
         */
        if (shooterVelocity>1350) {
            minimum=leftOuttake.getVelocity()-40;
        }else{
            minimum=0;
        }
        leftOuttake.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, coeffs);
        rightOuttake.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, coeffs);
        leftOuttake.setVelocity(shooterVelocity);
        rightOuttake.setVelocity(shooterVelocity);

        double intakePower = 1;
        if (gamepad1.right_trigger > 0.15) {
            intake.setPower(intakePower);
        } else if (gamepad1.x) {
            intake.setPower(-intakePower);
        } else {
            intake.setPower(0);
        }





        if (gamepad1.y && leftOuttake.getVelocity()>minimum) {
            transferTime.reset();
            transfer.setPower(transferPower);

        } else if (gamepad1.right_bumper) {
            transfer.setPower(-transferPower);
        } else {
            transfer.setPower(0);
        }
        telemetry.addData("Ticks/s", ticksPerSecond);
        telemetry.addData("Left Velocity", leftOuttake.getVelocity());
        telemetry.addData("Right Velocity", rightOuttake.getVelocity());
        telemetry.addData("Error", ticksPerSecond-leftOuttake.getVelocity());
        telemetry.update();
    }
    public void onStop(){

    }
}

