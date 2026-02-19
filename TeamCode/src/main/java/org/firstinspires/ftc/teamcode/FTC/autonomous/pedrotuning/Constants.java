package org.firstinspires.ftc.teamcode.FTC.autonomous.pedrotuning;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.FilteredPIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(9.888)
            .forwardZeroPowerAcceleration(-55.62758406063349)
            .lateralZeroPowerAcceleration(-82.50850901843515)
            .translationalPIDFCoefficients(new PIDFCoefficients(
                    0.105,
                    0,
                    0.007,
                    0.023
            ))
            .headingPIDFCoefficients(new PIDFCoefficients(
                    1.2,
                    0.0,
                    0.015,
                    0.023
            ))

            .drivePIDFCoefficients(new FilteredPIDFCoefficients(
                    0.088,
                    0.0,
                    0.001,
                    0.6,
                    0.023
            ))




            ;
    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .xVelocity(57.623991237850646)
            .yVelocity(45.60789573849656)
            .rightFrontMotorName("frontRight")
            .rightRearMotorName("backRight")
            .leftRearMotorName("backLeft")
            .leftFrontMotorName("frontLeft")
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD);

    public static PinpointConstants localizerConstants = new PinpointConstants()
            .forwardPodY(2.6378)
            .strafePodX(-1.5748)
            .distanceUnit(DistanceUnit.MM)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD);

    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 1, 1);

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pinpointLocalizer(localizerConstants)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .build();
    }
}