package org.firstinspires.ftc.teamcode.FTC

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.FTC.TeleOp.tests.yeeter
import org.firstinspires.ftc.teamcode.architecture.subsystems.SpindexerSubsytem
import org.firstinspires.ftc.teamcode.setup.motorSetup
import org.firstinspires.ftc.teamcode.setup.servoSetup
import org.firstinspires.ftc.teamcode.util.ServoConstants

class Hardware(val hardwareMap: HardwareMap){

    val hood = servoSetup(hardwareMap,"angle", ServoConstants.AXON)
    val intake = motorSetup(hardwareMap, "intake", DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE)
    val yeeter = motorSetup(hardwareMap, "yeeter", DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE)
    val spindexer = servoSetup(hardwareMap,"spindexer", ServoConstants.AXON)
    val launcher = motorSetup(hardwareMap,"launcher", DcMotorSimple.Direction.FORWARD,DcMotor.ZeroPowerBehavior.BRAKE)

    fun write(){
        hood.write()
        intake.write()
        yeeter.write()
        spindexer.write()
        launcher.write()
    }
}