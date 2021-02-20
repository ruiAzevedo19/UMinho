/*
 * Copyright 2013 Universidade do Minho
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software   distributed under the License is distributed on an "AS IS" BASIS,   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and limitations under the License.
 */

/*
 * Created on 24-May-2005
 *
 */
package escada.tpc.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Ricardo Vilaça
 * 
 */
public class PerformanceLogger {

    private static PrintWriter out;


    public static void setPrintWriter(String fileName)
    {
        try {
            out=new PrintWriter(new FileWriter(fileName),true);
            out.println("StartTime:Replica:FinishTime:OperationTime:Result:Access:Type");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public static boolean isPerformanceLoggerEnabled() {
		return (out!=null);
	}

	public static void info(String pStr) {
		out.println(pStr);
	}

    public static void close()
    {
        out.flush();
        out.close();
        out=null;
    }
}