package org.firstinspires.ftc.teamcode.autonomous.lime

import com.qualcomm.hardware.limelightvision.*
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.robot.Robot
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D
import org.firstinspires.ftc.teamcode.architecture.subsystems.SpindexerSubsytem
import org.firstinspires.ftc.teamcode.architecture.subsystems.YeeterSubsystem
import org.firstinspires.ftc.teamcode.setup.motorSetup
import org.firstinspires.ftc.teamcode.util.Robotconstants

class OrderDetection(val hardwareMap: HardwareMap, val spindexer: SpindexerSubsytem, val telemetry: Telemetry,val opMode: LinearOpMode,val yeeterSubsystem: YeeterSubsystem) {
    val limelight = hardwareMap.get("Limelight") as Limelight3A
    var order = ShootMethod.GPP
    val launcher = motorSetup(hardwareMap,"launcher", DcMotorSimple.Direction.FORWARD,DcMotor.ZeroPowerBehavior.BRAKE)
    fun init(){
        limelight.pipelineSwitch(6)
        limelight.start()
    }
    fun setState(){

    }

    enum class State(var pos: Robotconstants.SpindexerPosition){
        G(Robotconstants.SpindexerPosition.ONE_SHOOT),
        P(Robotconstants.SpindexerPosition.TWO_SHOOT),
        P2(Robotconstants.SpindexerPosition.THREE_SHOOT)
    }


    // TODO: make the indexer method with color sensor
    // save below, but start new system above

    enum class ShootMethod(var pos1: Robotconstants.SpindexerPosition,var pos2: Robotconstants.SpindexerPosition,var pos3: Robotconstants.SpindexerPosition){
        GPP(
            Robotconstants.SpindexerPosition.ONE_SHOOT,
            Robotconstants.SpindexerPosition.TWO_SHOOT,
            Robotconstants.SpindexerPosition.THREE_SHOOT
        ),  //21
        PGP(
            Robotconstants.SpindexerPosition.TWO_SHOOT,
            Robotconstants.SpindexerPosition.ONE_SHOOT,
            Robotconstants.SpindexerPosition.THREE_SHOOT
        ),  //22
        PPG(
            Robotconstants.SpindexerPosition.THREE_SHOOT,
            Robotconstants.SpindexerPosition.TWO_SHOOT,
            Robotconstants.SpindexerPosition.ONE_SHOOT
        )   //23
    }

    fun shootOrder() {
        launcher.effort = 1.0
        launcher.write()
        spindexer.position(order.pos1)
        spindexer.write()
        opMode.sleep(3000)
        yeeterSubsystem.run(true)
        yeeterSubsystem.write()
        opMode.sleep(3000)
        spindexer.position(order.pos2)
        spindexer.write()
        opMode.sleep(3000)
        spindexer.position(order.pos3)
        spindexer.write()
        opMode.sleep(3000)
        spindexer.position(Robotconstants.SpindexerPosition.ONE_INTAKE)
        spindexer.write()
        opMode.sleep(800)
        yeeterSubsystem.run(false)
        launcher.effort = 0.0
        yeeterSubsystem.write()
        launcher.write()
    }
    /// FOR AUTONONOOMOUS ONLY :3
    fun fireOrder() {
        val llResult: LLResult = limelight.latestResult
        if (llResult.isValid){
            telemetry.addLine("worked register")
            val tags = llResult.fiducialResults
            if (tags.isNotEmpty()) {
                for (i in tags) {
                    telemetry.addData("apriltag",i)
                    telemetry.update()
                }
                val tag = tags[0]
                if (tag.fiducialId == 21) {
                    order = ShootMethod.GPP
                } else if (tag.fiducialId==22) {
                    order = ShootMethod.PGP
                } else {
                    order = ShootMethod.PPG
                }
            }
        }
    }

}