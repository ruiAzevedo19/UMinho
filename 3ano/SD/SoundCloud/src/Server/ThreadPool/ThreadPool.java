package Server.ThreadPool;

import Server.Tasks.*;
import java.util.ArrayList;
import java.util.List;

public class ThreadPool {
    private final BoundedBuffer<Task> tasksQueue;
    /** Bounded buffer com pedidos dos clientes **/
    private final List<Thread> workers;
    /** Threads criadas para atender os pedidos **/
    private volatile boolean shutdown = false;          /** Estado do servidor **/

    /**
     * Metodo Construtor : Inicia as threads e adiciona-as a lista workers
     *
     * @param nr_threads : numero total de threads
     * @param nr_tasks   : numero maximo de tarefas
     */
    public ThreadPool(int nr_threads, int nr_tasks) {
        this.tasksQueue = new BoundedBuffer<>(nr_tasks);
        this.workers = new ArrayList<>(nr_threads);

        for (int i = 0; i < nr_threads; i++) {
            Thread thread = new Thread(new Worker(tasksQueue));
            this.workers.add(thread);
            thread.start();
        }
    }

    /**
     * Adiciona uma task à lista de tasks
     *
     * @param task : tarefa a adicionar
     */
    public void addTask(Task task) {
        try {
            this.tasksQueue.put(task);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    /**
     * Espera que as tarefas da threadpool sejam feitas para depois interromper as threads
     */
    public void shutdownPool(){
        while( !tasksQueue.isEmpty() ){
            try{
                Thread.sleep(1000);
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }
        shutdown = true;

        workers.forEach(Thread::interrupt);
    }
}
