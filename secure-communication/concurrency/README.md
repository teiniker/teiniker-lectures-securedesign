# Java Concurrency

The Java platform is designed to support concurrent programming, with basic concurrency support in the Java 
programming language and the Java class libraries. 

Since **Java 5**, the platform has also included **high-level concurrency APIs**. 

## Processes and Threads
In concurrent programming, there are two basic units of execution: processes and threads.

It's becoming more and more common for computer systems to have 
**multiple processors or processors with multiple execution cores**. 
This greatly enhances a system's capacity for concurrent execution of processes and threads.
But concurrency is possible even on simple systems, without multiple processors or execution cores.

### Processes

* A process has a **self-contained execution environment**. 

* A process generally has a complete, private set of basic run-time resources - each process has **its own memory space**.

* Processes are often seen as **synonymous with programs or applications**. 

* To facilitate communication between processes, most operating systems support **Inter Process Communication (IPC)** 
    resources, such as pipes and sockets.

* IPC is used not just for communication between processes on the same system, but **processes on different systems**.


### Threads

* Threads are sometimes called **lightweight processes**. 
    Creating a new thread requires fewer resources than creating a new process.

* **Threads exist within a process**. Threads share the process's resources, including memory and open files. 
    This makes for efficient, but potentially problematic, communication.

* A program starts with just one thread, called the **main thread**. 
    This thread has the ability to create additional threads.

_Example_: Creating and starting multiple threads
``` Java
public class RunnableExample
    implements Runnable
{
    @Override
    public void run()
    {
        // Do something
    }
    
    public static void main(String... args)
    {
        Thread t1 = new Thread(new RunnableExample());
        Thread t2 = new Thread(new RunnableExample());
        Thread t3 = new Thread(new RunnableExample());
        t1.start();
        t2.start();
        t3.start();
    }
}
``` 
The `Runnable` interface defines a single method, run, meant to contain the code executed in the thread. 
The `Runnable` object is passed to the `Thread` constructor.

The `Thread` class defines a number of methods useful for thread management. 
These include static methods, which provide information about, or affect the status of, the thread invoking the method. 
The other methods are invoked from other threads involved in managing the thread and `Thread` object. 


## Synchronization

Threads communicate primarily by **sharing access to fields and the objects reference fields refer to**. 

This form of communication is extremely efficient, but **makes two kinds of errors possible**:

* **Thread Interference** describes how errors are introduced when multiple threads access shared data.

* **Memory Consistency Errors** describes errors that result from inconsistent views of shared memory.

### Thread Interference
Interference happens when **two operations**, running in **different threads**, but acting on **the same data**, 
interleave. 
This means that the two operations consist of multiple steps, and the **sequences of steps overlap**.

Note that even **simple statements** can translate to **multiple steps by the virtual machine**. 
_Example_: i++
```Java 
public class UnsafeSequence
{
	private int value;
	
	public int getNext()
	{
		return value++;
	}
}
 
Bytecode instructions for getNext():
    public getNext()I
    ALOAD 0
    DUP
    GETFIELD org/se/lab/synchronization/UnsafeSequence.value : I
    DUP_X1
    ICONST_1
    IADD
    PUTFIELD org/se/lab/synchronization/UnsafeSequence.value : I
    IRETURN
    MAXSTACK = 4
    MAXLOCALS = 1
``` 

### Memory Consistency Errors
Memory consistency errors occur when **different threads** have **inconsistent views** of what should 
be **the same data**.

The key to avoiding memory consistency errors is understanding the **happens-before relationship**. 
This relationship is simply a guarantee that memory writes by one specific statement are visible to 
another specific statement.

There are several actions that create happens-before relationships. One of them is **synchronization**.

### Synchronized Methods

To make a method synchronized, simply add the `synchronized` keyword to its declaration.

Making methods synchronized has two effects:

