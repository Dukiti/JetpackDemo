# Remove Android logging calls (in this case, including errors).
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int d(...);
    public static int i(...);
    public static int w(...);
    public static int e(...);
    public static int wtf(...);
    public static int println(...);
    public static java.lang.String getStackTraceString(java.lang.Throwable);
}

# Remove Java logging calls.
-assumenosideeffects class java.util.logging.Logger {
    public static java.util.logging.Logger getLogger(...);
    public boolean isLoggable(java.util.logging.Level);
    public void entering(...);
    public void exiting(...);
    public void finest(...);
    public void finer(...);
    public void fine(...);
    public void config(...);
    public void info(...);
    public void warning(...);
    public void severe(...);
}

-assumenosideeffects class java.lang.Throwable {
    public void printStackTrace();
}

-assumenosideeffects class java.io.PrintStream {
     public void println(%);
     public void println(**);
}