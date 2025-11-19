package org.firstinspires.ftc.teamcode.tests

import com.qualcomm.hardware.limelightvision.LLResult
import com.qualcomm.hardware.limelightvision.Limelight3A
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D
import org.firstinspires.ftc.teamcode.mechanisms.drivetrain
import org.firstinspires.ftc.teamcode.setup.Controller

@TeleOp(name="Limelighter")
class LimelightOp: LinearOpMode() {
    var integralSum = 0.0
    var runtime = ElapsedTime() as ElapsedTime
    var derivative = 0.0
    var lasterror = 0.0
    override fun runOpMode() {

        val limelight = hardwareMap.get("Limelight") as Limelight3A
        limelight.pipelineSwitch(7)
        // val imu = hardwareMap.get("imu") as IMU
        val drive = drivetrain(hardwareMap, true)
        val controller1 = Controller(gamepad1)

        var revHubOrientationOnRobot = RevHubOrientationOnRobot(
            RevHubOrientationOnRobot.LogoFacingDirection.UP,
            RevHubOrientationOnRobot.UsbFacingDirection.RIGHT
        ) as RevHubOrientationOnRobot
        // imu.initialize( IMU.Parameters(revHubOrientationOnRobot))
        waitForStart()
        limelight.start()
        runtime.reset()
        runtime.startTime()
        while (opModeIsActive()){
            controller1.update()

            // var orientation: YawPitchRollAngles = imu.robotYawPitchRollAngles
            //limelight.updateRobotOrientation(orientation.yaw)
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
                //telemetry.addData("IMU Yaw Pitch Roll", imu.robotYawPitchRollAngles)
                telemetry.update()

            }
            if (controller1.a()){
                var power = PIDController(llResult.tx,0.2,0.0,0.5)
                drive.frontRight.effort = power
                drive.frontLeft.effort = -power
                drive.backRight.effort = power
                drive.backLeft.effort = -power

            }
            if (controller1.b()){
                var power = PIDController(llResult.tx,0.2,0.0,0.5)
                drive.mecanumEffort(0.0,0.0,power)

            }
            drive.write()
        }

    }

    fun PIDController(error: Double, Kp:Double,Ki:Double,Kd:Double): Double{
        integralSum = error * runtime.seconds()
        derivative = (error-lasterror) / runtime.seconds()
        lasterror = error
        runtime.reset()
        return error*Kp + integralSum*Ki + derivative*Kd
    }
}