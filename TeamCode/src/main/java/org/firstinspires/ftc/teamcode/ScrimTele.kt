package org.firstinspires.ftc.teamcode

import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.mechanisms.drivetrain
import org.firstinspires.ftc.teamcode.mechanisms.flywheels
import org.firstinspires.ftc.teamcode.setup.Controller
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit


@TeleOp(name="ScrimTeleOp1Controller")
class ScrimTele : LinearOpMode(){

    override fun runOpMode(){
        val intake = hardwareMap.get("intake") as Servo
        val intake2 = hardwareMap.get("intake2") as Servo
        val drive = drivetrain(hardwareMap, false)
        val flywheels = flywheels(hardwareMap)
        val controller1 = Controller(gamepad1)
        waitForStart()
        val allHubs = hardwareMap.getAll<LynxModule?>(LynxModule::class.java)
        for (module in allHubs) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO)
        }
        while (opModeIsActive()){
            controller1.update()
            drive.stickTankEffort(controller1.right_stick_x, -controller1.left_stick_y)
            flywheels.runMotors()
            drive.runMotors()
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
            //shooting
            if (controller1.right_trigger>0.1){
                flywheels.maxPower()
                sleep(1000)
                intake.position = 0.7
            } else{
                flywheels.zeroPower() 
            }


            // open intake2/left is 0.55, intake/right is 0.25
            // not shooting closed, 0.15, 0.55
            // shooting pos 0.7 for intake/right
            telemetry.addData("intake2pos",intake2.position)
            telemetry.addData("intakepos",intake.position)
            telemetry.update()


        }
    }
}