package org.firstinspires.ftc.teamcode.FTC.TeleOp.tests

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.robot.Robot
import org.firstinspires.ftc.teamcode.architecture.subsystems.SpindexerSubsytem
import org.firstinspires.ftc.teamcode.setup.Controller
import org.firstinspires.ftc.teamcode.setup.servoSetup
import org.firstinspires.ftc.teamcode.util.Robotconstants
import org.firstinspires.ftc.teamcode.util.ServoConstants

@TeleOp(name = "spindexertest")
class Spindexertest: LinearOpMode() {
    override fun runOpMode() {
        val spindexer = servoSetup(hardwareMap, "spindexer", ServoConstants.AXON)
        val controller1 = Controller(gamepad1)
        waitForStart()
        while (opModeIsActive()){
            controller1.update()
            if (controller1.dpad_RightOnce()){
                spindexer.position = spindexer.position + 0.05
            }
            if (controller1.dpad_LeftOnce()){
                spindexer.position = spindexer.position - 0.05
            }
            if (controller1.dpad_UpOnce()){
                spindexer.position = Robotconstants.SpindexerPosition.ONE_SHOOT.pos1
            }
            if (controller1.dpad_DownOnce()){
                spindexer.position = Robotconstants.SpindexerPosition.TWO_SHOOT.pos1
            }
            if (controller1.xOnce()){
                spindexer.position = Robotconstants.SpindexerPosition.THREE_SHOOT.pos1
            }

            if (controller1.yOnce()){
                spindexer.position = Robotconstants.SpindexerPosition.ONE_INTAKE.pos1
            }
            if (controller1.bOnce()){
                spindexer.position = Robotconstants.SpindexerPosition.TWO_INTAKE.pos1
            }
            if (controller1.aOnce()){
                spindexer.position = Robotconstants.SpindexerPosition.THREE_INTAKE.pos1
            }
            spindexer.write()
            telemetry.addData("Servo position", spindexer.position)
            telemetry.addLine("up=1shoot, down=2shoot, x=3shoot")
            telemetry.addLine("y=1intake, b=2intake, a=3intake")
            telemetry.addLine("right and left dpad for manual")
            telemetry.update()
        }
    }
}