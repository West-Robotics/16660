package org.firstinspires.ftc.teamcode.FTC.TeleOp.Subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.FTC.TeleOp.CleanTeleOp
import org.firstinspires.ftc.teamcode.FTC.TeleOp.TestTeleOP2
import org.firstinspires.ftc.teamcode.setup.servoSetup
import org.firstinspires.ftc.teamcode.util.Robotconstants
import org.firstinspires.ftc.teamcode.util.ServoConstants

class SpindexerSubsystem(hardwareMap: HardwareMap, var robotState: RobotState) {

    val spindexer = servoSetup(hardwareMap,"spindexer", ServoConstants.AXON)

    var spindexerpos = Robotconstants.SpindexerPosition.TWO_INTAKE


    private fun setPos(){
        spindexer.position = spindexerpos.pos1
    }
    fun intakeReset(){
        spindexerpos = Robotconstants.SpindexerPosition.TWO_INTAKE
        setPos()
    }

    fun intakeCycle(){
        spindexerpos = when(spindexerpos) {
            Robotconstants.SpindexerPosition.TWO_INTAKE ->{
                Robotconstants.SpindexerPosition.THREE_INTAKE
            }

            Robotconstants.SpindexerPosition.THREE_INTAKE -> {
                Robotconstants.SpindexerPosition.ONE_INTAKE
            }

            else ->{
                Robotconstants.SpindexerPosition.TWO_INTAKE
            }
        }
        setPos()
    }

    fun initLaunchCycle(){
        robotState.current = CleanTeleOp.State.LAUNCHING
        spindexerpos = Robotconstants.SpindexerPosition.ONE_SHOOT
        setPos()
    }

    fun launchCycle(){
        when (spindexerpos) {
            Robotconstants.SpindexerPosition.ONE_SHOOT -> {
                spindexerpos = Robotconstants.SpindexerPosition.TWO_SHOOT
            }

            Robotconstants.SpindexerPosition.TWO_SHOOT -> {
                spindexerpos = Robotconstants.SpindexerPosition.THREE_SHOOT
            }
            else -> {
                robotState.current = CleanTeleOp.State.INTAKING
                spindexerpos = Robotconstants.SpindexerPosition.TWO_INTAKE
            }
        }
        setPos()
    }

    fun write(){
        spindexer.write()
    }
}