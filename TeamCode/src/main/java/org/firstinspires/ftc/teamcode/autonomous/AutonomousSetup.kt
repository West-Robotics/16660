package org.firstinspires.ftc.teamcode.autonomous

import com.bylazar.field.Line
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.architecture.subsystems.SpindexerSubsytem
import org.firstinspires.ftc.teamcode.setup.Controller
import org.firstinspires.ftc.teamcode.util.Robotconstants

@TeleOp(name = "AutoSetup")
class AutonomousSetup : LinearOpMode(){
    override fun runOpMode() {
        val spindexer = SpindexerSubsytem(hardwareMap)
        val controller1 = Controller(gamepad1)
        waitForStart()
        while (opModeIsActive()){
            controller1.update()
            if (controller1.dpad_LeftOnce()){
                spindexer.position(Robotconstants.SpindexerPosition.ONE_SHOOT)
            }
            if (controller1.dpad_UpOnce()){
                spindexer.position(Robotconstants.SpindexerPosition.TWO_SHOOT)
            }
            if (controller1.dpad_RightOnce()){
                spindexer.position(Robotconstants.SpindexerPosition.THREE_SHOOT)
            }
            spindexer.write()
        }
    }
}