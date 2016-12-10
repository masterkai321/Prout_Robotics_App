/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CompassSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

/**
 * This file illustrates the concept of driving up to a line and then stopping.
 * It uses the common Pushbot hardware class to define the drive on the robot.
 * The code is structured as a LinearOpMode
 *
 * The code shows using two different light sensors:
 *   The Primary sensor shown in this code is a legacy NXT Light sensor (called "light sensor")
 *   Alternative "commented out" code uses a MR Optical Distance Sensor (called "sensor_ods")
 *   instead of the LEGO sensor.  Chose to use one sensor or the other.
 *
 *   Setting the correct WHITE_THRESHOLD value is key to stopping correctly.
 *   This should be set half way between the light and dark values.
 *   These values can be read on the screen once the OpMode has been INIT, but before it is STARTED.
 *   Move the senso on asnd off the white line and not the min and max readings.
 *   Edit this code to make WHITE_THRESHOLD half way between the min and max.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="Pushbot: Auto Drive To Line", group="Pushbot")

public class ProutBotAutoDriveToLine_Linear extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareProutBot         robot   = new HardwareProutBot();   // Use a Pushbot's hardware
    private ElapsedTime              runtime = new ElapsedTime();


    // OpticalDistanceSensor   lightSensor;   // Alternative MR ODS sensor


    static final double     WHITE_THRESHOLD = 0.215;  // spans between 0.1 - 0.5 from dark to light
    static final double     APPROACH_SPEED  = -0.5;


    @Override
    public void runOpMode() throws InterruptedException {

        /* Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        double rdetected = robot.rlightSensor.getLightDetected();
        double ldetected = robot.llightSensor.getLightDetected();
        

        // If there are encoders connected, switch to RUN_USING_ENCODER mode for greater accuracy
        // robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // get a reference to our Light Sensor object.



        // Primary LEGO Light Sensor
        //  lightSensor = hardwareMap.opticalDistanceSensor.get("sensor_ods");  // Alternative MR ODS sensor.

        // turn on LED of light sensor.
        robot.rlightSensor.enableLed(true);
        robot.llightSensor.enableLed(true);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        while (!isStarted()) {

            // Display the light level while we are waiting to start
            telemetry.addData("R Light Level", robot.rlightSensor.getLightDetected());
            telemetry.addData("L Light Level", robot.llightSensor.getLightDetected());
            telemetry.addData("Dis To Wall", robot.backDis.getUltrasonicLevel());
            telemetry.addData("Bearing", robot.compassSensor.getDirection());

            telemetry.update();
            idle();
        }

        // Aim the Robot towards the Vortex and Shoot
        robot.SetHeading(robot.compassSensor.getDirection(), 290);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2.0)) {

            telemetry.addData("Leg 1: %2.5f Sec Elapsed", runtime.seconds());
            telemetry.update();
            idle();
        }

        //Aim Towards Beacons and Go Until Line
        robot.SetHeading(robot.compassSensor.getDirection(), 0);
        while (opModeIsActive() && (robot.frontDis.getUltrasonicLevel() < 225)) {

        }
        //Adjust onto Line and Follow until specific distance from Wall
        while (opModeIsActive() && (robot.frontDis.getUltrasonicLevel() > 10 || robot.frontDis.getUltrasonicLevel() == 0)) {

        }
        //Analyze colors and Press Respective Beacon
        while (opModeIsActive() && (robot.llightSensor.getLightDetected() < WHITE_THRESHOLD)) {

            // Display the light level while we are looking for the line
            telemetry.addData("R Light Level",  robot.rlightSensor.getLightDetected());
            telemetry.addData("L Light Level", robot.llightSensor.getLightDetected());
            telemetry.addData("Dis To Wall", robot.backDis.getUltrasonicLevel());
            telemetry.addData("Bearing", robot.compassSensor.getDirection());
            telemetry.update();
            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }
        //Turn towards Center Vortex and Go


        //Drive until Ball is moved and Robot is on Center Vortex - Stop

        sleep(1000);
        runtime.reset();


        /*while (opModeIsActive() && ((robot.sideDis.getUltrasonicLevel() > 8) || robot.sideDis.getUltrasonicLevel() == 0.0)) {
            if (rdetected < WHITE_THRESHOLD && ldetected > WHITE_THRESHOLD) {
                robot.leftMotor.setPower(0.0);
                robot.rightMotor.setPower(APPROACH_SPEED);
            }
            if (rdetected > WHITE_THRESHOLD && ldetected < WHITE_THRESHOLD) {
                robot.leftMotor.setPower(APPROACH_SPEED);
                robot.rightMotor.setPower(0.0);
            }
            else {
                robot.rightMotor.setPower(APPROACH_SPEED);
                robot.leftMotor.setPower(APPROACH_SPEED);
            }
            idle();
        }*/
    }
}
