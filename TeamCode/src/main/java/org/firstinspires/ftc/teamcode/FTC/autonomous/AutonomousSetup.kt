package org.firstinspires.ftc.teamcode.FTC.autonomous

import com.bylazar.field.Line
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.FTC.TeleOp.tests.testtele
import org.firstinspires.ftc.teamcode.architecture.subsystems.SpindexerSubsytem
import org.firstinspires.ftc.teamcode.setup.Controller
import org.firstinspires.ftc.teamcode.setup.servoSetup
import org.firstinspires.ftc.teamcode.util.Robotconstants
import org.firstinspires.ftc.teamcode.util.ServoConstants

@TeleOp(name = "AutoSetup")
class AutonomousSetup : LinearOpMode(){
    override fun runOpMode() {
        val spindexer = servoSetup(hardwareMap,"spindexer", ServoConstants.AXON)
        val controller1 = Controller(gamepad1)
        var spindexerpos = Robotconstants.SpindexerPosition.ONE_SHOOT
        waitForStart()
        while (opModeIsActive()){
            controller1.update()
            if(controller1.dpad_LeftOnce()){
                spindexerpos = Robotconstants.SpindexerPosition.ONE_SHOOT
            }
            if(controller1.dpad_UpOnce()){
                spindexerpos = Robotconstants.SpindexerPosition.TWO_SHOOT
            }
            if(controller1.dpad_RightOnce()){
                spindexerpos = Robotconstants.SpindexerPosition.THREE_SHOOT
            }
            spindexer.position = spindexerpos.pos1
            if(spindexerpos == Robotconstants.SpindexerPosition.ONE_SHOOT){
                telemetry.addLine("Green")
            }
            if(spindexerpos == Robotconstants.SpindexerPosition.TWO_SHOOT){
                telemetry.addLine("Purple")
            }
            if(spindexerpos == Robotconstants.SpindexerPosition.THREE_SHOOT){
                telemetry.addLine("Purple")
            }

            spindexer.write()
            telemetry.update()
        }
    }
}