package abd;

import com.beust.jcommander.Parameter;

public class Options {
    @Parameter(names = {"-h","-?","--help"}, help = true, description = "display usage information")
    public boolean help;

    @Parameter(names = {"-d","--database"}, description = "JDBC database url")
    public String database = "jdbc:postgresql://localhost/benchmark";

    @Parameter(names = {"-U","--user"}, description = "database user name")
    public String user = null;

    @Parameter(names = {"-P","--password"}, description = "database password")
    public String passwd = null;

    @Parameter(names = {"-W","--warmup"}, description = "warmup time")
    public int warmup = 5;

    @Parameter(names = {"-R","--runtime"}, description = "run time")
    public int runtime = 10;

    @Parameter(names = {"-c","--clients"}, description = "number of client threads")
    public int clients = 1;

    @Parameter(names = {"-p","--populate"}, description = "create and initialize tables")
    public boolean populate = false;

    @Parameter(names = {"-x","--execute"}, description = "execute the workload")
    public boolean execute = false;

    @Parameter(names = {"-C","--connection"}, description = "check database connection")
    public boolean connection = false;

    @Parameter(names = {"-D","--dropSchema"}, description = "Drops database schema")
    public boolean dropSchema = false;

    @Parameter(names = {"-s", "--stats"}, description = "Display transaction operations stats")
    public boolean stats = false;
}