* It is not possible for two invocations of synchronized methods on the same object to interleave.
    **When one thread is executing a synchronized method for an object, all other threads that invoke synchronized 
    methods for the same object suspend execution until the first thread is done with the object.**
    
* A synchronized method automatically establishes a happens-before relationship with any subsequent invocation of a 
    synchronized method for the same object.
    This **guarantees that changes to the state of the object are visible to all threads**.

Note that **constructors cannot be synchronized**. 
Synchronizing constructors doesn't make sense, because only the thread that creates an object should have access 
to it while it is being constructed.

### Intrinsic Locks 

Synchronization is built around an internal entity known as the **intrinsic lock** or **monitor lock**:
* **Every object has an intrinsic lock** associated with it. 
* A thread that needs exclusive and consistent access to an object's fields has to **acquire** the object's intrinsic lock 
    before accessing them, and then **release** the intrinsic lock when it's done with them. 
* **A thread is said to own the intrinsic lock between the time it has acquired the lock and released the lock**. 
* **As long as a thread owns an intrinsic lock, no other thread can acquire the same lock**. 
    The other thread will block when it attempts to acquire the lock.

There are two ways to acquire an intrinsic lock:
* **Locks in Synchronized Methods**\ 
    When a thread invokes a synchronized method, it automatically acquires the intrinsic lock for that method's object 
    and releases it when the method returns. The lock release occurs even if the return was caused by an uncaught exception.

* **Synchronized Statements**\
    Unlike synchronized methods, synchronized statements must **specify the object that provides the intrinsic lock**.
    Synchronized statements are useful for improving concurrency with **fine-grained synchronization**. 
        
_Example_: Synchronized class
```Java
public class SynchronizedPoint
{
    private int x;
    private int y;
    private String name;
    
    public SynchronizedPoint(int x, int y, String name)
    {
        check(x, y, name);
        this.x = x;
        this.y = y;
        this.name = name;
    }
    
    public void setPoint(int x, int y, String name)
    {
        check(x, y, name);
        synchronized(this)
        {
            this.x = x;
            this.y = y;
            this.name = name;
        }
    }
    
    synchronized public void invert()
    {
        x = -x;
        y = -y;
        name = "inverse of " + name;
    }
    
    synchronized public String toString()
    {
        return "(" + name + ":" + x + ";" + y + ")";
    }
        
    private void check(int x, int y, String name)
    {
        if(x < -100 || x > 100)
            throw new IllegalArgumentException();
        if(y < -100 || y > 100)
            throw new IllegalArgumentException();
        if(name == null)
            throw new NullPointerException();
    }
}
```
Note that **methods that do not access instance variables** do not have to be synchronized. 
They only operate on local data (stored on the stack).

### Reentrant Synchronization
Allowing a thread to **acquire the same lock more than once** enables reentrant synchronization. 
This describes a situation where **synchronized code, directly or indirectly, invokes a method that 
also contains synchronized code**, and both sets of code use the same lock. 

Without reentrant synchronization, synchronized code would have to take many additional precautions to avoid having 
a thread cause itself to block.

### Atomic Access

An atomic action is one that effectively happens all at once. 
**An atomic action cannot stop in the middle: it either happens completely, or it doesn't happen at all**. 
No side effects of an atomic action are visible until the action is complete.

Atomic actions cannot be interleaved, so they can be used without fear of thread interference. 
However, this does not eliminate all need to synchronize atomic actions, 
because **memory consistency errors are still possible**.

We have already seen that an increment expression, such as i++, does not describe an atomic action.
But there are actions in the VM that are atomic:
* Reads and writes are atomic for reference variables and for most primitive variables (all types except long and double).
* Reads and writes are atomic for all variables declared **volatile** (including long and double variables). 

Using volatile variables reduces the risk of memory consistency errors, because any write to a volatile variable 
**establishes a happens-before relationship** with subsequent reads of that same variable.
**Changes to a volatile variable are always visible to other threads**. 


## Concurrent Building Blocks

