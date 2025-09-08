package org.firstinspires.ftc.teamcode.setup;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;




public class motorSetup {

    private DcMotorEx motor;
    public void motorSet (HardwareMap hardwareMap, DcMotorSimple.Direction dir, String name){
        this.motor = hardwareMap.get(DcMotorEx.class,name);
        this.motor.setDirection(dir);

    }









}
