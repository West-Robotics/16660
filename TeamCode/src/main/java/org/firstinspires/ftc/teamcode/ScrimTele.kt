package org.firstinspires.ftc.teamcode

import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import org.firstinspires.ftc.teamcode.mechanisms.drivetrain
import org.firstinspires.ftc.teamcode.mechanisms.flywheels
import org.firstinspires.ftc.teamcode.setup.Controller


@TeleOp(name="ScrimTeleOp1Controller")
class ScrimTele : LinearOpMode(){

    enum class OutTake {
        OUTTAKE_OPEN,
        OUTTAKE_CLOSED,
        RUN_FLY_MOTORS,
        PUSH_SERVO,
    }

    override fun runOpMode() {
        val intake = hardwareMap.get("intake") as Servo
        val intake2 = hardwareMap.get("intake2") as Servo
        val drive = drivetrain(hardwareMap, false)
        val flywheels = flywheels(hardwareMap)
        val controller1 = Controller(gamepad1)
        var outTake: OutTake = OutTake.OUTTAKE_OPEN
        var timer = ElapsedTime()
        waitForStart()
        val allHubs = hardwareMap.getAll<LynxModule?>(LynxModule::class.java)
        for (module in allHubs) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL)
        }
        timer.reset()
        while (opModeIsActive()) {
            for (hub in allHubs) {
                hub.clearBulkCache()
            }
            controller1.update()

            drive.stickTankEffort(controller1.right_stick_x, -controller1.left_stick_y)
            flywheels.runMotors()
            drive.runMotors()

            when (outTake) {
                OutTake.OUTTAKE_OPEN ->{
                    intake.position = 0.25
                    intake2.position = 0.55
                    //close the servos
                    if (controller1.rightBumperOnce()){
                        outTake = OutTake.OUTTAKE_CLOSED
                    }
                }
                OutTake.OUTTAKE_CLOSED ->{
                    // open the servos
                    intake.position = 0.55
                    intake2.position = 0.15
                    if (controller1.leftBumperOnce()){
                        outTake = OutTake.OUTTAKE_OPEN
                    }
                    // run the motors
                    if (controller1.rightBumperOnce()){
                        outTake = OutTake.RUN_FLY_MOTORS
                    }
                }
                OutTake.RUN_FLY_MOTORS ->{
                    flywheels.maxPower()
                    flywheels.runMotors()
                    outTake = OutTake.PUSH_SERVO
                }
                OutTake.PUSH_SERVO ->{
                    if (flywheels.rightFly.motor.velocity>1000 && flywheels.leftFly.motor.velocity>1000){
                        intake.position = 0.7
                        timer.reset()
                        timer.startTime()
                    }
                    if (timer.seconds() >0.2){
                        outTake = OutTake.OUTTAKE_OPEN
                    }

                }


            }
            /*
            when (outTake) {
                OutTake.OUTTAKE_START -> {
                    if (controller1.dpad_UpOnce()) {
                        outTake = OutTake.PUSH_SERVO
                    }
                }

                OutTake.PUSH_SERVO -> {
                    if (flywheels.leftFly.motor.velocity > 20 && flywheels.rightFly.motor.velocity > 20) {
                        intake.position = 0.7
                        timer.reset()
                        timer.startTime()
                        outTake = OutTake.INTAKE_OUT
                    }
                }

                OutTake.INTAKE_OUT -> {
                    if (timer.seconds()>0.2){
                        intake.position = 0.25
                        intake2.position = 0.55
                    }
                    outTake = OutTake.OUTTAKE_START
                }
            }

             */
            /*
            if (controller1.xOnce()){
                intake.position = 0.5 // left side
            }

            if (controller1.yOnce()){
                intake.position += 0.05
            }

            if (controller1.aOnce()){
                intake.position -= 0.05
            }


            if (controller1.dpad_LeftOnce()){
                intake2.position = 0.5 // right side
            }

            if (controller1.dpad_UpOnce()){
                intake2.position += 0.05
            }

            if (controller1.dpad_DownOnce()){
                intake2.position -= 0.05
            }
             */
            /*
            //open
            if (controller1.leftBumperOnce()){
                intake2.position = 0.55
                intake.position = 0.25
            }
            //closed
            if (controller1.rightBumperOnce()){
                intake2.position = 0.15
                intake.position = 0.55
            }
            //halfway
            if(controller1.aOnce()){
                intake2.position = 0.24
                intake.position = 0.55
            }

            //shooting
            if (controller1.right_trigger>0.1){
                flywheels.maxPower()
                flywheels.runMotors()
                sleep(1000)
                intake.position = 0.7
            } else{
                flywheels.zeroPower()
            }

             */


            // open intake2/left is 0.55, intake/right is 0.25
            // not shooting closed, 0.15, 0.55
            // shooting pos 0.7 for intake/right
            telemetry.addData("leftflycurrent", flywheels.leftFly.motor.getCurrent(CurrentUnit.AMPS))
            telemetry.addData("rightflycurrent", flywheels.rightFly.motor.getCurrent(CurrentUnit.AMPS))
            telemetry.addData("intake2pos",intake2.position)
            telemetry.addData("intakepos",intake.position)
            telemetry.update()


        }
    }
}