package org.firstinspires.ftc.teamcode.FTC.autonomous

import com.pedropathing.geometry.Pose
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.FTC.mechanisms.Ball
import org.firstinspires.ftc.teamcode.FTC.mechanisms.BallColor
import org.firstinspires.ftc.teamcode.util.Robotconstants

@Autonomous
class BlueFar6: PedroOpMode() {
    override var blue = true

    var startPose = Pose(56.0,8.0,Math.toRadians(270.0))
    var shootPose = Pose(60.01976935749587,15.894563426688618,Math.toRadians(-66.0))

    var intakePose = Pose(45.156893819334385,38.80522979397782,Math.toRadians(195.0))
    var endIntakePose = Pose(21.76069730586371,38.80522979397782,Math.toRadians(195.0))
    var parkPose = Pose(59.308072487644125,37.2454695222405,Math.toRadians(180.0))


    enum class PathState(){
        START_SHOOT,
        SHOOTING,
        SHOOTING_RUNNING,
        SHOOTING_TO_INTAKE,
        INTAKING,
        INTAKING_TO_SHOOT,
        SHOOTING2,
        SHOOTING2_RUNNING,
        SHOOT_PARK
    }

    override fun looping() {
        val startToShoot = buildPath(startPose,shootPose)
        val shootToIntake = buildPath(shootPose,intakePose)
        val intakePath = buildPath(intakePose,endIntakePose)
        val intakeToShoot = buildPath(endIntakePose,shootPose)
        val shootToPark = buildPath(shootPose,parkPose)
        var indexing = 0
        var pathState = PathState.START_SHOOT
        follower.pose = startPose
        waitForStart()
        initialize()
        while(opModeIsActive()){
            follower.update()
            when(pathState){
                PathState.START_SHOOT -> {
                    follower.followPath(startToShoot)
                    pathState = PathState.SHOOTING
                }
                PathState.SHOOTING -> {
                    if (!follower.isBusy){
                        shootInit(
                            Ball(Robotconstants.SpindexerPosition.ONE_SHOOT, BallColor.GREEN),
                            Ball(Robotconstants.SpindexerPosition.TWO_SHOOT, BallColor.PURPLE),
                            Ball(Robotconstants.SpindexerPosition.THREE_SHOOT, BallColor.PURPLE))
                        pathState = PathState.SHOOTING_RUNNING

                    }
                }
                PathState.SHOOTING_RUNNING ->{
                    if (shootLoop()){
                        follower.followPath(shootToIntake)
                        pathState = PathState.SHOOTING_TO_INTAKE
                    }
                }
                PathState.SHOOTING_TO_INTAKE -> {
                    if (!follower.isBusy){
                        pathState = PathState.INTAKING
                    }
                }
                PathState.INTAKING ->{
                    var continuing = initIntake()
                    if (continuing){
                        intakeLoop(intakePath)
                    }
                    if(isDoneIntake()){
                        follower.breakFollowing()
                        intake.effort = 0.0
                        intake.write()
                        pathState = PathState.INTAKING_TO_SHOOT
                        follower.followPath(intakeToShoot)
                        getReadySpindexer()
                    }
                }
                PathState.INTAKING_TO_SHOOT ->{
                    if (!follower.isBusy){
                        pathState = PathState.SHOOTING2
                    }
                }
                PathState.SHOOTING2 -> {
                    if (!follower.isBusy){
                        shootInit(Ball(Robotconstants.SpindexerPosition.ONE_SHOOT, BallColor.GREEN),
                            Ball(Robotconstants.SpindexerPosition.TWO_SHOOT, BallColor.PURPLE),
                            Ball(Robotconstants.SpindexerPosition.THREE_SHOOT, BallColor.PURPLE))
                        pathState = PathState.SHOOTING2_RUNNING
                    }
                }
                PathState.SHOOTING2_RUNNING ->{
                    if(shootLoop()){
                        pathState = PathState.SHOOT_PARK
                    }
                }
                PathState.SHOOT_PARK -> {
                    follower.followPath(shootToPark)
                }
            }
        }
    }
}