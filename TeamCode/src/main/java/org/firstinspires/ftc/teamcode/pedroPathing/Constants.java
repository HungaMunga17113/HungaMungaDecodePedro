package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

//Must connect once to robot wifi using usb c to a cable
public class Constants {
    //Panels Dashboard - http://192.168.43.1:8001/
        public static FollowerConstants followerConstants = new FollowerConstants()
                .forwardZeroPowerAcceleration(-30.327367609188478)
                .lateralZeroPowerAcceleration(-55.0414278486583)
                .translationalPIDFCoefficients(new PIDFCoefficients(0.095, 0, 0.01, 0.08))
                .secondaryTranslationalPIDFCoefficients(new PIDFCoefficients(0.046,0,0.001,0.008))
                .headingPIDFCoefficients(new PIDFCoefficients(1.5, 0, 0.08, 0.05)) //1.3
                .secondaryHeadingPIDFCoefficients(new PIDFCoefficients(1.3, 0, 0.06, 0)) //1.1
                .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.08,0.0,0.0001,0.6,0.01))
                .secondaryDrivePIDFCoefficients(new FilteredPIDFCoefficients(0.06,0,0.000067,0.6,0.02))
                .centripetalScaling(0.00065)
                .useSecondaryTranslationalPIDF(true)
                .useSecondaryHeadingPIDF(true)
                .useSecondaryDrivePIDF(true)
                .mass(10.61406);


    public static PathConstraints pathConstraints = new PathConstraints(0.99, 50, 1.6, 1.4);
    public static PinpointConstants localizerConstants = new PinpointConstants()
            .forwardPodY(0)
            .strafePodX(-6.9375)
            .distanceUnit(DistanceUnit.INCH)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD);
    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .xVelocity(79.7965)
            .yVelocity(62.5067153089624)
            .rightFrontMotorName("rightFront")
            .rightRearMotorName("rightBack")
            .leftRearMotorName("leftBack")
            .leftFrontMotorName("leftFront")
            .leftFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .leftRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightRearMotorDirection(DcMotorSimple.Direction.REVERSE);

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .pinpointLocalizer(localizerConstants)
                .mecanumDrivetrain(driveConstants)
                .build();
    }
}
