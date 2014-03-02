/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.usage;

import static me.hurel.hqlbuilder.builder.Yaqapi.*;
import static org.fest.assertions.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import me.hurel.entity.User;
import me.hurel.hqlbuilder.Condition;

import org.junit.Test;

public class MultithreadedTest {

    @Test
    public void test_with_10_threads() throws InterruptedException {
	List<Runnable> runnables = new ArrayList<Runnable>();
	for (int i = 0; i < 10; i++) {
	    runnables.add(newTest());
	}
	assertConcurrent("Test with 10 threads", runnables, 5);
    }

    @Test
    public void test_with_30_threads() throws InterruptedException {
	List<Runnable> runnables = new ArrayList<Runnable>();
	for (int i = 0; i < 10; i++) {
	    runnables.add(newTest());
	}
	assertConcurrent("Test with 30 threads", runnables, 5);
    }

    public Runnable newTest() {
	return new Runnable() {
	    public void run() {
		User user = queryOn(new User());
		User child = andQueryOn(new User());
		User littleChild = andQueryOn(new User());
		Condition<Long> query = select(user).from(user).innerJoinFetch(user.getChildren()).whereExists(distinct(child.getId())).from(child)
			.whereExists(distinct(littleChild.getId())).from(littleChild).where(littleChild.getAge()).isLessEqualThan(2).and(littleChild.getFather().getId())
			.isEqualTo(child.getId()).closeExists().and(child.getFather().getId()).isEqualTo(user.getId()).closeExists();
		assertThat(query.getQueryString())
			.isEqualTo(
				"SELECT user FROM User user INNER JOIN FETCH user.children children WHERE EXISTS ( SELECT distinct(user2.id) FROM User user2 WHERE EXISTS ( SELECT distinct(user3.id) FROM User user3 WHERE user3.age <= ?1 AND user3.father.id = user2.id ) AND user2.father.id = user.id ) ");
		assertThat(query.getParameters()).containsExactly(2);
	    }
	};
    }

    public static void assertConcurrent(final String message, final List<? extends Runnable> runnables, final int maxTimeoutSeconds) throws InterruptedException {
	final int numThreads = runnables.size();
	final List<Throwable> exceptions = Collections.synchronizedList(new ArrayList<Throwable>());
	final ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
	try {
	    final CountDownLatch allExecutorThreadsReady = new CountDownLatch(numThreads);
	    final CountDownLatch afterInitBlocker = new CountDownLatch(1);
	    final CountDownLatch allDone = new CountDownLatch(numThreads);
	    for (final Runnable submittedTestRunnable : runnables) {
		threadPool.submit(new Runnable() {
		    public void run() {
			allExecutorThreadsReady.countDown();
			try {
			    afterInitBlocker.await();
			    submittedTestRunnable.run();
			} catch (final Throwable e) {
			    exceptions.add(e);
			} finally {
			    allDone.countDown();
			}
		    }
		});
	    }
	    // wait until all threads are ready
	    assertThat(allExecutorThreadsReady.await(runnables.size() * 10, TimeUnit.MILLISECONDS)).as(
		    "Timeout initializing threads! Perform long lasting initializations before passing runnables to assertConcurrent").isTrue();
	    // start all test runners
	    afterInitBlocker.countDown();
	    assertThat(allDone.await(maxTimeoutSeconds, TimeUnit.SECONDS)).as(message + " timeout! More than" + maxTimeoutSeconds + "seconds").isTrue();
	} finally {
	    threadPool.shutdownNow();
	}
	assertThat(exceptions.isEmpty()).as(message + "failed with exception(s)" + exceptions).isTrue();
    }

}
