
package com.irontigers;
import java.util.TimerTask;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.I2C.Port;

/**
 * DESCRIPTION: <br>
 * Based on https://gist.github.com/tech2077/c4ba2d344bdfcddd48d2/download# Comments added, some
 * functionality trimmed out to match our needs. Multithreaded, will poll the sensor in the
 * background at an appropriate rate. <br>
 * USAGE:
 * <ol>
 * <li>Instantiate class.</li>
 * <li>Call start() method to begin polling the sensor</li>
 * <li>Call getDistance() during periodic loops to get the distance read from the sensor.</li>
 * </ol>
 *
 */

public class pulsedLightLIDAR { // We don't need any pid system, So I took out the code where LIDAR
                                // inherits from a PID system
    private static I2C i2c;
    private static byte[] distance;
    private static java.util.Timer updater;
    private static final int LIDAR_ADDR = 0x62;
    private static final int LIDAR_CONFIG_REGISTER = 0x00;
    private static final int LIDAR_DISTANCE_REGISTER = 0x8f;


    public static void pulsedLightLIDAR2() {
        i2c = new I2C(Port.kMXP, LIDAR_ADDR);
        distance = new byte[2];
        updater = new java.util.Timer();
    }


    /**
     * Internally return Distance in cm
     * 
     * @return distance in cm
     */
    public static int getDistance() {           
        return (int) Integer.toUnsignedLong(distance[0] << 8) + Byte.toUnsignedInt(distance[1]);
    }


    /**
     * Return Distance in Inches
     * 
     * @return distance in inches
     */
    public static double getDistanceIn() { // I made this function better. It used to be part of a PID
                                    // system. We didn't need a PID system.
        return (double) getDistance(); //* 0.393701; for inches 
    }


    /**
     * Start 10Hz polling of LIDAR sensor, in a background task. Only allow 10 Hz. polling at the
     * moment.
     */
    public static void start() {
        updater.scheduleAtFixedRate(new LIDARUpdater(), 0, 100);
    }


    /**
     * Stop the background sensor-polling task.
     */
    public static void stop() {
        updater.cancel();
        updater = new java.util.Timer();
    }


    /**
     * Read from the sensor and update the internal "distance" variable with the result.
     */
    public static void update() {
        i2c.write(LIDAR_CONFIG_REGISTER, 0x04); // Initiate measurement
        Timer.delay(0.04); // Delay for measurement to be taken
        i2c.read(LIDAR_DISTANCE_REGISTER, 2, distance); // Read in measurement
        Timer.delay(0.005); // Delay to prevent over polling
    }

    /**
     * Timer task to keep distance updated
     *
     */
    public static class LIDARUpdater extends TimerTask {
        public void run() {
            while (true) {
                update();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}