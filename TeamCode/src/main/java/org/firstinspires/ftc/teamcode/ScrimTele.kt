package org.firstinspires.ftc.teamcode

import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.mechanisms.drivetrain
import org.firstinspires.ftc.teamcode.mechanisms.flywheels
import org.firstinspires.ftc.teamcode.setup.Controller
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit


@TeleOp(name="ScrimTeleOp")
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
        var ybool = false
        var xbool = false
        while (opModeIsActive()){
            controller1.update()
            drive.stickTankEffort(controller1.left_stick_x, -controller1.left_stick_y)
            flywheels.variablePower(-controller1.right_stick_y)
            flywheels.runMotors()
            drive.runMotors()

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

            if (controller1.leftBumperOnce()){ // open position
                intake.position = 0.25
                intake2.position = 0.8

            }

            if (controller1.rightBumperOnce()){  // closed position
                intake.position = 0.55
                intake2.position = 0.45
            }

            if (controller1.bOnce()){
                intake.position = 0.72
            }

            /*
            if (controller1.yOnce() && !ybool){
                intake.position = 0.3 // out left side
                ybool = true
            } else if(controller1.yOnce() && ybool){
                intake.position = 0.75 //in
                ybool = false
            }
            if (controller1.xOnce() && !xbool){
                intake2.position = 0.8 // out right side
                xbool = true
            } else if(controller1.xOnce() && xbool){
                intake2.position = 0.35 // in
                xbool = false
            }

             */

        }
    }
}