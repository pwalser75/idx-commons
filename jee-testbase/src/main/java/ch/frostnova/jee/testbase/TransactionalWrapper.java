package ch.frostnova.jee.testbase;

import javax.transaction.Transactional;

/**
 * Allows to make individual code blocks transactional.<br>
 * Usage:
 * <pre><code>
 * &#64;Inject
 * private TransactionalWrapper tx;
 *
 * public void foo() throws Exception {
 *
 *     tx.execute(()-&lt; {
 *         // do something
 *     });
 *
 *     Something s = tx.execute() -&lt; {
 *          // return something;
 *     });
 * }
 * </code></pre>
 */
public class TransactionalWrapper {

    @Transactional
    public void execute(CheckedRunnable runnable) throws Exception {
        try {
            runnable.run();
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Transactional
    public <T> T execute(CheckedProducer<T> producer) throws Exception {
        try {
            return producer.run();
        } catch (Exception ex) {
            throw ex;
        }
    }

    @FunctionalInterface
    public static interface CheckedRunnable {
        void run() throws Exception;
    }

    @FunctionalInterface
    public static interface CheckedProducer<T> {
        T run() throws Exception;
    }
}
