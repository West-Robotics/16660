package org.firstinspires.ftc.teamcode.architecture.commands

import com.qualcomm.robotcore.robot.Robot
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.architecture.Command
import org.firstinspires.ftc.teamcode.architecture.subsystems.LauncherSubsystem
import org.firstinspires.ftc.teamcode.architecture.subsystems.SpindexerSubsytem
import org.firstinspires.ftc.teamcode.architecture.subsystems.YeeterSubsystem
import org.firstinspires.ftc.teamcode.util.Robotconstants

class ShootCommand(spindexer: SpindexerSubsytem, launcher: LauncherSubsystem, yeeter: YeeterSubsystem, spindexerpos: Robotconstants.SpindexerPosition): Command() {
    val spindexer = spindexer
    val launcher = launcher
    val yeeter = yeeter
    var spindexerpos = Robotconstants.SpindexerPosition.ONE_SHOOT
    var doAgain = true
    val timer = ElapsedTime()
    override fun initialize() {
        spindexer.position(spindexerpos)
        launcher.setEffort(1.0)
        timer.reset()
        timer.startTime()
    }


    override fun execute() {
        if ((launcher.outTakeHigher.velocity>2000 ||timer.seconds()>0.37) && doAgain){
            yeeter.run(true)
            doAgain = false
            timer.reset()
            timer.startTime()
        } else if((spindexerpos == Robotconstants.SpindexerPosition.ONE_SHOOT && timer.seconds()>0.23)){
            spindexerpos = Robotconstants.SpindexerPosition.TWO_SHOOT
            timer.reset()
            timer.startTime()
        } else if (spindexerpos == Robotconstants.SpindexerPosition.TWO_SHOOT&&timer.seconds()>0.23){
            spindexerpos = Robotconstants.SpindexerPosition.THREE_SHOOT
            timer.reset()
            timer.startTime()
        }
        spindexer.position(spindexerpos)

    }

    override fun isFinished(): Boolean {
        if (spindexerpos == Robotconstants.SpindexerPosition.THREE_SHOOT && timer.seconds()>0.2){
            return true
        } else return false
    }

    override fun end(interrupted: Boolean) {
        launcher.setEffort(0.0)
        yeeter.run(false)
        spindexerpos = Robotconstants.SpindexerPosition.ONE_INTAKE
        spindexer.position(spindexerpos)
    }


}