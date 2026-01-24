package org.firstinspires.ftc.teamcode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.architecture.subsystems.SpindexerSubsytem
import org.firstinspires.ftc.teamcode.architecture.subsystems.YeeterSubsystem
import org.firstinspires.ftc.teamcode.autonomous.lime.OrderDetection

@Autonomous(name = "1test")
class limeLightTest : LinearOpMode(){
    override fun runOpMode() {
        val spindexer = SpindexerSubsytem(hardwareMap)
        val yeeter = YeeterSubsystem(hardwareMap)
        var ordering = OrderDetection(hardwareMap,spindexer,telemetry,this,yeeter)
        ordering.init()
        waitForStart()
        ordering.fireOrder()
        ordering.shootOrder()

    }
}