package org.firstinspires.ftc.teamcode.architecture.subsystems

import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.architecture.Subsystem
import org.firstinspires.ftc.teamcode.setup.CRservoSetup
import org.firstinspires.ftc.teamcode.util.Robotconstants
import org.firstinspires.ftc.teamcode.util.CRServoConstants

class YeeterSubsystem(hardwareMap: HardwareMap): Subsystem(){
    val rightIntake = CRservoSetup(hardwareMap,"yeeter1",CRServoConstants.GOBILDA_SPEED,DcMotorSimple.Direction.REVERSE)
    val leftIntake = CRservoSetup(hardwareMap,"yeeter2",CRServoConstants.GOBILDA_SPEED,DcMotorSimple.Direction.FORWARD)

    fun run(running:Boolean?){
        if (running == true){
            leftIntake.effort = Robotconstants.OuttakeBallPower.ON.speedServo1
            rightIntake.effort = Robotconstants.OuttakeBallPower.ON.speedServo2
        } else if (running ==false){
            leftIntake.effort = Robotconstants.OuttakeBallPower.OFF.speedServo1
            rightIntake.effort = Robotconstants.OuttakeBallPower.OFF.speedServo2
        } else{

        }
    }

    override fun write() {
        rightIntake.write()
        leftIntake.write()
    }
}