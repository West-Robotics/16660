package org.firstinspires.ftc.teamcode.TeleOp.tests

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.architecture.subsystems.YeeterSubsystem
import org.firstinspires.ftc.teamcode.setup.Controller
import org.firstinspires.ftc.teamcode.setup.servoSetup
import org.firstinspires.ftc.teamcode.util.ServoConstants

@TeleOp(name="yeeter")
class yeeter : LinearOpMode(){
    override fun runOpMode() {
        var yeeter = YeeterSubsystem(hardwareMap)
        var controller1 = Controller(gamepad1)
        val spindexer = servoSetup(hardwareMap, "spindexer", ServoConstants.AXON)
        waitForStart()
        while (opModeIsActive()){
            controller1.update()
            if (controller1.bOnce()){
                yeeter.run(true)
            }
            if (controller1.aOnce()){
                yeeter.run(false)
            }
            yeeter.write()
        }

    }
}