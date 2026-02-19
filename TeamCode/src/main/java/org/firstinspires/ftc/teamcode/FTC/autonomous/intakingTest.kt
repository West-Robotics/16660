package org.firstinspires.ftc.teamcode.FTC.autonomous

import com.pedropathing.geometry.Pose
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.util.Robotconstants

@Autonomous(name = "intakeTest")
class intakingTest: PedroOpMode() {
    override var blue = true

    var startPose = Pose(0.0,0.0,Math.toRadians(90.0))
    var endPose = Pose(0.0,20.0,Math.toRadians(90.0))


    override fun looping() {

        val intakePath = buildPath(startPose,endPose)

        spindexerpos = Robotconstants.SpindexerPosition.TWO_INTAKE
        spindexer.position = spindexerpos.pos1
        spindexer.write()

        follower.pose = startPose

        intakingTimer.reset()
        afterIntakingTimer.reset()

        waitForStart()

        while (opModeIsActive()) {
            follower.update()
            intakeLoop(intakePath)

        }
    }
}