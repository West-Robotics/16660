package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.setup.Controller
import org.firstinspires.ftc.teamcode.setup.motorSetup

@TeleOp
class testtele : LinearOpMode(){
    override fun runOpMode() {
        val leftMotor = motorSetup(hardwareMap,"leftMotor", DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE)
        val rightMotor = motorSetup(hardwareMap, "rightMotor", DcMotorSimple.Direction.REVERSE,DcMotor.ZeroPowerBehavior.BRAKE)
        val controller1 = Controller(gamepad1)
        waitForStart()
        while (opModeIsActive()){
            controller1.update()
            if (controller1.b()){
                rightMotor.effort = 1.0
            } else if (controller1.y()){
                rightMotor.effort = -1.0
            } else
             {
                rightMotor.effort = 0.0
            }
            if (controller1.x()){
                leftMotor.effort = 1.0
            } else if(controller1.a()){
                leftMotor.effort = -1.0
            } else{
                leftMotor.effort = 0.0
            }
            rightMotor.toPower()
            leftMotor.toPower()
        }
    }

}