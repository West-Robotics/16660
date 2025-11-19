package org.firstinspires.ftc.teamcode.TeleOp.Scrim

import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import org.firstinspires.ftc.teamcode.mechanisms.drivetrain
import org.firstinspires.ftc.teamcode.mechanisms.flywheels
import org.firstinspires.ftc.teamcode.setup.Controller

@TeleOp(name="ScrimTeleOp2Controller")
class ScrimTeleOp2Controllers: LinearOpMode(){
    override fun runOpMode() {
        val intake = hardwareMap.get("intake") as Servo
        val intake2 = hardwareMap.get("intake2") as Servo
        val drive = drivetrain(hardwareMap, false)
        val flywheels = flywheels(hardwareMap)
        val controller1 = Controller(gamepad1)
        val controller2 = Controller(gamepad2)
        waitForStart()
        val allHubs = hardwareMap.getAll<LynxModule?>(LynxModule::class.java)
        for (module in allHubs) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO)
        }
        while (opModeIsActive()) {
            // controller 1 is the drive controller
            controller1.update()
            // controller 2 is the intake and shooter controller
            controller2.update()
            drive.stickTankEffort(controller1.right_stick_x, -controller1.left_stick_y)
            flywheels.runMotors()
            drive.write()

            //open
            if (controller2.leftBumperOnce()){
                intake2.position = 0.55
                intake.position = 0.25
            }
            //closed
            if (controller2.rightBumperOnce()){
                intake2.position = 0.15
                intake.position = 0.55
            }
            //halfway
            if(controller2.aOnce()){
                intake2.position = 0.24
                intake.position = 0.55
            }

            //shooting
            if (controller2.right_trigger>0.1){
                flywheels.maxPower()
                flywheels.runMotors()
                sleep(1000)
                intake.position = 0.7
            } else{
                flywheels.zeroPower()
            }

            // open intake2/left is 0.55, intake/right is 0.25
            // not shooting closed, 0.15, 0.55
            // shooting pos 0.7 for intake/right
            telemetry.addData("leftflycurrent", flywheels.leftFly.motor.getCurrent(CurrentUnit.AMPS))
            telemetry.addData("intake2pos",intake2.position)
            telemetry.addData("intakepos",intake.position)
            telemetry.update()



        }
    }
}