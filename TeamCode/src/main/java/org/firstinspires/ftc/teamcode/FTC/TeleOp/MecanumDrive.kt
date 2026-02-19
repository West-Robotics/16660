package org.firstinspires.ftc.teamcode.FTC.TeleOp

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.setup.Controller;
import org.firstinspires.ftc.teamcode.FTC.mechanisms.drivetrain

@TeleOp(name = "Mecanum Drive")
class MecanumDrive: LinearOpMode(){

    override fun runOpMode(){
        val drive = drivetrain(hardwareMap, telemetry)
        val controller1 = Controller(gamepad1)

        waitForStart()

        while (opModeIsActive()){
            controller1.update()
            drive.odoUpdate()
            drive.mecanumEffort(-controller1.left_stick_y,controller1.left_stick_x,controller1.right_stick_x)
            drive.write()
            drive.addTelemetry()
            telemetry.addData("voltage",hardwareMap.voltageSensor.iterator().next().voltage)
            telemetry.update()

        }
    }



}