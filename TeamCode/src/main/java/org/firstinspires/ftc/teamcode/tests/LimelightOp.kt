package org.firstinspires.ftc.teamcode.tests

import com.qualcomm.hardware.limelightvision.LLResult
import com.qualcomm.hardware.limelightvision.Limelight3A
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.IMU
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles
import org.firstinspires.ftc.teamcode.testtele

class LimelightOp: LinearOpMode() {
    override fun runOpMode() {
        val limelight = hardwareMap.get("Limelight") as Limelight3A
        limelight.pipelineSwitch(0)
        val imu = hardwareMap.get("imu") as IMU
        var revHubOrientationOnRobot = RevHubOrientationOnRobot(
            RevHubOrientationOnRobot.LogoFacingDirection.UP,
            RevHubOrientationOnRobot.UsbFacingDirection.RIGHT
        ) as RevHubOrientationOnRobot
        imu.initialize( IMU.Parameters(revHubOrientationOnRobot))
        waitForStart()
        limelight.start()
        while (opModeIsActive()){
            var orientation: YawPitchRollAngles = imu.robotYawPitchRollAngles
            limelight.updateRobotOrientation(orientation.yaw)
            var llResult: LLResult = limelight.latestResult
            if (llResult != null && llResult.isValid){
                var botPoseMT2: Pose3D = llResult.botpose_MT2
                var botPose: Pose3D = llResult.botpose
                telemetry.addData("Target X", llResult.tx)
                telemetry.addData("Target Y", llResult.ty)
                telemetry.addData("Target Area", llResult.ta)
                telemetry.addData("BotPose", botPose.toString())
                telemetry.addData("BotPoseMT2", botPoseMT2.toString())
                telemetry.addData("LL Yaw",botPose.orientation.yaw)
                telemetry.addData("IMU Yaw Pitch Roll", imu.robotYawPitchRollAngles)
            }
        }
    }
}