/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author damangrea
 */
public class LoggerFile {
    String pathFile;
    String IdLog;
    

    public LoggerFile(String pathFile, String IdLog) {
        this.pathFile = pathFile;
        this.IdLog = IdLog;
    }
    
    public void writeLog(String msg){
        StringBuilder sb= new StringBuilder();
        sb.append(System.currentTimeMillis());
        sb.append(" ").append(IdLog);
        sb.append(" ").append(msg);
        sb.append(System.lineSeparator());
        System.out.println(sb.toString());
        try{
            FileWriter fw = new FileWriter(pathFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            out.print(sb.toString());
            out.flush();
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }
}
