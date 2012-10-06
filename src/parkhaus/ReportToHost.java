package parkhaus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import javax.swing.JOptionPane;

public class ReportToHost {

    private MainClass plugin;
    private FileSend filesend;
    private StackTraceUtil util;

    public ReportToHost(MainClass pl) {
        this.plugin = pl;
        util = new StackTraceUtil();
        if (PrepareLibrary.loaded()) {
            this.filesend = new FileSend(plugin);
        }
    }

    public String report(int line, String other, String message, String classfile, Exception stack) {
        int returnstatus = JOptionPane.showConfirmDialog(plugin, "Soll ein Fehlerbericht gesendet werden?", "Fehler senden?", JOptionPane.OK_CANCEL_OPTION);
        if (returnstatus == JOptionPane.OK_OPTION) {
            if (other == null) {
                other = "none";
            }
            if (message == null) {
                message = "none";
            }
            String stacktrace;
            if (stack != null) {
                stacktrace = util.getStackTrace(stack);
            } else {
                stacktrace = "none";
            }

            return send(line + "", message, classfile, stacktrace, other);
        }
        return "internet not enabled in the config.yml";
    }

    public String report(int line, String other, String message, String classfile, String stacktrace) {
        int returnstatus = JOptionPane.showConfirmDialog(plugin, "Soll ein Fehlerbericht gesendet werden?", "Fehler senden?", JOptionPane.OK_CANCEL_OPTION);
        if (returnstatus == JOptionPane.OK_OPTION) {
            if (other == null) {
                other = "none";
            }
            if (message == null) {
                message = "none";
            }
            if (stacktrace == null) {
                stacktrace = "none";
            }
            return send(line + "", message, classfile, stacktrace, other);
        }
        return "internet not enabled in the config.yml";
    }

    public String readAll(String url) {
        String zeile;
        try {
            URL myConnection = new URL(url);
            URLConnection connectMe = myConnection.openConnection();
            InputStreamReader lineReader = new InputStreamReader(connectMe.getInputStream());
            BufferedReader br = new BufferedReader(new BufferedReader(lineReader));
            zeile = br.readLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            this.plugin.Logger("Exception: IOException! Exception on reading message!", "Error");
            return "Exception on reading message!";
        } catch (Exception e) {
            e.printStackTrace();
            this.plugin.Logger("Exception: Exception! Exception on reading message!", "");
            return "Exception on reading message!";
        }
        return zeile;
    }

    public String send(String line, String message, String classfile, String stacktrace, String other) {
        String ret = "Error";
        String gameversion = "unknown";
        try {
            gameversion = URLEncoder.encode(System.getProperty("os.name") + ": " + System.getProperty("os.version"), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            gameversion = "exceptiononencoding";
        }
        try {
            stacktrace = URLEncoder.encode(stacktrace, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            stacktrace = "exceptiononencoding";
        }
        try {
            message = URLEncoder.encode(message, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            message = "exceptiononencoding";
        }
        try {
            classfile = URLEncoder.encode(classfile, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            classfile = "exceptiononencoding";
        }
        try {
            other = URLEncoder.encode(other, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            other = "exceptiononencoding";
        }
        String url = "http://report.ibhh.de/index.php?programm=" + this.plugin.getName() + "&version=" + this.plugin.getVersion() + "&line=" + line + "&gameversion=" + gameversion + "&message=" + message + "&class=" + classfile + "&stacktrace=" + stacktrace + "&other=" + other;
        try {
            String temp = "[" + this.plugin.getName() + "] Sending issue report to ibhh.de!";
            System.out.print(temp);
            this.plugin.getDebugLogger().log(temp);
            temp = "[" + this.plugin.getName() + "] -------------------------";
            System.out.print(temp);
            this.plugin.getDebugLogger().log(temp);
            temp = "[" + this.plugin.getName() + "] Version: " + this.plugin.getVersion();
            System.out.print(temp);
            this.plugin.getDebugLogger().log(temp);
            System.out.print("[" + this.plugin.getName() + "] ErrorID: " + line);
            temp = "[" + this.plugin.getName() + "] ErrorID: " + line;
            System.out.print(temp);
            this.plugin.getDebugLogger().log(temp);
            temp = "[" + this.plugin.getName() + "] Gameversion: " + System.getProperty("os.name") + ": " + System.getProperty("os.version");
            System.out.print(temp);
            this.plugin.getDebugLogger().log(temp);
            temp = "[" + this.plugin.getName() + "] Other: " + other;
            System.out.print(temp);
            this.plugin.getDebugLogger().log(temp);
            temp = "[" + this.plugin.getName() + "] Message: " + message;
            System.out.print(temp);
            this.plugin.getDebugLogger().log(temp);
            if (MainClass.debug) {
                temp = "[" + this.plugin.getName() + "] Stacktrace: " + stacktrace;
                System.out.print(temp);
                this.plugin.getDebugLogger().log(temp);
            }
            temp = "[" + this.plugin.getName() + "] Class: " + classfile;
            System.out.print(temp);
            this.plugin.getDebugLogger().log(temp);
            temp = "[" + this.plugin.getName() + "] -------------------------";
            System.out.print(temp);
            this.plugin.getDebugLogger().log(temp);
            ret = readAll(url);
            temp = "[" + this.plugin.getName() + "] Message of Server: " + ret;
            JOptionPane.showMessageDialog(plugin, ret, "Message of the Server", JOptionPane.INFORMATION_MESSAGE);
            System.out.print(temp);
            this.plugin.getDebugLogger().log(temp);
            temp = "[" + this.plugin.getName() + "] -------------------------";
            System.out.print(temp);
            this.plugin.getDebugLogger().log(temp);
        } catch (Exception ex) {
            String temp = "[" + this.plugin.getName() + "] Couldnt send error report to ibhh.de!";
            System.out.print(temp);
            this.plugin.getDebugLogger().log(temp);
            if (MainClass.debug) {
                ex.printStackTrace();
            }
        }
        if (PrepareLibrary.loaded()) {
            this.plugin.Logger("filesend loaded", "Debug");
            if (ret != null) {
                this.plugin.Logger("ret != null", "Debug");
                try {
                    String[] id_text = ret.split(":");
                    String id = id_text[1];
                    this.plugin.Logger("ID: " + id, "Debug");
                    try {
                        if (id != null) {
                            int returnstatus = JOptionPane.showConfirmDialog(plugin, "Sollen die Debuglogs gesendet werden?", "Logs senden?", JOptionPane.OK_CANCEL_OPTION);
                            if (returnstatus == JOptionPane.OK_OPTION) {
                                this.filesend.sendDebugFile(id);
                            }
                        }
                    } catch (Exception e1) {
                        this.plugin.Logger("Could not send debugfile!", "Error");
                        if (MainClass.debug) {
                            e1.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
        return ret;
    }
}