The Java standard libraries include a rich set of concurrent building blocks, such as **thread-safe collections** and 
a **variety of synchronizers** that can coordinate the control flow of cooperating threads. 

### Atomic Variables
The `java.util.concurrent.atomic` package defines classes that **support atomic operations on single variables**.
All classes have `get()` and `set()` methods that work like reads and writes on volatile variables. 
That is, a set has a happens-before relationship with any subsequent get on the same variable. 
The atomic `compareAndSet()` method also has these memory consistency features, as do the simple atomic arithmetic 
methods that apply to integer atomic variables.

_Example_: Atomic integer
```Java
public class AtomicCounter
{
    private AtomicInteger counter;
    
    public AtomicCounter(int startValue)
    {
        counter = new AtomicInteger(startValue);
    }
    
    public void increment()
    {
        counter.incrementAndGet();
    }
    
    public void decrement()
    {
        counter.decrementAndGet(); 
    }
    
    public int value()
    {
        return counter.get(); 
    }
}
```

### Synchronized Collections
These classes achieve thread safety be encapsulating their state and **synchronizing every public method** 
so that only one thread at a time can access the collection state.

The synchronized collection classes include **Vector** and **Hashtable**, as well as the synchronized wrapper classes 
created by the **Collections.synchronizedXXX()** factory methods. 

Synchronized collections achieve their thread safety by **serializing all access to the collection's state**. 
The cost of this approach is **poor concurrency**, when multiple threads contend for the collection-wide lock, 
throughput suffers.

The synchronized collections are thread safe, but we may sometimes need to use additional client-side locking to 
guard compound actions.
**Compound actions** on collections include iteration (repeatedly fetch elements until the collection is exhausted), 
navigation (find the next element after tis one according to some order), and conditional operations such as 
put-if-absent. 
With a synchronized collection, these compound actions are still technically thread-safe even without 
client-side locking, but they may not behave as we might expect when other treads can concurrently modify the collection.

### Concurrent Collections

Java improves on the synchronized collections by providing several concurrent collection classes. 
The concurrent collections are **designed for concurrent access from multiple threads**. 

* **ConcurrentHashMap**: A replacement for synchronized hash-based `Map` implementations

* **CopyOnWriteArrayList**: A replacement for synchronized `List` implementations.

* **BlockingQueue**: Extends `Queue` to add blocking insertion and retrieval operations - if the queue is empty, 
    a retrieval blocks until an element is available, and if the queue is full an insertion blocks until there 
    is space available. Blocking queues are **extremely useful in producer-consumer designs**.
    
* **ConcurrentSkipListMap** and **ConcurrentSkipListSet**, which are concurrent replacements for a synchronized 
    `SortedMap` or `SortedSet` (such as `TreeMap` or `TreeSet` wrapped with synchronizedMap).


## Executor Framework 

In **large-scale applications**, it makes sense to separate thread management and creation from the rest of the 
application. 
The executor framework automatically provides a **pool of threads** and an **API for assigning tasks** to it.
 
### Task Execution
Most concurrent applications are organized around the execution of **tasks**: abstract, discrete units of work. 

Ideally, tasks are **independent activities**: work that doesn't depend on the state, result, or side effects of other 
tasks. Independence facilitates concurrency, as independent tasks can be executed in parallel if there are adequate 
processing resources.

**Dividing the work of an application into tasks** promotes concurrency by providing a natural structure for 
**parallelizing work**.
The first step in organizing a program around task execution is **identifying sensible task boundaries**. 

**Server applications** should exhibit both good throughput and good responsiveness under normal load. 
Applications should exhibit graceful degradation as they become overloaded, rather than simply falling over under 
heavy load.
Most server applications offer a natural choice of task boundary: **individual client requests**. 
Web servers, mail servers, file servers, EJB containers, and database servers all accept requests via network 
connections from remote clients.

**Execute tasks sequentially in a single thread**: This approach is simple and theoretically correct, but 
would **perform poorly** in production because it can handle only one request at a time.

