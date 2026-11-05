package ca.accessarchitects.cmpt276group2;

// Stores user preferences regarding appearance and functionality.
public class Settings {
    // settings is a singleton so that only one instance can exist
    // globally.
    private static Settings singleton = null;

    // notification settings
    private boolean sendEmailNotifications;
    private boolean sendSmsNotifications;

    // app theme settings
    private boolean darkMode;

    private Settings()
    {

    }

    public static synchronized Settings getInstance()
    {
        if (singleton == null) {
            singleton = new Settings();
        }
        return singleton;
    }

    // IDE generated getters and setters for the settings.
    // The setters should set in motion anything that's meant to happen
    // when the settings are changed.
    public boolean isSendEmailNotifications() {
        return sendEmailNotifications;
    }

    public void setSendEmailNotifications(boolean sendEmailNotifications) {
        this.sendEmailNotifications = sendEmailNotifications;
    }

    public boolean isSendSmsNotifications() {
        return sendSmsNotifications;
    }

    public void setSendSmsNotifications(boolean sendSmsNotifications) {
        this.sendSmsNotifications = sendSmsNotifications;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }
}
