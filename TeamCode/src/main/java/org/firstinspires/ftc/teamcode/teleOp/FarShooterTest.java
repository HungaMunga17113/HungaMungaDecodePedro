package org.firstinspires.ftc.teamcode.teleOp;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.robotcore.internal.system.Deadline;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.EndPose;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Configurable
@TeleOp
public class FarShooterTest extends OpMode {
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
    public static double ticksPerSecond = 1520;
    //1500 is far
    //1250 is close
    public static double servoPos = 0.335;
    //0.335 is far
    //0.393 is close
    public static double minimum = 0;
    //0 is close
    //1480 is far
    public static double transferPower = 0.9;
    //1 is close
    //0.85 is far
    double maxHood = 0.57;
    double minHood = 0.32;
    private Supplier<PathChain> pathChain;

    static final double targetX = 0;
    static final double targetY = 144;
    double minVelocity = 1200;
    double maxVelocity = 1900;
    private boolean automatedDrive;

    double minDistance = 33.941125497;
    double maxDistance = 190.91883092;
    public static PIDFCoefficients coeffs = new PIDFCoefficients(334.3, 0, 0.1, 14.6);
    //708.5, 0, 0.015, 11.7
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

    }

    public void shootTest() {
        servo.setPosition(servoPos);
        leftOuttake.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, coeffs);
        rightOuttake.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, coeffs);
        leftOuttake.setVelocity(ticksPerSecond);
        rightOuttake.setVelocity(ticksPerSecond);

        /*
        leftOuttake.setPower(outtakePower);
        rightOuttake.setPower(outtakePower);
        */
        double intakePower = 1;

        if (gamepad1.right_trigger > 0.15) {
            intake.setPower(intakePower);
        } else if (gamepad1.x) {
            intake.setPower(-intakePower);
        } else {
            intake.setPower(0);
        }
        /*
        if (ticksPerSecond<1350) {
            minimum = 0;
            maximum = 1330;
        } else {
            minimum = 1475;
            maximum = 1575;
        }

         */

        if (gamepad1.y && leftOuttake.getVelocity()>minimum) {
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
}