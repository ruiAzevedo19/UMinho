package Server;

import Server.Tasks.Task;

import java.util.Date;

/**
 * Classe usada para aglomerar uma tarefa e o tempo de chegada ao sistema
 */
public class TimeTask {
    private long arrival;
    private Task task;

    /**
     * Metodo construtor
     *
     * @param arrival : hora de chegada da tarefa ao sistema
     * @param task : tarefa
     */
    public TimeTask(long arrival, Task task) {
        this.arrival = arrival;
        this.task = task;
    }

    /**
     * @return : devolve a hora de chegada
     */
    public long getArrival() {
        return arrival;
    }

    /**
     * @return : devolve a tarefa
     */
    public Task getTask() {
        return task;
    }
}
