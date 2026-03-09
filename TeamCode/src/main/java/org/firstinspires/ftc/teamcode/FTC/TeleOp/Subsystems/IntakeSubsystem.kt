package org.firstinspires.ftc.teamcode.FTC.TeleOp.Subsystems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.setup.motorSetup


class IntakeSubsystem(hardwareMap: HardwareMap) {
    val intake = motorSetup(hardwareMap,"intake", DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE)
    var intakeToggle = false

    fun toggle(){
        if (intakeToggle){
            intake.effort = 0.0
            intakeToggle = false
        } else {
            intake.effort = 1.0
            intakeToggle = true
        }
    }

    fun write(){
        intake.write()
    }
}