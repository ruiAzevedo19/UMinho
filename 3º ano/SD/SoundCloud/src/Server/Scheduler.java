package Server;

import Server.ThreadPool.ThreadPool;
import Server.Tasks.Task;

import java.util.List;

/**
 * Classe que implementa a politica de escalonamento de tarefas
 */
public class Scheduler implements Runnable{
    private MinHeap tasks;
    private ThreadPool threadPool;
    private ThreadPool threadPoolDownloads;
    private boolean shutdown;

    /**
     * Metodo construtor
     *
     * @param tasks : minheap
     * @param threadPool : threadpool para pedidos
     * @param threadPoolDownloads : threadpool para downloads
     * @param shutdown : estado do servidor
     */
    public Scheduler(MinHeap tasks, ThreadPool threadPool, ThreadPool threadPoolDownloads, boolean shutdown) {
        this.tasks = tasks;
        this.threadPool = threadPool;
        this.threadPoolDownloads = threadPoolDownloads;
        this.shutdown = shutdown;
    }

    /**
     * Retira alguns elementos seguidos da heap e adiciona os elementos
     * a thread pool
     */
    @Override
    public void run() {
        while( !shutdown ) {
            List<Task> task_list = tasks.get();
            for(Task task : task_list){
                if( task.getClass().getName().equals("Server.Tasks.DownloadTask") )
                    threadPoolDownloads.addTask(task);
                else
                    threadPool.addTask(task);
            }
        }
    }
}
