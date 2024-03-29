package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.AutonomousTicondeRobot;
import org.firstinspires.ftc.teamcode.TicondeRobot;

@Autonomous(name = "Autonomous Blue Spinner", group = "Blue")
public class AutonomousBlueSpinner extends LinearOpMode {
    private AutonomousTicondeRobot robot = new AutonomousTicondeRobot();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.initHardware(hardwareMap);

        waitForStart();

        robot.moveToPositionPID(70, telemetry, () -> opModeIsActive());
        robot.setDriveTrainMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.setMovement(-.05,-.05,-.05,-.05);
        robot.runSpinner(true);
        robot.setMovement(0,0,0,0);
        robot.runSpinner(true);

    }
}
