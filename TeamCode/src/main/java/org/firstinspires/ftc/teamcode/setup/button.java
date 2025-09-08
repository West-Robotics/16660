package org.firstinspires.ftc.teamcode.setup;
import com.qualcomm.robotcore.hardware.Gamepad;
public class button {
    private int value = 0;

    public void check(boolean gamepadbutton){
        if (gamepadbutton){
            value++;
        } else {
            value = 0;
        }
    }

    public int returnValue(){return value;}
}
