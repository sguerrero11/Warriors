package helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * Helper Class to Handle logs in execution time.
 */
public abstract class LoggerHelper {

    private final static Logger _logger = LoggerFactory.getLogger(LoggerHelper.class);

    /***
     * Write a warning message in stdout console.
     * @param message [String]
     */
    public static void logWarning(String message) {
        _logger.warn(message);
    }

    /***
     * Write an error message in stdout console.
     * @param message [String]
     */
    public static void logError(String message) {
        _logger.error(message);
    }

    public static void logError(Throwable errorMessage) {
        _logger.error(String.valueOf(errorMessage));
    }

    /***
     * Write an info message in stdout console.
     * @param message [String]
     */
    public static void logInfo(String message) {
        _logger.info(message);
    }
    public static void logInfo(Integer number) {
        _logger.info(String.valueOf(number));
    }
    public static void logInfo(Double number) {
        _logger.info(String.valueOf(number));
    }

    public static void logInfo(String message, String extendedMessage) {
        _logger.info(message,extendedMessage);
    }

    public static void logSeparator() {
        logInfo("######################################################################################");
    }

    public static void logSeparatorSpaced() {
        logInfo("# # # # # # # # # # # # # # # # # # # # # # # # # # # #");
    }


}
