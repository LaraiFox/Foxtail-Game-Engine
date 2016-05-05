package laraifox.foxtail.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Logger {
	private static class LoggedMessage {
		private final String text;
		private final int messageLevel;

		public LoggedMessage(String text, int messageLevel) {
			this.text = text;
			this.messageLevel = messageLevel;
		}

		public String getText() {
			return text;
		}

		public int getMessageLevel() {
			return messageLevel;
		}
	}

	private static class LoggingThread extends Thread {
		private final List<LoggedMessage> MESSAGE_QUEUE = new ArrayList<LoggedMessage>();

		public LoggingThread(List<LoggedMessage> messages) {
			while (messages.size() > 0) {
				this.MESSAGE_QUEUE.add(messages.remove(0));
			}
		}

		public void run() {
			for (LoggedMessage message : this.MESSAGE_QUEUE) {
				if (message.getMessageLevel() < Logger.ERROR_MESSAGE_LEVEL) {
					System.out.println(message.getText());
				} else {
					System.err.println(message.getText());
				}
			}

			MESSAGE_QUEUE.clear();
		}
	}

	public static final int MESSAGE_LEVEL_DEBUG = 0;
	public static final int MESSAGE_LEVEL_DEFAULT = 1;
	public static final int MESSAGE_LEVEL_WARNING = 2;
	public static final int MESSAGE_LEVEL_ERROR = 3;
	public static final int MESSAGE_LEVEL_CRITICAL = 4;
	public static final int MESSAGE_LEVEL_COUNT = 5;

	private static final String[] MESSAGE_LEVEL_STRING = new String[] {
			"DEBUG", "INFO", "WARNING", "ERROR", "CRITICAL ERROR"
	};

	private static final List<LoggedMessage> MESSAGE_LOG = new ArrayList<LoggedMessage>();
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");
	private static final int ERROR_MESSAGE_LEVEL = MESSAGE_LEVEL_ERROR;
	private static String DEFAULT_SENDER_NAME = new String("UNKNOWN");

	private static int requiredMessageLevel = MESSAGE_LEVEL_DEFAULT;
	private static boolean immediateLogging = true;

	public static void initialize(int requiredMessageLevel, boolean immediateLogging) {
		Logger.MESSAGE_LOG.clear();

		Logger.requiredMessageLevel = requiredMessageLevel;
		Logger.immediateLogging = immediateLogging;
	}

	public static void flush() {
		Logger.flush(false);
	}

	public static void flush(boolean wait) {
		if (wait) {
			for (LoggedMessage message : Logger.MESSAGE_LOG) {
				if (message.getMessageLevel() < Logger.ERROR_MESSAGE_LEVEL) {
					System.out.println(message.getText());
				} else {
					System.err.println(message.getText());
				}
			}

			MESSAGE_LOG.clear();
		} else {
			Thread thread = new LoggingThread(MESSAGE_LOG);
			thread.start();
		}
	}

	public static void lineBreak() {
		lineBreak(MESSAGE_LEVEL_DEFAULT);
	}

	public static void lineBreak(int messageLevel) {
		if (messageLevel < requiredMessageLevel) {
			return;
		} else if (immediateLogging) {
			System.out.println();
		} else {
			LoggedMessage loggedMessage = new LoggedMessage("", messageLevel);

			MESSAGE_LOG.add(loggedMessage);

			if (messageLevel > MESSAGE_LEVEL_DEFAULT) {
				Logger.flush(true);
			}
		}
	}

	public static void log(String message) {
		log(message, DEFAULT_SENDER_NAME, MESSAGE_LEVEL_DEFAULT);
	}

	public static void log(String message, String sender) {
		log(message, sender, MESSAGE_LEVEL_DEFAULT);
	}

	public static void log(String message, int messageLevel) {
		log(message, DEFAULT_SENDER_NAME, messageLevel);
	}

	public static void log(String message, String sender, int messageLevel) {
		if (messageLevel < requiredMessageLevel) {
			return;
		} else if (immediateLogging) {
			if (messageLevel < Logger.ERROR_MESSAGE_LEVEL) {
				System.out.println(formatMessage(message, sender, messageLevel));
			} else {
				System.err.println(formatMessage(message, sender, messageLevel));
			}
		} else {
			LoggedMessage loggedMessage = new LoggedMessage(formatMessage(message, sender, messageLevel), messageLevel);

			MESSAGE_LOG.add(loggedMessage);

			if (messageLevel > MESSAGE_LEVEL_DEFAULT) {
				Logger.flush(true);
			}
		}
	}

	private static String formatMessage(String message, String sender, int messageLevel) {
		StringBuilder result = new StringBuilder();

		result.append("[" + DATE_FORMAT.format(new Date()) + "]");
		result.append("[" + sender + "]");
		result.append("[" + MESSAGE_LEVEL_STRING[messageLevel] + "]");
		result.append(": " + message);

		return result.toString();
	}
}
