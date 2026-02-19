package org.firstinspires.ftc.teamcode.FTC.autonomous

import com.pedropathing.geometry.Pose
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.FTC.mechanisms.Ball
import org.firstinspires.ftc.teamcode.FTC.mechanisms.BallColor
import org.firstinspires.ftc.teamcode.util.Robotconstants

@Autonomous
class RedFar9: PedroOpMode() {
    override var blue = false

    var startPose = Pose(88.0,8.0,Math.toRadians(270.0))
    var shootPose = Pose(84.19017432646594,20.225974643423125,Math.toRadians(251.0))

    var intakePose = Pose(101.79080824088749,35.1568938193344,Math.toRadians(-15.0))
    var endIntakePose = Pose(134.0,35.15213946117275,Math.toRadians(-15.0))

    var intakePos2 = Pose(100.86212361331218,59.57052297939778,Math.toRadians(-15.0))
    var endintakePos2 = Pose(134.0,59.57052297939778,Math.toRadians(-15.0))
    var parkPose = Pose(83.29001584786052,35.60855784469098,Math.toRadians(0.0))


    enum class PathState(){
        START_SHOOT,
        SHOOTING,
        SHOOTING_RUNNING,
        SHOOTING_TO_INTAKE,
        INTAKING,
        INTAKING_TO_SHOOT,
        SHOOTING2,
        SHOOTING2_RUNNING,
        SHOOTING_TO_INTAKE2,
        INTAKING2,
        INTAKING_TO_SHOOT2,
        SHOOTING3,
        SHOOTING3_RUNNING,
        SHOOT_PARK
    }

    override fun looping() {
        val startToShoot = buildPath(startPose,shootPose)
        val shootToIntake = buildPath(shootPose,intakePose)
        val intakePath = buildPath(intakePose,endIntakePose)
        val intakeToShoot = buildPath(endIntakePose,shootPose)
        val shootToIntake2 = buildPath(shootPose,intakePos2)
        val intake2Path = buildPath(intakePos2,endintakePos2)
        val intake2ToShoot = buildPath(endintakePos2,shootPose)
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
                        follower.followPath(shootToIntake2)
                        pathState = PathState.SHOOTING_TO_INTAKE2
                    }
                }
                PathState.SHOOTING_TO_INTAKE2 ->{
                    if(!follower.isBusy){
                        pathState = PathState.INTAKING2
                    }
                }
                PathState.INTAKING2 ->{
                    var continuing = initIntake()
                    if (continuing){
                        intakeLoop(intake2Path)
                    }
                    if(isDoneIntake()){
                        follower.breakFollowing()
                        intake.effort = 0.0
                        intake.write()
                        pathState = PathState.INTAKING_TO_SHOOT2
                        follower.followPath(intake2ToShoot)
                    }
                }
                PathState.INTAKING_TO_SHOOT2 ->{
                    if(!follower.isBusy){
                        pathState = PathState.SHOOTING3
                    }
                }
                PathState.SHOOTING3 ->{
                    if (!follower.isBusy){
                        shootInit(Ball(Robotconstants.SpindexerPosition.ONE_SHOOT, BallColor.GREEN),
                            Ball(Robotconstants.SpindexerPosition.TWO_SHOOT, BallColor.PURPLE),
                            Ball(Robotconstants.SpindexerPosition.THREE_SHOOT, BallColor.PURPLE))
                        pathState = PathState.SHOOTING3_RUNNING
                    }
                }
                PathState.SHOOTING3_RUNNING ->{
                    if(shootLoop()){
                        follower.followPath(shootToPark)
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