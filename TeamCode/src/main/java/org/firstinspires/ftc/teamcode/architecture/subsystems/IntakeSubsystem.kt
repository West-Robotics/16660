package org.firstinspires.ftc.teamcode.architecture.subsystems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.architecture.Subsystem
import org.firstinspires.ftc.teamcode.setup.motorSetup

class IntakeSubsystem(hardwareMap: HardwareMap): Subsystem() {

    val intakeMotor = motorSetup(hardwareMap,
        "intakeMotor",
        DcMotorSimple.Direction.FORWARD,
        DcMotor.ZeroPowerBehavior.BRAKE)

    fun setEffort(power:Double){
        intakeMotor.effort = power
    }

    fun write(){
        intakeMotor.write()
    }

}