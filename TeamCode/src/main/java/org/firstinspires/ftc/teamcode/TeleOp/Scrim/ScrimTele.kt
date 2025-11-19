package org.firstinspires.ftc.teamcode.TeleOp.Scrim

import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import org.firstinspires.ftc.teamcode.mechanisms.DrivetrainTank
import org.firstinspires.ftc.teamcode.mechanisms.drivetrain
import org.firstinspires.ftc.teamcode.mechanisms.flywheels
import org.firstinspires.ftc.teamcode.setup.Controller

@TeleOp(name="ScrimTeleOp1Controller")
class ScrimTele : LinearOpMode(){

    enum class OutTake {
        OUTTAKE_OPEN,
        OUTTAKE_CLOSED2,
        OUTTAKE_CLOSED,
        RUN_FLY_MOTORS,
        PUSH_SERVO,
    }

    override fun runOpMode() {
        val intake = hardwareMap.get("intake") as Servo
        val intake2 = hardwareMap.get("intake2") as Servo
        val drive = DrivetrainTank(hardwareMap)
        val flywheels = flywheels(hardwareMap)
        val controller1 = Controller(gamepad1)
        var outTake: OutTake = OutTake.OUTTAKE_OPEN
        var done = false
        var timer = ElapsedTime()
        waitForStart()
        val allHubs = hardwareMap.getAll<LynxModule?>(LynxModule::class.java)
        for (module in allHubs) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL)
        }
        timer.reset()
        while (opModeIsActive()) {
            for (hub in allHubs) {
                hub.clearBulkCache()
            }
            controller1.update()
            drive.stickEffort(controller1.right_stick_x, -controller1.left_stick_y)
            when (outTake) {
                OutTake.OUTTAKE_OPEN ->{
                    intake.position = 0.25
                    intake2.position = 0.55
                    flywheels.zeroPower()
                    //close the servos
                    if (controller1.rightBumperOnce()){
                        outTake = OutTake.OUTTAKE_CLOSED
                    }
                    if ( controller1.left_trigger > 0.1){
                        outTake = OutTake.OUTTAKE_CLOSED2
                    }
                }
                OutTake.OUTTAKE_CLOSED2 -> {
                    // to hold two balls
                    intake.position = 0.36
                    intake2.position = 0.27;888888888
                    flywheels.zeroPower()
                    if(controller1.rightBumperOnce()){
                        outTake = OutTake.OUTTAKE_CLOSED
                    }
                    if (controller1.leftBumperOnce()){
                        outTake = OutTake.OUTTAKE_OPEN
                    }
                    if (controller1.right_trigger>0.2){
                        outTake = OutTake.RUN_FLY_MOTORS
                    }
                }
                OutTake.OUTTAKE_CLOSED ->{
                    intake.position = 0.55
                    intake2.position = 0.15 //0.15
                    // open the servos
                    if (controller1.leftBumperOnce()){
                        outTake = OutTake.OUTTAKE_OPEN
                    }
                    // run the motors
                    if (controller1.right_trigger>0.2){
                        outTake = OutTake.RUN_FLY_MOTORS
                    }
                    if ( controller1.left_trigger > 0.1){
                        outTake = OutTake.OUTTAKE_CLOSED2
                    }
                }
                OutTake.RUN_FLY_MOTORS ->{
                    intake2.position = 0.10
                    flywheels.maxPower()
                    flywheels.runMotors()
                    outTake = OutTake.PUSH_SERVO
                }
                OutTake.PUSH_SERVO ->{
                    if (flywheels.rightFly.motor.velocity>2380 && flywheels.leftFly.motor.velocity>2380 && !done){
                        intake.position = 0.7
                        timer.reset()
                        timer.startTime()
                        done = true
                    }
                    if (timer.seconds() >0.75 && done){
                        flywheels.zeroPower()
                        outTake = OutTake.OUTTAKE_OPEN
                        done = false
                    }
                    if (controller1.leftBumperOnce()){
                        outTake = OutTake.OUTTAKE_OPEN
                    }
                }
            }
            flywheels.runMotors()
            drive.write()

            // open intake2/left is 0.55, intake/right is 0.25
            // not shooting closed, 0.15, 0.55
            // shooting pos 0.7 for intake/right
            telemetry.addData("leftflymotorvelocity", flywheels.leftFly.motor.velocity)
            telemetry.addData("rightflymotorvelocity",flywheels.rightFly.motor.velocity)
            telemetry.addData("leftflycurrent", flywheels.leftFly.motor.getCurrent(CurrentUnit.AMPS))
            telemetry.addData("rightflycurrent", flywheels.rightFly.motor.getCurrent(CurrentUnit.AMPS))
            telemetry.addData("intake2pos",intake2.position)
            telemetry.addData("intakepos",intake.position)
            telemetry.update()
        }
    }
}