**Create a new thread for servicing each request**: The difference is that for each connection, the application 
creates a new thread to process the request.  
This has three main consequences:
* Task processing is off-loaded from the main thread, enabling the main loop to resume waiting for the next incoming 
    connection more quickly. This enables new connections to be accepted before previous requests complete, 
    **improving responsiveness**.
    
* Tasks can be processed in parallel, enabling multiple requests to be serviced simultaneously. 
    This may **improve throughput** if there are multiple processors, or if tasks need to block for any 
    reason such as I/O completion, lock acquisition, or resource availability.

* **Task-handling code must be thread-safe**, because it may be invoked concurrently for multiple tasks.

The thread-per-task approach is an improvement over sequential execution. 
As long as the requests arrival rate does not exceed the server's capacity to handle requests, this approach 
offers better responsiveness and throughput.

However, the **thread-per-task approach has some practical drawbacks**, especially when a large number of threads 
may be created: 
* **Thread lifecycle overhead**: Thread creation and teardown are not free. 

* **Resource consumption**:Active threads consume system resources, especially memory.

*  **Stability**: There is a limit on how many threads can be created. When we hit this limit, the most likely result 
    is an `OutOfMemoryError`.

### Executor Service
Tasks are logical units of work, and threads are a mechanism by which tasks can run asynchronously. 
We have examined two policies for executing tasks using threads - executing tasks sequentially in a single thread, 
and executing each task in its own thread. 
Both have serious limitations.

The primary abstraction for task execution in the executor framework is not Thread, but **Executor**.
The `Executor` interface provides a standard means of **decoupling task submission from task execution**, 
describing tasks with `Runnable`. 
The Executor implementations also provide lifecycle support and hooks for adding statistics gathering, 
application management, and monitoring.
Executor is based on the **producer-consumer pattern**, where activities that submit tasks are producers and the 
threads that execute tasks are the consumers. 

_Example_: Executor interface
```Java
public interface Executor 
{    
    void execute(Runnable command);
}
```

To address the issue of **execution service lifecycle**, the **ExecutorService** interface extends `Executor`, 
adding a number of methods for lifecycle management.
The lifecycle implied by `ExecutorService` has three states - **running**, **shutting down**, and **terminated**.
ExecutorServices are initially created in the running state.

_Example_: Using the ExecutorService 
```Java 
    ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
    
    for (String filename : fileNameList)
    {
        Runnable task = new EncodingTask(filename + ".jpg", filename + ".txt");
        executor.submit(task);
    }
    
    executor.shutdown();
    executor.awaitTermination(1, TimeUnit.MINUTES);
``` 
 
The `shutdown()` method initiates a **graceful shutdown**: no new tasks are accepted but previously submitted 
tasks are allowed to complete - including those that have not yet begun execution. 
The `shutdownNow()` method initiates an **abrupt shutdown**.

Once all tasks have completed, the `ExecutorService` transitions to the terminated state. 
We can wait for an `ExecutorService` to reach the terminated state with `awaitTermination()` or poll 
for whether it has yet terminated with `isTerminated()`.

### Thread Pools
A thread pool manages a **homogeneous pool of worker threads**. A thread pool is tightly bound to a **work queue** 
holding tasks waiting to be executed.
**Reusing an existing thread** instead of creating a new one amortizes thread creation and teardown costs over 
multiple requests.

**Worker threads have a simple life**: Request the next task from the work queue, execute it, and go back to waiting 
for another task.



## References
* Brian Goetz. **Java Concurrency in Practice**. Addison-Wesley, 2006

* Doug Lea. **Concurrent Programming in Java**. Addison-Wesley, 2nd Edition, 2000

* [The Java Tutorial: Lesson: Concurrency](https://docs.oracle.com/javase/tutorial/essential/concurrency/index.html)

*Egon Teiniker, 2019-2021, GPL v3.0*