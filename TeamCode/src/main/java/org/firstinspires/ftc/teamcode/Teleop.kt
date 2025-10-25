package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import org.firstinspires.ftc.teamcode.setup.Controller;
import org.firstinspires.ftc.teamcode.mechanisms.drivetrain
import kotlin.math.max
import kotlin.math.abs

@TeleOp(name = "TestTele")
class Teleop: LinearOpMode(){

    override fun runOpMode(){
        val drive = drivetrain(hardwareMap,true)
        val controller1 = Controller(gamepad1)

        waitForStart()

        while (opModeIsActive()){
            controller1.update()
            drive.mecanumEffort(-controller1.left_stick_y,controller1.left_stick_x,controller1.right_stick_x)
            drive.runMotors()
            telemetry.addData("voltage",hardwareMap.voltageSensor.iterator().next().voltage)
            telemetry.addData("frontleft",drive.frontLeft.motor.getCurrent(CurrentUnit.AMPS))
            telemetry.update()

        }
    }



}