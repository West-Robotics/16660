package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple


@Autonomous
class Auto: LinearOpMode() {
    lateinit var leftMotor: DcMotor
    lateinit var rightMotor: DcMotor

    override fun runOpMode(){
        leftMotor = hardwareMap.get(DcMotor::class.java, "left_motor")
        rightMotor = hardwareMap.get(DcMotor::class.java, "right_motor")

        leftMotor.direction = DcMotorSimple.Direction.REVERSE
        rightMotor.direction = DcMotorSimple.Direction.FORWARD

        leftMotor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        rightMotor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER

        leftMotor.mode = DcMotor.RunMode.RUN_USING_ENCODER
        rightMotor.mode = DcMotor.RunMode.RUN_USING_ENCODER

        waitForStart()

        leftMotor.setPower(0.5)
        rightMotor.setPower(0.5)

        sleep(4000)

        leftMotor.setPower(0.0)
        rightMotor.setPower(0.0)


    }
}

