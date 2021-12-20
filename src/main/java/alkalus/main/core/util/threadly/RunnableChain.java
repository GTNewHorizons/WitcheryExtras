package alkalus.main.core.util.threadly;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Collections;
import java.util.Iterator;

import alkalus.main.core.WitcheryExtras;

/**
 * A class to chain multiple runnables to later be run together, within the same thread.
 * 
 * @since 1.0.0
 */
public class RunnableChain implements Runnable {
	protected final boolean exceptionStopsChain;
	private final Iterable<? extends Runnable> toRun;

	/**
	 * Constructs a runnable chain with a provided list of runnables to iterate over.
	 * 
	 * @param exceptionStopsChain {@code true} for uncaught exception stops the execution of the chain
	 * @param toRun Iterable collection of runnables to run
	 */
	public RunnableChain(boolean exceptionStopsChain, 
			Iterable<? extends Runnable> toRun) {
		if (toRun == null) {
			toRun = Collections.emptyList();
		}

		this.exceptionStopsChain = exceptionStopsChain;
		this.toRun = toRun;		
		WitcheryExtras.log(0, "[Module Loader] Loading Module with "+toRun.spliterator().getExactSizeIfKnown()+" objects.");
		
	}

	@Override
	public void run() {
		if (exceptionStopsChain) {
			runExceptionsCascade();
		} else {
			runIsolated();
		}
	}

	/**
	 * Iterates through the toRun list, executing along the way.  If any exceptions are thrown, they 
	 * will be propagated out of this call.
	 */
	protected void runExceptionsCascade() {
		Iterator<? extends Runnable> it = toRun.iterator();
		while (it.hasNext()) {
			it.next().run();
		}
	}

	/**
	 * Iterates through the toRun list, executing along the way.  If any exceptions are thrown, they 
	 * will be handled to {@link ExceptionUtils} and will not stop future executions.
	 */
	protected void runIsolated() {
		Iterator<? extends Runnable> it = toRun.iterator();
		while (it.hasNext()) {
			runRunnable(it.next());
		}
	}

	/**
	 * Invokes {@link Runnable#run()} on the provided runnable on this thread, ensuring that no 
	 * throwables are thrown out of this invocation.  If any throwable's are thrown, they will be 
	 * provided to {@link #handleException(Throwable)}.
	 * 
	 * @param r Runnable to invoke, can not be null
	 */
	public static void runRunnable(Runnable r) {
		try {
			r.run();
		} catch (Throwable t) {
			handleException(t);
		}
	}

	/**
	 * This call handles an uncaught throwable.  If a default uncaught exception handler is set, 
	 * then that will be called to handle the uncaught exception.  If none is set, then the 
	 * exception will be printed out to standard error.
	 * 
	 * @param t throwable to handle
	 */
	public static void handleException(Throwable t) {
		if (t == null) {
			return;
		}    
		try {
			Thread currentThread = Thread.currentThread();
			UncaughtExceptionHandler ueHandler = currentThread.getUncaughtExceptionHandler();
			ueHandler.uncaughtException(currentThread, t);
		} catch (Throwable handlerThrown) {
			try {
				System.err.println("Error handling exception: ");
				t.printStackTrace();
				System.err.println("Error thrown when handling exception: ");
				handlerThrown.printStackTrace();
			} catch (Throwable ignored) {
				// sigh...I give up
			}
		}
	}

}
