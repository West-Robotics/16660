package org.firstinspires.ftc.teamcode

import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.mechanisms.drivetrain
import org.firstinspires.ftc.teamcode.setup.Controller

@TeleOp(name="ScrimTeleOp")
class ScrimTele : LinearOpMode(){

    override fun runOpMode(){
        val drive = drivetrain(hardwareMap, false)
        val controller1 = Controller(gamepad1)
        waitForStart()
        val allHubs = hardwareMap.getAll<LynxModule?>(LynxModule::class.java)
        for (module in allHubs) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO)
        }

        while (opModeIsActive()){
            controller1.update()
            drive.tankEffort(controller1.left_trigger, controller1.right_trigger)
        }
    }
}